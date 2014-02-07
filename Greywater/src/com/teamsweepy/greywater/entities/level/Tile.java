
package com.teamsweepy.greywater.entities.level;

import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Tile extends Entity {

	/**
	 * Constructor!
	 * 
	 * @param graphicsComponent - tile sprite that represents this bit of terrain
	 * @param xPos -y location in 2D flatspace
	 * @param yPos -x location in 2D flatspace
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
	 * @param xPos -y location in 2D flatspace
	 * @param yPos -x location in 2D flatspace
	 * @param widthAndHeight - width of a tile on the grid, not isometric. Tiles are squares, width = height
	 */
	public Tile(TextureRegion tex, float xPos, float yPos, int widthAndHeight) {
		this.graphicsComponent = new Sprite(tex);
		// speed is 0 because tiles don't move, silly
		this.physicsComponent = new Hitbox(xPos, yPos, widthAndHeight, widthAndHeight, 0);
	}
	
	public void render(SpriteBatch g){
		Point2F p = mainCamera.toIsoCoord((graphicsComponent.getImageWidth()/2)*getY()/getWidth(), graphicsComponent.getImageHeight()*-getX()/getHeight());
		graphicsComponent.render(g, p.x, p.y);
	}


}
