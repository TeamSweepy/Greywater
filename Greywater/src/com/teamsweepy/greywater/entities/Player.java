
package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.entities.level.Level;

import com.badlogic.gdx.Gdx;


public class Player extends Mob {

	private float elTime = 0f;
	private boolean elTimes = true;

	/**
	 * Creates a new player standing in the center of the tile specified.
	 * @param x - Tile X Position, not objective position
	 * @param y - Tile Y Position, not objective position
	 */
	public Player(float x, float y, Level level) {
		super(x * 50, y * 50, 35, 35, 1f, level);
		super.name = "Tavish";
		currentDirection = "South";
		this.graphicsComponent = new Sprite(name, "Stand_South");
		this.walkCycleDuration = 1;
		graphicsComponent.setImage(.6f, "Walk_South", Sprite.LOOP);
	}

	@Override
	protected void getInput() {
		// TODO remove depthsort testing code
		elTime += Gdx.graphics.getDeltaTime();
		if (elTime > 10 && elTime < 15) {

			graphicsComponent.setImage(.6f, "Walk_East", Sprite.LOOP);
		}
		if (elTime > 20) {
			graphicsComponent.setImage(.6f, "Attack_East", Sprite.LOOP);
		}

		if (elTime > 7 && elTimes) {
			this.physicsComponent.moveTo(getX() + 1, getY() + 90);
			elTimes = !elTimes;
		}
		
		

	}

	@Override
	protected void attack(Mob enemy) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean interact() {
		// TODO Auto-generated method stub
		return false;
	}



}
