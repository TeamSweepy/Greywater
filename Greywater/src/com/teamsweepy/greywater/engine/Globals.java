/**
 * Functions and variables needed everywhere.
 * 
 * @author - Barnes
 */

package com.teamsweepy.greywater.engine;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.math.Vector2;

import java.awt.Point;
import java.util.Random;

public class Globals {

	//width and height of a tile in memory (not image size)
	public static float tileGridWidth = 50;
	public static float tileGridHeight = 50;

	//width and height of a tile in on screen
	public static float tileImageWidth = 56;
	public static float tileImageHeight = 112;

	//Width of tile in memory as compared to its size on screen
	//(world units to pixels)
	public static float tileRatio = 1.12f;

	public static Random rand = new Random(); //used for dicerolls

	/** Convert Isometric Coordinates to Screen Coordinates (Accounts for Camera Movement) */
	public static Point2F toScreenCoord(Point2F isoLocation) {
		return toScreenCoord(isoLocation.x, isoLocation.y);
	}

	/** Convert Isometric Coordinates to Screen Coordinates (Accounts for Camera Movement) */
	public static Point2F toScreenCoord(float xIso, float yIso) {
		return new Point2F(xIso + Camera.getDefault().xOffsetAggregate, yIso + Camera.getDefault().yOffsetAggregate);
	}

	/** Convert objective coordinates to isometric coordinates */
	public static Point2F toIsoCoord(Point2F objectiveLocation) {
		return toIsoCoord(objectiveLocation.x, objectiveLocation.y);
	}

	/** Convert objective coordinates to isometric coordinates */
	public static Point2F toIsoCoord(float xCoord, float yCoord) {
		//reverse the x and y because Tiled Map Editor uses a wacky coordinate system
		//multiply x and y by the ratio of hitbox to sprite size
		float temp = xCoord * tileRatio;
		xCoord = yCoord * tileRatio;
		yCoord = -temp;

		//standard isometric math
		float x = xCoord - yCoord;
		float y = (xCoord + yCoord) / 2;

		//get actual screen coordinates 
		return new Point2F(x, y);
	}

	/** Convert screen coordinates to flatspace/objective coordinates */
	public static Point2F toNormalCoord(Point2F isoLocation) {
		return toNormalCoord(isoLocation.x, isoLocation.y);
	}

	/** Convert screen coordinates to flatspace/objective coordinates */
	public static Point2F toNormalCoord(float xIso, float yIso) {
		//reverse the x and y because Tiled Map Editor uses a wacky coordinate system
		//divide x and y by the ratio of hitbox to sprite size
		float temp = (xIso - Camera.getDefault().xOffsetAggregate ) / tileRatio;
		xIso = -(yIso - Camera.getDefault().yOffsetAggregate) / tileRatio;
		yIso = temp;

		float x = xIso + yIso / 2;
		float y = yIso / 2 - xIso;

		//+- 50 because that is the size of the tile in objective coords
		return new Point2F(x+25, y-25);
	}

	/** Converts objective coordinates to tile indices */
	public static Point toTileIndices(Point2F objectiveLocation) {
		return toTileIndices(objectiveLocation.x, objectiveLocation.y);
	}

	/** Converts objective coordinates to tile indices */
	public static Point toTileIndices(float xCoord, float yCoord) {
		Point tileIndex = new Point((int) Math.floor(xCoord / tileGridWidth), (int) Math.floor(yCoord / tileGridHeight));
		return tileIndex;
	}

	/** Converts tile indices to objective coordinates */
	public static Point2F toNormalCoordFromTileIndices(Point2F objectiveLocation) {
		return toNormalCoordFromTileIndices(objectiveLocation.x, objectiveLocation.y);
	}

	/** Converts tile indices to objective coordinates */
	public static Point2F toNormalCoordFromTileIndices(float xCoord, float yCoord) {
		//+25 to indicate the center of a tile
		Point2F location = new Point2F((xCoord * tileGridWidth) + 25 , (yCoord * tileGridHeight) + 25);
		return location;
	}
	
	public static String getDirectionString(Mob target, Mob directionGetter){
		Vector2 centerLoc = new Vector2();
		target.getHitbox().getCenter(centerLoc);
		float tX = centerLoc.x; //targetX
		float tY = centerLoc.y;
		directionGetter.getHitbox().getCenter(centerLoc);

		float x = centerLoc.x;
		float y = centerLoc.y;
		return getDirectionString(tX - x, tY - y);
	}

	/**
	 * Gives the value of the entity's direction using angles
	 * 
	 * @param xDiff - TargetX - CurrentX
	 * @param yDiff - TargetY - CurrentY
	 */
	public static String getDirectionString(double xDiff, double yDiff) {
		double angle = Math.toDegrees(Math.atan2(yDiff, xDiff)) - 45;
		if (angle < 0)
			angle += 360;
		if (angle >= 337.5 || angle < 22.5)
			return "East";
		else if (angle >= 22.5 && angle < 67.5)
			return "Northeast";
		else if (angle >= 67.5 && angle < 112.5)
			return "North";
		else if (angle >= 112.5 && angle < 157.5)
			return "Northwest";
		else if (angle >= 157.5 && angle < 202.5)
			return "West";
		else if (angle >= 202.5 && angle < 247.50)
			return "Southwest";
		else if (angle >= 247.5 && angle < 292.5)
			return "South";
		else if (angle >= 292.5 && angle < 337.5)
			return "Southeast";

		return "666"; //if the direction cannot be found, Satan.
	}

	/**
	 * Roll a dice with the specified number of sides. Returns an integer from 1 to "dice", inclusive.
	 */
	public static int D(int dice) {
		return rand.nextInt(dice) + 1;
	}
	
	/** Generates a random valid point in objective coordinates for items and people 
	 * 
	 * @param Level - the level to generate a random point in
	 * @param tileRadius - how many tiles away the point can be from the original point
	 **/
	public static Point2F calculateRandomLocation(Point2F location, Level level, float tileRadius){
		float newX;
		float newY;
		Point loc;
		do{
		 newX = location.x + Globals.rand.nextInt((int)(50+50*tileRadius))*Integer.signum(Globals.rand.nextInt());
		 newY = location.y + Globals.rand.nextInt((int)(50+50*tileRadius))*Integer.signum(Globals.rand.nextInt());
		 loc = toTileIndices(newX, newY);
		} while(!level.isTileWalkable(loc.x, loc.y));
			
		return new Point2F(newX, newY);
	}
	
	/** Generates a random valid point in objective coordinates for items and people 
	 * 
	 * @param Level - the level to generate a random point in
	 * @param tileRadius - how many tiles away the point can be from the original point
	 **/
	public static Point calculateRandomTileIndex(Point location, Level level, int tileRadius){
		int newX;
		int newY;
		Point loc;
		do{
		 newX = location.x + Globals.rand.nextInt(tileRadius)*Integer.signum(Globals.rand.nextInt());
		 newY = location.y + Globals.rand.nextInt(tileRadius)*Integer.signum(Globals.rand.nextInt());
		
		} while(!level.isTileWalkable(newX, newY));
			
		return new Point(newX, newY);
	}
}
