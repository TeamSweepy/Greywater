
package com.teamsweepy.greywater.entity;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.component.Entity;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.Inventory;

import com.badlogic.gdx.math.Vector2;

import java.awt.Point;


public class Watchman extends Mob {

	public Watchman(float x, float y, Level level, Entity focus) {
		super("Watchman", x, y, 35, 35, 1.3f, level, true);
		currentDirection = "South";
		this.walkCycleDuration = 1;
		graphicsComponent.setImage(.6f, "Walk_South", Sprite.LOOP);
		focusTarget = focus;
		inventory = new Inventory(this);
	}

	@Override
	protected void getInput() {
		if (attacking || interact())
			return;

		if (!physicsComponent.isMoving() && canSeeTarget() && Globals.rand.nextBoolean()) {
			if(Globals.rand.nextBoolean() && Globals.rand.nextBoolean() && Globals.rand.nextBoolean() && Globals.rand.nextBoolean()){
				pather.createPath(Globals.toTileIndices(getLocation()), ((Mob) focusTarget).getFinalDestination());
			}
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
		physicsComponent.stopMovement();
		pather.reset();
		this.currentDirection = Globals.getDirectionString(enemy, this);
		attacking = true;

		int chanceToHit = Globals.D(20) + 4; //20 sided dice, bitch
		if (chanceToHit > ((Mob)focusTarget).getArmor()) {
			
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

	@Override
	public void executeAttack() {
		int damage = Globals.D(8);
		((Mob)focusTarget).changeHP(damage);
	}

}
