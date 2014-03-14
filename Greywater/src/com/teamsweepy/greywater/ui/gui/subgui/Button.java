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

	/** Constructor for visible buttons! */
	public Button(float x, float y, float w, float h, String imageName) {
		super(x, y, w, h);// I (Žiga) added this for hitbox initialization.

		this.sprite = new Sprite("MenuItems", imageName);
		visible = true;
	}

	/** The method should be overridden when creating the button */
	protected void clicked() {
		System.out.println("Button clicked");
	}

	//TODO this method doesn't yet work.
	public boolean intersects(Point2F mousePosition) {
		if (sprite != null) { //animate buttons with sprites
			if (getHitbox().intersects(mousePosition)) {
				sprite.setImage(.6f, "", Sprite.LOOP);
			} else {
				sprite.setImage(.6f, "", Sprite.STILL_IMAGE);
			}
		}
		return getHitbox().intersects(mousePosition);
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.render(batch, pos.x - Camera.getDefault().xOffsetAggregate, pos.y - Camera.getDefault().yOffsetAggregate, size.x, size.y);
	}
}
