
package com.teamsweepy.greywater.entities.level;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;


public class Tile extends Entity {

	/**
	 * Constructor!
	 * 
	 * @param graphicsComponent - tile sprite that represents this bit of terrain
	 * @param xPos - objective coordinates
	 * @param yPos - objective coordinates
	 * @param widthAndHeight - width of a tile on the grid, not isometric. Tiles are squares, width = height
	 */
	public Tile(Sprite graphicsComponent, float xPos, float yPos, int widthAndHeight) {
		super();
		this.graphicsComponent = graphicsComponent;
		// speed is 0 because tiles don't move, silly
		this.physicsComponent = new Hitbox(xPos, yPos, widthAndHeight, widthAndHeight, 0);
	}

	/**
	 * Constructor!
	 * 
	 * @param graphicsComponent - tile sprite that represents this bit of terrain
	 * @param xPos - objective coordinates
	 * @param yPos - objective coordinates
	 * @param widthAndHeight - width of a tile on the grid, not isometric. Tiles are squares, width = height
	 */
	public Tile(TextureRegion tex, float xPos, float yPos, int widthAndHeight) {
		this.graphicsComponent = new Sprite(tex);
		// speed is 0 because tiles don't move, silly
		this.physicsComponent = new Hitbox(xPos, yPos, widthAndHeight, widthAndHeight, 0);
	}


}
