
package com.teamsweepy.greywater.ui.item.potions;

import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.ui.item.IDs;
import com.teamsweepy.greywater.ui.item.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Mercury extends Item {

	public Mercury() {
		sprite = new Sprite("mercury");
	}

	@Override
	public void render(SpriteBatch g, float x, float y, float w, float h) {
		super.render(g, x, y, w, h);
	}

	@Override
	public int getID() {
		return IDs.MERCURY.getID();
	}

}
