
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.Gdx;


public class Inventory extends GUIComponent {

	public Inventory() {
		sprite = new Sprite("HUD-1600");
		pos = new Point2F(0, 0);
		size = new Point2F(Gdx.graphics.getWidth(), sprite.getImageHeight());
		size = new Point2F(1600, sprite.getImageHeight());

		hitbox = new Hitbox((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, 0f);
	}
}
