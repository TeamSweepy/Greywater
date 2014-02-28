
package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.entities.components.ai.PathfinderMotor;
import com.teamsweepy.greywater.entities.components.ai.core.Pathfinder;
import com.teamsweepy.greywater.entities.level.Level;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.geom.Line2D;

public abstract class Mob extends Entity {

	/* ********* ANIMATION VARIABLES *********** */
	public String name; //indicates what Atlas to get sprites from
	protected String currentDirection = "South";   //used to indicate what direction Mob is facing to get the corresponding sprite
	protected float walkCycleDuration = 1f; //duration in seconds of animation

	/* *********** WORLD INTERACTION VARIABLES ************ */
	protected boolean attacking = false;
	public boolean friendly; //indicates if this is a friend of the Player.
	private int HP = 100;
	protected Line2D.Float sightLine;
	protected boolean canSeeTarget;
	protected int maxSightRange = 20;//in tiles
	private Entity focusTarget;
	private Level world;
    private PathfinderMotor pather;

	//protected Inventory inventory;

	/**
	 * @param x - tile location x
	 * @param y - tile location y
	 * @param width - physics component width
	 * @param height - - physics component height
	 * @param speed - tiles per second
	 */
	public Mob(float x, float y, int width, int height, float speed, Level level) {
		physicsComponent = new Hitbox(x, y, width, height, speed * 50);
		world = level;
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
		graphicsComponent.render(g, p.x - graphicsComponent.getImageWidth() / 2, p.y - Globals.tileImageHeight / 2);
	}

	/**
	 * Update graphics and physics components, deal with animation and behavior
	 */
	public void tick(float deltaTime) {
		getInput();

		if (HP < 0 && !graphicsComponent.getCurrentImageName().contains("DIE")) {
			graphicsComponent.setImage(.4f, "Die", Sprite.FORWARD);
			attacking = false;
			return;
		} else {
			super.tick(deltaTime);

			if (attacking)
				graphicsComponent.setImage(.25f, "Attack_" + currentDirection, Sprite.FORWARD); // TODO if multiple attacks clicked, pingpong
			else if (physicsComponent.isMoving()) {
				graphicsComponent.setImage(walkCycleDuration, "Walk_" + currentDirection, Sprite.LOOP);
			} else
				graphicsComponent.setImage(1f, "Stand_" + currentDirection, Sprite.STILL_IMAGE);
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

	/**
	 * Gets next action for this mob, can be AI logic or player input, subclasses!
	 */
	protected abstract void getInput();

	protected abstract void attack(Mob enemy);

	public abstract boolean interact();
}
