/**
 * Functions and variables needed everywhere.
 */

package com.teamsweepy.greywater.engine;

import com.teamsweepy.greywater.math.Point2F;

import java.awt.Point;

public class Globals {

	//width and height of a tile in memory (not image size)
	public static float tileGridWidth = 50;
	public static float tileGridHeight = 50;

	//width and height of a tile in on screen
	public static float tileImageWidth = 56;
	public static float tileImageHeight = 112;

	public static float tileRatio = 1.12f;

	/**
	 * Convert objective coordinates to screen coordinates
	 * VERIFIED OUTPUT
	 */
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

	/**
	 * Convert screen coordinates to flatspace/objective coordinates
	 * VERIFIED OUTPUT
	 */
	public static Point2F toNormalCoord(float xIso, float yIso) {
		//reverse the x and y because Tiled Map Editor uses a wacky coordinate system
		//divide x and y by the ratio of hitbox to sprite size
		float temp = xIso / tileRatio;
		xIso = -yIso / tileRatio;
		yIso = temp;

		float x = xIso + yIso / 2;
		float y = yIso / 2 - xIso;
		//+- 50 because that is the size of the tile in objective coords
		return new Point2F(x + 50, y- 50);
	}

	/**
	 * Converts objective coordinates to tile indices
	 * VERIFIED OUTPUT
	 */
	public static Point toTileIndices(float xCoord, float yCoord) {
		Point tileIndex = new Point((int)Math.floor(xCoord / tileGridWidth), (int)Math.floor(yCoord / tileGridHeight));
		return tileIndex;
	}
	
	/**
	 * Converts objective coordinates to tile indices
	 */
	public static Point2F toNormalCoordFromTileIndices(float xCoord, float yCoord) {
		Point2F location = new Point2F((xCoord * tileGridWidth), (yCoord * tileGridHeight));
		return location;
	}

	/**
	 * Gives the value of the entity's direction using angles
	 * 
	 * @param xDiff - TargetX - CurrentX
	 * @param yDiff - TargetY - CurrentY
	 */
	public static String getDirectionString(double xDiff, double yDiff) {
		double angle = (-1) * Math.toDegrees(Math.atan2(yDiff, xDiff)) - 45;
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


		return "666";
	}
}
