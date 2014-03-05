
package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.entities.components.ai.PathfinderMotor;
import com.teamsweepy.greywater.entities.level.Level;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Point;


public class Player extends Mob {

	private static Point2F mouseLocation;
	private static boolean mouseClicked;

	private PathfinderMotor pather;

	/**
	 * Creates a new player standing in the center of the tile specified.
	 * @param x - Tile X Position, not objective position
	 * @param y - Tile Y Position, not objective position
	 */
	public Player(float x, float y, Level level) {
		super(x * 50 + 25, y * 50 + 25, 35, 35, 1.25f, level);
		super.name = "Tavish";
		currentDirection = "South";
		this.graphicsComponent = new Sprite(name, "Stand_South");
		this.walkCycleDuration = 1;
		graphicsComponent.setImage(.6f, "Walk_South", Sprite.LOOP);

		pather = new PathfinderMotor(PathfinderMotor.Method.ASTAR);
		pather.updateMap(level);
	}

	@Override
	protected void getInput() {
		//PATHFINDING CODE
		if (mouseClicked) {
			mouseClicked = false;
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
	protected void attack(Mob enemy) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean interact() {
		// TODO Auto-generated method stub
		return false;
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



}
