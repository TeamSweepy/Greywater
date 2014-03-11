/**
 * 
 */

package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;


public class Button extends SubGUIComponent {
	
	//Commented out  logic here because it is literally the exact same as the superconstructor.
	//Please remove commented code if I was right to do this
	public Button() {
		super(0,0,100,100);
//		pos = new Point2F(0, 0);
//		size = new Point2F(100, 100);
//
//		hitbox = new Hitbox((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, 0f);
	}

//Commented out  logic here because it is literally the exact same as the superconstructor.
//Please remove commented code if I was right to do this
	public Button(float x, float y, float w, float h) {
		super(x,y,w,h);
//		pos = new Point2F(x, y);
//		size = new Point2F(w, h);
//		hitbox = new Hitbox((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, 0f);
	}

	/** Constructor for visible buttons! */
	public Button(float x, float y, String imageName) {
		pos = new Point2F(x,y);
		this.sprite = new Sprite("MenuItems", imageName);
		this.hitbox = new Hitbox();//sprite.get
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
}
