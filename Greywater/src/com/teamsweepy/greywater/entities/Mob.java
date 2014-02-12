
package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Mob extends Entity {

	public String name;

	protected int direction;
	protected String currentDirection = "South";
	protected float walkCycleDuration = 1f;
	protected boolean attacking = false;

	public boolean friendly;

	private int HP = 100;

	/**
	 * @param x - tile location x
	 * @param y - tile location y
	 * @param width - physics component width
	 * @param height - - physics component height
	 */
	public Mob(float x, float y, int width, int height, float speed) {
		physicsComponent = new Hitbox(x, y, width, height, speed);
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
			if (true) return;
			if (attacking)
				graphicsComponent.setImage(.25f, "Attack_" + currentDirection, Sprite.FORWARD); // TODO if multiple attacks clicked, pingpong
			else if (physicsComponent.isMoving()) {
				graphicsComponent.setImage(walkCycleDuration, "Walk_" + currentDirection, Sprite.LOOP);
			} else
				graphicsComponent.setImage(1f, "Stand_" + currentDirection, Sprite.STILL_IMAGE);
		}
	}

	/**
	 * Change the Mob's HP by the given amount.
	 * 
	 * @param damage - how much to change mob HP by
	 */
	public void changeHP(int damage) {
		HP -= damage;
		System.out.println(name + " took " + damage + " dmg ---> " + HP + " HP");
		if (HP <= 0) {
			graphicsComponent.setImage(0.4f, "Die", Sprite.FORWARD);
		}
	}

	public boolean isAlive() {
		if (HP > 0) return true;
		return false;
	}

	/**
	 * Gets next action for this mob, can be AI logic or player input, subclasses!
	 */
	protected abstract void getInput();

	protected abstract void attack(Mob enemy);

}
