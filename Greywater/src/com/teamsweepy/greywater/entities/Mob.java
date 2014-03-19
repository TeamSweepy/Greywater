/**
 * Abstract class that holds logic for all Mobile Units.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Barnes
 */

package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.entities.components.ai.PathfinderMotor;
import com.teamsweepy.greywater.entities.components.events.AnimEvent;
import com.teamsweepy.greywater.entities.components.events.AnimEventListener;
import com.teamsweepy.greywater.entities.components.events.GameEvent;
import com.teamsweepy.greywater.entities.components.events.GameEventListener;
import com.teamsweepy.greywater.entities.level.Level;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.Inventory;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Mob extends Entity implements AnimEventListener {

	/* ********* ANIMATION VARIABLES *********** */
	protected String name; //indicates what Atlas to get sprites from
	protected String currentDirection = "South";   //used to indicate what direction Mob is facing to get the corresponding sprite
	protected float walkCycleDuration = 1f; //duration in seconds of animation

	/* *********** WORLD INTERACTION VARIABLES ************ */
	protected boolean attacking = false;
	public boolean friendly; //indicates if this is a friend of the Player.
	protected int HP = 100;
	protected Line2D.Float sightLine;
	protected boolean canSeeTarget;
	protected int maxSightRange = 20 * 50;//in tiles
	protected Level world;
	protected Entity focusTarget;
	protected PathfinderMotor pather;

	protected Inventory inventory;
	protected ArrayList<Entity> killList;
	private List<GameEventListener> listeners;
	
	public Mob() {
	}

	/**
	 * @param x - tile location x
	 * @param y - tile location y
	 * @param width - physics component width
	 * @param height - - physics component height
	 * @param speed - tiles per second
	 * @param isAStar - true = AStar PathFinder, false = PotentialField PathFinder
	 */
	public Mob(String name, float x, float y, int width, int height, float speed, Level level, boolean isAStar) {
		this.name = name;
		physicsComponent = new Hitbox(x * 50 + 25, y * 50 + 25, width, height, speed * 50);
		this.graphicsComponent = new Sprite(getName(), "Stand_South");
		graphicsComponent.addAnimListener(this);
		world = level;

		if (isAStar)
			pather = new PathfinderMotor(PathfinderMotor.Method.ASTAR);
		else
			pather = new PathfinderMotor(PathfinderMotor.Method.POTENTIAL_FIELD);

		pather.updateMap(level);
	}


	/** Draws the sprite for this entity centered on a tile. */
	public void render(SpriteBatch g) {
		Point2F p = Globals.toIsoCoord(getX(), getY());
		//center on the tile
		graphicsComponent.render(g, p.x - graphicsComponent.getImageWidth() / 2, p.y + Globals.tileImageHeight / 10);
		if (inventory != null) {
			inventory.render(g);
		}
	}

	/** Update graphics and physics components, deal with animation and behavior */
	public void tick(float deltaTime) {
		super.tick(deltaTime);

		if (HP <= 0) { //if dead
			physicsComponent.stopMovement();
			attacking = false;
		} else { //if alive
			getInput();
			if (attacking) {
				graphicsComponent.setImage(.25f, "Attack_" + currentDirection, Sprite.FORWARD); // TODO if multiple attacks clicked, pingpong
				physicsComponent.stopMovement();
				return;
			} else if (physicsComponent.isMoving()) {
				currentDirection = Globals.getDirectionString(physicsComponent.destination.x - getX(), physicsComponent.destination.y - getY());
				graphicsComponent.setImage(walkCycleDuration, "Walk_" + currentDirection, Sprite.LOOP);
			} else {
				physicsComponent.stopMovement();
				graphicsComponent.setImage(1f, "Stand_" + currentDirection, Sprite.STILL_IMAGE);
			}
		}
	}

	/** Generic implementation for walk and attack sounds based on Animation Events */
	public void handleEvent(AnimEvent e) {
		if (e.action.contains("ATTACK") && e.ending && !e.beginning) {
			attacking = false;
		} else if (e.action.contains("WALK") && !e.beginning) {
			//((Sound) AssetLoader.getAsset(Sound.class, "TavWalk1V1.wav")).play(); //replace with proper naming convention TODO	
		} else if (e.action.contains("Die") && e.ending) {
			System.out.println(name + " died: " + HP);
			graphicsComponent = new Sprite(this.name, name + "Dead");
			attacking = false;
		}

	}

	//	public Inventory getInventory(){ TODO activate when inventories exist
	//		return inventory;
	//	}

	/** Reduce or increase this mob's health by the given amount (+ for damage, - for buffs/healing) */
	public void changeHP(int damage) {
		HP -= damage;
		System.out.println(name + " took " + damage + " dmg ---> " + HP + " HP");
		if (HP <= 0) {
			graphicsComponent.setImage(0.7f, "Die", Sprite.FORWARD);
		}
	}

	/** Indicates if this mob has >0 HP */
	public boolean isAlive() {
		if (HP > 0)
			return true;
		return false;
	}

	/** Returns this mob's current HP */
	public int getHP() {
		return HP;
	}

	/** Checks to see if sightline between the mob and its assigned target is unobstructed by level geometry. */
	public boolean canSeeTarget() {
		if (sightLine == null)
			sightLine = new Line2D.Float();
		sightLine.setLine(focusTarget.getX(), focusTarget.getY(), getX(), getY());
		if (sightLine != null && sightLine.getP1().distance(sightLine.getP2()) <= this.maxSightRange && !world.checkLevelCollision(sightLine))
			canSeeTarget = true;
		else
			canSeeTarget = false;
		return canSeeTarget;
	}

	/** Return the line that indicates the Mobs sightline to its target */
	public Line2D.Float getSight() {
		return sightLine;
	}

	/** Return this mobs name */
	public String getName() {
		return name;
	}

	/** Used to find out if a clicked point intersected Mobs sprite bounding box */
	protected boolean didPointHitImage(Point2F point) {
		Point2F p = Globals.toIsoCoord(getX(), getY());
		return graphicsComponent.getImageRectangleAtOrigin(p.x + mainCamera.xOffsetAggregate - graphicsComponent.getImageWidth() / 2,
			p.y + mainCamera.yOffsetAggregate + Globals.tileImageHeight / 3).contains(point.x, point.y);
	}

	public void setInventory(Inventory i) {
		inventory = i;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public int maxHP(){
		return 100;
	}
	
	/** Add a class that implements GameEventListener Interface who wants to listen to this mob */
	public void addGameListener(GameEventListener listener) {
		if(listeners == null){
			listeners = new ArrayList<GameEventListener>();
		}
		listeners.add(listener);
	}

	/** Remove a class that implements GameEventListener Interface who no longer wants to listen to this mob */
	public void removeGameListener(GameEventListener listener) {
		if(listeners == null || listeners.size() < 1)
			return;
		listeners.remove(listener);
	}

	/** Fire an event for all listeners to hear */
	public void fireEvent(GameEvent e) {
		if (listeners == null || listeners.size() < 1)
			return;

		for (GameEventListener listener : listeners) {
			listener.handleGameEvent(e);
		}
	}
	

	/** Gets next action for this mob, can be AI logic or player input, subclasses can deal with it. */
	protected abstract void getInput();

	/** Deal damage and pursue clicked Mobs */
	protected abstract void attack(Mob enemy);

	/** Deal with friendly entities or attack enemy mobs */
	public abstract boolean interact();
}
