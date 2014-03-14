
package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.entities.level.Level;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.math.Vector2;

import java.awt.Point;


public class Watchman extends Mob {

	public Watchman(float x, float y, Level level, Entity focus) {
		super("Tavish", x, y, 35, 35, 1.3f, level, true);
		currentDirection = "South";
		this.walkCycleDuration = 1;
		graphicsComponent.setImage(.6f, "Walk_South", Sprite.LOOP);
		focusTarget = focus;
	}

	@Override
	protected void getInput() {
		if (attacking || interact())
			return;

		if (!physicsComponent.isMoving() && canSeeTarget()) {
			pather.createPath(Globals.toTileIndices(getLocation()), Globals.toTileIndices(focusTarget.getLocation()));

			Point newPoint = pather.getNextStep();
			if (newPoint != null) {
				Point2F newLoc = Globals.toNormalCoordFromTileIndices(newPoint.x, newPoint.y);
				physicsComponent.moveTo(newLoc.x, newLoc.y);
			}
		} else { //if no recent click, continue along pre-established path
			if (!physicsComponent.isMoving()) {
				Point newPoint = pather.getNextStep();
				if (newPoint != null) {
					Point2F newLoc = Globals.toNormalCoordFromTileIndices(newPoint.x, newPoint.y);
					physicsComponent.moveTo(newLoc.x, newLoc.y);
				}
			}
		}
	}

	@Override
	protected void attack(Mob enemy) {

		if (enemy == null || attacking)
			return;

		System.out.println(name + " attacked " + (enemy).name);

		physicsComponent.stopMovement();

		Vector2 centerLoc = new Vector2();
		enemy.getHitbox().getCenter(centerLoc);
		float tX = centerLoc.x; //targetX
		float tY = centerLoc.y;
		getHitbox().getCenter(centerLoc);

		float x = centerLoc.x;
		float y = centerLoc.y;

		this.currentDirection = Globals.getDirectionString(tX - x, tY - y);
		attacking = true;

		int damage = 0;

		int chanceToHit = Globals.D(20); //20 sided dice, bitch
		System.out.println(this.name + " rolled " + chanceToHit + " to hit " + enemy.name);
		if (chanceToHit > 8) {
			damage += Globals.D(8);
			enemy.changeHP(damage);
			System.out.println(name + " hit " + enemy.name + " for " + damage + " damage...");
			System.out.println(enemy.getHP());
		}

	}

	@Override
	public boolean interact() {
		// Point2D p = Globals.getIsoCoords(getX() + spriteXOff, getY() + spriteYOff);
		if (focusTarget.getLocation().distance(getLocation()) < 60 && ((Mob) focusTarget).isAlive()) {
			if (!attacking) {
				attack((Mob) focusTarget);
				return true;
			} else {
				System.out.println("already attacking");//TODO QUEUE UP NEXT ATTACK
			}
		}

		return false;
	}

}
