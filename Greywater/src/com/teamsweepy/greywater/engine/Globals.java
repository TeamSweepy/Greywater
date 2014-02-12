/**
 * Functions and variables needed everywhere.
 */

package com.teamsweepy.greywater.engine;

import com.teamsweepy.math.Point2F;

public class Globals {

	//Directional data! Sprites can only face these directions.
	public static final int NORTH = 0;
	public static final int NORTHEAST = 1;
	public static final int EAST = 2;
	public static final int SOUTHEAST = 3;
	public static final int SOUTH = 4;
	public static final int SOUTHWEST = 5;
	public static final int WEST = 6;
	public static final int NORTHWEST = 7;

	//width and height of a tile in memory (not image size)
	public static float tileGridWidth = 50;
	public static float tileGridHeight = 50;

	//width and height of a tile in on screen
	public static float tileImageWidth = 56;
	public static float tileImageHeight = 112;
	
	public static float tileRatio = 1.12f;

	/**
	 * Convert objective coordinates to screen coordinates
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
	 */
	public static Point2F toNormalCoord(float xIso, float yIso) {
		float x = (2 * xIso + yIso) / 2;
		float y = (2 * yIso - xIso) / 2;
		//TODO fix this method so it's the proper inverse of toIsoCoord
		return new Point2F(x, y);
	}

	/**
	 * Converts objective coordinates to tile indices
	 */
	public static Point2F toTileIndices(float xCoord, float yCoord) {
		Point2F tileIndex = new Point2F(xCoord / tileGridWidth, yCoord / tileGridHeight);
		return tileIndex;
	}
}


