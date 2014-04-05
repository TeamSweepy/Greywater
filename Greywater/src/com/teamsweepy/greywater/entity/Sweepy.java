
package com.teamsweepy.greywater.entity;

import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.component.Entity;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.item.misc.VoltCell;
import com.teamsweepy.greywater.entity.item.misc.Wires;
import com.teamsweepy.greywater.entity.item.potions.HealthPotion;
import com.teamsweepy.greywater.entity.item.potions.Mercury;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.AIInventory;
import com.teamsweepy.greywater.ui.gui.GUI;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import java.awt.Point;
import java.util.ArrayList;


public class Sweepy extends Mob {

	boolean jitter;
	Mob lootMob;
	float lootTimeSeconds = 0f;
	private final float maxLootTimeSeconds = 7f;

	/**
	 * Creates a new player standing in the center of the tile specified.
	 * 
	 * @param x - Tile X Position, not objective position
	 * @param y - Tile Y Position, not objective position
	 */
	public Sweepy(float x, float y, Level level) {
		super("Sweepy", x, y, 20, 20, 3, level, true);
		graphicsComponent.setImage(1f, currentDirection, Sprite.STILL_IMAGE);
		inventory = new AIInventory(this);
		friendly = true;
		GUI.addGUIComponent(inventory);
		inventory.setVisible(false);
		killList = new ArrayList<Entity>();
	}

	/** Update graphics and physics components, deal with animation and behavior */
	public void tick(float deltaTime) {
		graphicsComponent.tick(deltaTime);
		physicsComponent.tick(deltaTime);
		getInput();
		if (lootMob != null) {
			lootTimeSeconds += deltaTime;
			if (lootTimeSeconds >= maxLootTimeSeconds) {
				lootMob = null;
				lootTimeSeconds = 0f;
			}
		}

		if (physicsComponent.isMoving()) {
			currentDirection = Globals.getDirectionString(physicsComponent.destination.x - getX(), physicsComponent.destination.y - getY());
		} else {
			physicsComponent.stopMovement();
		}
		graphicsComponent.setImage(1f, currentDirection, Sprite.STILL_IMAGE);
	}

	@Override
	protected void getInput() {
		if (!physicsComponent.isMoving() && !jitter && !pather.hasPath() && Globals.rand.nextBoolean() && lootMob == null && focusTarget != null ) {
			System.out.println("JITTER");
			pather.createPath(getTileLocation(), Globals.calculateRandomTileIndex(this.getTileLocation(), world, 5));
			jitter = true;
		} else {
			jitter = false;
			if (sendInteract()){
				System.out.println("INTERACTED");
				return;
			}
		}
		if (!physicsComponent.isMoving()) {
			Point newPoint = pather.getNextStep();
			if (newPoint != null) {
				Point2F newLoc = Globals.toNormalCoordFromTileIndices(newPoint.x, newPoint.y);
				physicsComponent.moveTo(newLoc.x, newLoc.y);
			} else if (lootMob == null && focusTarget != null) {
				pather.createPath(getTileLocation(), focusTarget.getTileLocation());
				jitter = false;
			}
		}

	}

	@Override
	public boolean sendInteract() {
		if (lootMob == null) {
			Rectangle thisRect = this.getHitbox();
			ArrayList<Rectangle> rectList = new ArrayList<Rectangle>();
			rectList.add(new Rectangle(thisRect.x - 1000, thisRect.y - 1000, 2000, 2000));
			ArrayList<Mob> nearMe = (ArrayList<Mob>) world.getCollidedMobs(rectList);
			for (Mob m : nearMe) {
				if (!m.isAlive() && !m.friendly && !killList.contains(m)) {
					lootMob = m;
					pather.createPath(getTileLocation(), m.getTileLocation());
					killList.add(lootMob);
					return true;
				} else {
					continue;
				}
			}
		} else {
			if (getLocation().distance(lootMob.getLocation()) < 50) {
				physicsComponent.stopMovement();
				if (!killList.contains(lootMob)) {
					killList.add(lootMob);
						if (Globals.rand.nextBoolean())
							inventory.addItem(new Mercury());
						if (Globals.rand.nextBoolean())
							inventory.addItem(new Mercury());
						if (Globals.rand.nextBoolean())
							inventory.addItem(new Mercury());
						if (Globals.rand.nextBoolean())
							inventory.addItem(new Wires());
						if (Globals.rand.nextBoolean())
							inventory.addItem(new HealthPotion());
						if (Globals.rand.nextBoolean())
							inventory.addItem(new VoltCell());
						if (Globals.rand.nextBoolean())
							inventory.	addItem(new VoltCell());
						if (Globals.rand.nextBoolean())
							inventory.addItem(new VoltCell());
					}

					//TODO vaccuum noise
					//todo add loot
				
			}
		}
		return false;
	}

	@Override
	public void receiveInteract(Mob interlocutor) {
		((Sound)AssetLoader.getAsset(Sound.class, "sweepy_chirp.wav")).play();

		if (interlocutor != focusTarget)
			follow(interlocutor);
		inventory.setVisible(true);
	}

	@Override
	protected void attack(Mob enemy) {} //Sweepy is a peaceful bot

	@Override
	public void executeAttack() {} //Sweepy is a peaceful bot

	@Override
	public void changeHP(float dmg) {} //Sweepy is an invincible bot

}
