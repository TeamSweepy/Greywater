
package com.teamsweepy.greywater.ui.item;

import com.teamsweepy.greywater.entities.components.Sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Item {

	/************** All these varialbes have to be overriden in subclasses **************/

	// TODO : We should eventualy set up a table somewhere for our IDs, since there will probably be quite a lot of them.
	public final int ID = 00;
	public final String name = "";

	protected Sprite sprite;

	public Item() {

	}

	public void render(SpriteBatch g, float x, float y) {
		if (sprite == null) {
			System.out.println("[ERROR] There is no Sprite assigned to " + name + " item");
			return;
		}
		render(g, x, y, sprite.getImageWidth(), sprite.getImageHeight());
	}

	public void render(SpriteBatch g, float x, float y, float w, float h) {
		if (sprite == null) {
			System.out.println("[ERROR] There is no Sprite assigned to " + name + " item");
			return;
		}
		sprite.render(g, x, y, w, h);
	}

	public Sprite getSprite() {
		return sprite;
	}
}
