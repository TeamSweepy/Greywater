
package com.teamsweepy.greywater.entity;

import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.component.Entity;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.math.Point2I;
import com.teamsweepy.greywater.ui.gui.AIInventory;

import com.badlogic.gdx.audio.Sound;
import com.teamsweepy.greywater.utils.SoundManager;

public class Vagrant extends Mob {

	boolean jitter;

	/**
	 * Creates a new Vagrant standing in the center of the tile specified.
	 * 
	 * @param x - Tile X Position, not objective position
	 * @param y - Tile Y Position, not objective position
	 * @param focus - whoever (player) the watchman is pursuing
	 */
	public Vagrant(float x, float y, Level level, Entity focus) {
		super("Vagrant", x, y, 35, 35, 2.8f, level, true);
		currentDirection = "South";
		this.walkCycleDuration = .58f;
		graphicsComponent.setImage(.6f, "Walk_South", Sprite.LOOP);
		focusTarget = focus;
		inventory = new AIInventory(this);
	}

	@Override
	protected void getInput() {
		if (attacking || sendInteract())
			return;

		if (!physicsComponent.isMoving() && !jitter && !pather.hasPath() && !canSeeTarget()) {
			pather.createPath(getTileLocation(), Globals.calculateRandomTileIndex(this.getTileLocation(), world, 20));
			if(!pather.hasPath()){
				pather.createPath(getTileLocation(), Globals.calculateRandomTileIndex(this.getTileLocation(), world, 10));
			}
			jitter = true;
		} else {
			jitter = false;

		}

		if (!physicsComponent.isMoving() && canSeeTarget() && Globals.rand.nextBoolean()) {
			if (Globals.rand.nextBoolean() && Globals.rand.nextBoolean() && Globals.rand.nextBoolean() && Globals.rand.nextBoolean()) {
				pather.createPath(Globals.toTileIndices(getLocation()), ((Mob) focusTarget).getFinalDestination());
			}
			pather.createPath(Globals.toTileIndices(getLocation()), Globals.toTileIndices(focusTarget.getLocation()));

			Point2I newPoint = pather.getNextStep();
			if (newPoint != null) {
				Point2F newLoc = Globals.toNormalCoordFromTileIndices(newPoint.x, newPoint.y);
				physicsComponent.moveTo(newLoc.x, newLoc.y);
			} else {
				pather.reset();
			}
		} else { //if no recent click, continue along pre-established path
			if (!physicsComponent.isMoving()) {
                Point2I newPoint = pather.getNextStep();
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

        String sound_file = "TAVISH_ATTACK_" + (Globals.rand.nextInt(3) + 1 ) + ".wav";
        SoundManager.playSound(sound_file);

		physicsComponent.stopMovement();
		pather.reset();
		this.currentDirection = Globals.getDirectionString(enemy, this);
		attacking = true;

		int chanceToHit = Globals.D(20); //20 sided dice, bitch
		missing = !(chanceToHit > ((Mob) focusTarget).getArmor());

	}

	@Override
	public boolean sendInteract() {
		// Point2D p = Globals.getIsoCoords(getX() + spriteXOff, getY() + spriteYOff);
		if (focusTarget.getLocation().distance(getLocation()) < 60 && ((Mob) focusTarget).isAlive()) {
			if (!attacking) {
				attack((Mob) focusTarget);
				return true;
			} else {
				System.out.println("already attacking");
			}
		}
		return false;
	}

	@Override
	public void executeAttack() {
		int damage = Globals.D(5);
		((Mob) focusTarget).changeHP(damage);
		missing = true;
	}

	@Override
	public void receiveInteract(Mob interlocutor) {}

}
