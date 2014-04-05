/**
 * Special entity that doesn't move. Used to indicate the floor. Floor needs hitboxes for things like tile activated traps or slower moving
 * terrain (mud).
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Barnes
 */

package com.teamsweepy.greywater.entity.level;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.component.Entity;
import com.teamsweepy.greywater.entity.component.Hitbox;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Tile extends Entity {
	
	private boolean transparent;
	private Sprite trans;
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
		this.physicsComponent = new Hitbox(xPos, yPos, widthAndHeight, widthAndHeight, 0, true);
	}
	
	public Tile(TextureRegion tex, float xPos, float yPos, int widthAndHeight, boolean walkable) {
		super();
		this.graphicsComponent = new Sprite(tex);
		// speed is 0 because tiles don't move, silly
		this.physicsComponent = new Hitbox(xPos, yPos, widthAndHeight, widthAndHeight, 0, !walkable);
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
		this.physicsComponent = new Hitbox(xPos, yPos, widthAndHeight, widthAndHeight, 0, true);
	}
	
	public void render(SpriteBatch g){
		if(transparent){
			renderTransparent(g);
		} else {
			super.render(g);
		}
	}
	
	public void renderTransparent(SpriteBatch g){
		Point2F p = Globals.toIsoCoord(getX(), getY());
		trans.render(g, p.x, p.y);
		transparent = false;
	}
	
	public void setTransparency(boolean tr){
		if(trans == null){
			trans = new Sprite("Transparent", true);
		}
		transparent = tr;
	}
	
	public void setTransparency(boolean tr, boolean x){
		if(trans == null){
			trans = new Sprite("Transparent", true);
		}
		transparent = tr;
		if(x){
			if(!trans.getCurrentImageName().equalsIgnoreCase("Transparent_X")){
				trans.setImage("Transparent_X");
			}
		}else{
			if(!trans.getCurrentImageName().equalsIgnoreCase("Transparent_Y")){
				trans.setImage("Transparent_Y");
			}
		}
	}
	
	public boolean isTransparent(){
		return transparent;
	}


}
