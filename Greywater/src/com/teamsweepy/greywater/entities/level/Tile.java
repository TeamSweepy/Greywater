/**
 * Special entity that doesn't move. Used to indicate the floor. Floor needs hitboxes for things like tile activated traps or slower moving
 * terrain (mud).
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Barnes
 */

package com.teamsweepy.greywater.entities.level;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


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
	
	public void renderTransparent(SpriteBatch g){
		Sprite trans = new Sprite("Transparent");
		Point2F p = Globals.toIsoCoord(getX(), getY());
		trans.render(g, p.x, p.y);
		
	}


}
