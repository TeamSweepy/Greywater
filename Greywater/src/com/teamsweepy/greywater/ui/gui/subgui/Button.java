/**
 * 
 */

package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Button extends SubGUIComponent {


	public Button() {
		super(0, 0, 100, 100);
	}

	public Button(float x, float y, float w, float h) {
		super(x, y, w, h);
	}

	/** Constructor for visible buttons using texture atlases! */
	public Button(float x, float y, String atlasName, String imageName) {
		this.sprite = new Sprite(atlasName, imageName);
		visible = true;
		pos = new Point2F(x, y);
		size = new Point2F(sprite.getImageWidth(), sprite.getImageHeight());
		hitbox = new Hitbox(x, y, (int) sprite.getImageWidth(), (int) sprite.getImageHeight(), 0f);
	}
	
	/** Constructor for visible buttons using textures! */
	public Button(float x, float y, String imageName) {
		this.sprite = new Sprite(imageName);
		visible = true;
		pos = new Point2F(x, y);
		size = new Point2F(sprite.getImageWidth(), sprite.getImageHeight());
		hitbox = new Hitbox(x, y, (int) sprite.getImageWidth(), (int) sprite.getImageHeight(), 0f);
	}


	/** The method should be overridden when creating the button */
	protected void clicked() {
		System.out.println("Button clicked");
	}
	
	public void centerImage(float x, float y){
		float xLoc = x - size.x/2;
		float yLoc = y - size.y/2;
		pos = new Point2F(xLoc, yLoc);
		hitbox.setLocation(xLoc, yLoc);
		
	}

	public boolean intersects(Point2F mousePosition) {
		if (sprite != null) { //animate buttons with sprites
			if (getHitbox().intersects(mousePosition)) {
				sprite.changeSeriesPosition(100, -1);
			} else {
				sprite.changeSeriesPosition(-100, -1);
			}
		}
		return getHitbox().intersects(mousePosition);
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.render(batch, pos.x, pos.y, size.x, size.y);
	}
	
	@Override
	public void tick(float deltaTime){
		sprite.tick(deltaTime);
	}
}
