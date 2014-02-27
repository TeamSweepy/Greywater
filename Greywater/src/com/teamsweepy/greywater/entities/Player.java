
package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.entities.level.Level;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.Gdx;

import java.awt.Point;


public class Player extends Mob {

	private float elTime = 0f;
	private boolean elTimes = true;

	private static Point2F mouseLocation;
	private static boolean mouseClicked;

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

		if(mouseClicked){
			Point2F objCo = Globals.toNormalCoord(mouseLocation.x - Camera.getDefault().xOffsetAggregate, mouseLocation.y - Camera.getDefault().yOffsetAggregate);
			Point tileCo = Globals.toTileIndices(objCo.x, objCo.y);
			System.out.println(objCo);
			System.out.println(tileCo);
			mouseClicked = false;
		}

//			//PATHFINDING CODE	
//			if (mouseClicked) {
//				Point startTile = Globals.toTileIndices(getLocation().x, getLocation().y);
//				Point clickedTile = Globals.toTileIndices(mouseLocation.x, mouseLocation.y);
//				pather.setNewPath(startTile, clickedTile);
//
//				Point newPoint = pather.getNextLoc();
//				if (newPoint != null) {
//					Point2F newLoc = Globals.toNormalCoordFromTileIndices(newPoint.x, newPoint.y);
//					physicsComponent.moveTo(newLoc.x, newLoc.y);
//				}
//
//			} else { //if no recent click, continue along pre-established path
//				if (!physicsComponent.isMoving()) {
//					Point newPoint = pather.getNextLoc();
//					if (newPoint != null) {
//						Point2F newLoc = Globals.toNormalCoordFromTileIndices(newPoint.x, newPoint.y);
//						physicsComponent.moveTo(newLoc.x, newLoc.y);
//					}
//				}
//			} //END PATHFINDING CODE


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
	public static void handleInput(Point2F screenLocation, boolean clicked, int keyCode){
		mouseClicked = clicked;
		mouseLocation = screenLocation;
		
		if(mouseLocation !=null || keyCode != -69){
			return; //TODO deal with key input when needed
		}
	}



}
