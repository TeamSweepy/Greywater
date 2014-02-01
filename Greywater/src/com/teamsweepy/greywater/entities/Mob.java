package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Sprite;

public abstract class Mob extends Entity {

	public String name;

	private int direction;
	private String currentDirection = "South";
	private float walkCycleDuration = 1f;
	private boolean attacking = false;

	public boolean friendly;

	private int HP = 100;

	/**
	 * Update graphics and physics components, deal with animation and behavior
	 */
	public void tick(float deltaTime) {
		if (HP < 0 && !graphicsComponent.getCurrentImageName().contains("DIE")) {
			graphicsComponent.setImage(.4f, "Die", Sprite.FORWARD);
			attacking = false;
			return;
		} else {
			super.tick(deltaTime);

			if (attacking) graphicsComponent.setImage(.25f, "Attack_" + currentDirection, Sprite.FORWARD); // TODO if multiple attacks clicked, pingpong
			else if (physicsComponent.isMoving()) {
				graphicsComponent.setImage(walkCycleDuration, "Walk_" + currentDirection, Sprite.LOOP);
			} else graphicsComponent.setImage(1f, "Stand_" + currentDirection, Sprite.STILL_IMAGE);
		}
	}

	/**
	 * Change the Mob's HP by the given amount.
	 * 
	 * @param damage
	 *            - how much to change mob HP by
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
