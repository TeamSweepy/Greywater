
package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entities.components.AnimEvent;
import com.teamsweepy.greywater.entities.components.AnimEventListener;
import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.entities.components.ai.PathfinderMotor;
import com.teamsweepy.greywater.entities.level.Level;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.geom.Line2D;
import java.util.Random;

public abstract class Mob extends Entity implements AnimEventListener {

	/* ********* ANIMATION VARIABLES *********** */
	private String name; //indicates what Atlas to get sprites from
	protected String currentDirection = "South";   //used to indicate what direction Mob is facing to get the corresponding sprite
	protected float walkCycleDuration = 1f; //duration in seconds of animation

	/* *********** WORLD INTERACTION VARIABLES ************ */
	protected boolean attacking = false;
	public boolean friendly; //indicates if this is a friend of the Player.
	private int HP = 100;
	protected Line2D.Float sightLine;
	protected boolean canSeeTarget;
	protected int maxSightRange = 20;//in tiles
	protected Level world;
	protected Entity focusTarget;
	protected PathfinderMotor pather;

	//protected Inventory inventory;

	/**
	 * @param x - tile location x
	 * @param y - tile location y
	 * @param width - physics component width
	 * @param height - - physics component height
	 * @param speed - tiles per second
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


	/**
	 * Draws the sprite for this entity centered on a tile.
	 * @param g - Graphics object
	 */
	public void render(SpriteBatch g) {
		Point2F p = Globals.toIsoCoord(getX(), getY());
		//center on the tile
		graphicsComponent.render(g, p.x - graphicsComponent.getImageWidth() / 2, p.y + Globals.tileImageHeight / 3);
	}

	/**
	 * Update graphics and physics components, deal with animation and behavior
	 */
	public void tick(float deltaTime) {
		if (HP < 0 && !graphicsComponent.getCurrentImageName().contains("DIE")) { //if dead
			graphicsComponent.setImage(.4f, "Die", Sprite.FORWARD);
			attacking = false;
			return;
		} else { //if alive
			super.tick(deltaTime);
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

	public void handleEvent(AnimEvent e) {
		if (e.action.contains("ATTACK") && e.ending && !e.beginning) {
			attacking = false;
		} else if (e.action.contains("Walk") && !e.beginning) {
			int num = 0;
			num = new Random().nextInt(6) + 1;
			//			SoundLoader.playSingle(name + "Walk" + num);
		} else if (e.action.contains("Die") && e.ending) {
			System.out.println(name + " died: " + HP);
			graphicsComponent = new Sprite(this.name, name + "Dead");
		}

	}

	//	public Inventory getInventory(){ TODO activate when inventories exist
	//		return inventory;
	//	}

	public void changeHP(int damage) {
		HP -= damage;
		System.out.println(name + " took " + damage + " dmg ---> " + HP + " HP");
		if (HP <= 0) {
			graphicsComponent.setImage(0.4f, "Die", Sprite.FORWARD);
		}
	}

	public boolean isAlive() {
		if (HP > 0)
			return true;
		return false;
	}

	public int getHP() {
		return HP;
	}

	public boolean canSeeTarget() {
		if (sightLine != null && sightLine.getP1().distance(sightLine.getP2()) <= this.maxSightRange && !world.checkLevelCollision(sightLine))
			canSeeTarget = true;
		else
			canSeeTarget = false;
		return canSeeTarget;
	}

	public Line2D.Float getSight() {
		return sightLine;
	}

	public String getName() {
		return name;
	}

	protected boolean didPointHitImage(Point2F point) {
		Point2F p = Globals.toIsoCoord(getX(), getY());
		return graphicsComponent.getImageRectangleAtOrigin(p.x + mainCamera.xOffsetAggregate - graphicsComponent.getImageWidth() / 2,
			p.y + mainCamera.yOffsetAggregate + Globals.tileImageHeight / 3).contains(point.x, point.y);
	}

	/**
	 * Gets next action for this mob, can be AI logic or player input, subclasses!
	 */
	protected abstract void getInput();

	protected abstract void attack(Mob enemy);

	public abstract boolean interact();
}
