
package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.entities.level.Level;
import com.teamsweepy.greywater.entities.level.Tile;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.ProgressBarCircular;

import com.badlogic.gdx.math.Vector2;

import java.awt.Point;
import java.util.ArrayList;


public class Player extends Mob {

	private static Point2F mouseLocation;
	private static boolean mouseClicked;
	private static Player localPlayer;

	private ArrayList<Entity> killList;

	private ProgressBarCircular healthBar;
	private ProgressBarCircular manaBar;

	public static Player getLocalPlayer() {
		return localPlayer;
	}

	/**
	 * Creates a new player standing in the center of the tile specified.
	 * @param x - Tile X Position, not objective position
	 * @param y - Tile Y Position, not objective position
	 */
	public static Player initLocalPlayer(float x, float y, Level level) {
		localPlayer = new Player(x, y, level);
		return localPlayer;
	}

	/**
	 * Creates a new player standing in the center of the tile specified.
	 * @param x - Tile X Position, not objective position
	 * @param y - Tile Y Position, not objective position
	 */
	private Player(float x, float y, Level level) {
		super("Tavish", x, y, 35, 35, 1.25f, level, true);
		//		super("Tavish", x, y, 35, 35, 10f, level, true);
		currentDirection = "South";
		this.walkCycleDuration = 1;
		killList = new ArrayList<Entity>();
		graphicsComponent.setImage(3f, "Walk_South", Sprite.LOOP);
	}

	@Override
	public void tick(float deltaTime) {
		super.tick(deltaTime);

		if (healthBar != null && manaBar != null) {
			System.out.println(HP );
			healthBar.setValue(HP);
			manaBar.setValue(0);
		}
	}

	@Override
	protected void getInput() {
		//PATHFINDING CODE
		if (mouseClicked) {
			mouseClicked = false;

			if (attacking || interact()) //no need to walk if you're fighting/talking
				return;

			Point startTile = Globals.toTileIndices(getLocation().x, getLocation().y);
			Point2F objectiveClick = Globals.toNormalCoord(mouseLocation.x, mouseLocation.y);
			Point clickedTile = Globals.toTileIndices(objectiveClick.x, objectiveClick.y);
			System.out.println("Player starts at " + startTile);
			System.out.println("Clicked to move to " + clickedTile);
			pather.createPath(startTile, clickedTile);

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
		} //END PATHFINDING CODE
	}

	@Override
	public boolean interact() {
		Entity interacted = (Entity) world.getClickedEntity(mouseLocation, this);

		focusTarget = null;
		if (interacted == null) {
			return false;
		}
		System.out.println(interacted.getClass());
		if (interacted.getClass().getSuperclass() == Mob.class) { //deal with mobs
			Mob interactedMob = (Mob) interacted;
			if (interactedMob.isAlive() && !interactedMob.friendly) { //attack the living enemy
				focusTarget = interactedMob;
				attack(interactedMob);

			} else if (interactedMob.friendly) { //interact with friends
				//				if(interactedMob.getClass() == Sweepy.class){
				//
				//				} else{
				//					((NPC)interactedMob).interact(this);
				//				}

			} else { //clicked a dead guy
				return false;
			}


		}//end mob interaction

		//		if (interacted.getClass() == Item.class) { //pickup loot
		//			//some day, item logic will go here.
		//		}

		if (interacted.getClass() == Tile.class) {
			//someday door logic will go here
		}
		return true;
	}

	@Override
	protected void attack(Mob enemy) {
		if (enemy == null || attacking)
			return;

		System.out.println(name + " attacked " + (enemy).name);
		physicsComponent.stopMovement();

		if (enemy.getLocation().distance(getLocation()) > getWidth() * 2.5) { //if cant reach
			pather.createPath(Globals.toTileIndices(this.getLocation()), Globals.toTileIndices(enemy.getLocation()));
			Point newPoint = pather.getNextStep();
			newPoint = pather.getNextStep();

			if (newPoint != null) {
				Point2F newLoc = Globals.toNormalCoordFromTileIndices(newPoint.x, newPoint.y);
				physicsComponent.moveTo(newLoc.x, newLoc.y);
			}
			return;
		}

		attacking = true;
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
			damage += Globals.D(100);
			enemy.changeHP(damage);
			System.out.println(name + " hit " + enemy.name + " for " + damage + " damage...");
			if (!enemy.isAlive()) {
				killList.add(enemy);
			}
		}
	}

	/**
	 * Sets local player input variables. Used as a callback.
	 */
	public static void handleInput(Point2F screenLocation, boolean clicked, int keyCode) {
		mouseClicked = clicked;
		mouseLocation = screenLocation;

		if (mouseLocation != null || keyCode != -69) {
			return; //TODO deal with key input when needed
		}
	}

	public void setBars(ProgressBarCircular hp, ProgressBarCircular mana) {
		this.healthBar = hp;
		this.manaBar = mana;
	}

}
