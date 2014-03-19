
package com.teamsweepy.greywater.ui.item.weapons;

import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.ui.item.IDs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Wrench extends Weapon {

	public Wrench() {
		graphicsComponent = new Sprite("tavwrench");
	}

	@Override
	public void render(SpriteBatch g, float x, float y, float w, float h) {
		super.render(g, x, y, w, h);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return IDs.WRENCH.getID();
	}

}
