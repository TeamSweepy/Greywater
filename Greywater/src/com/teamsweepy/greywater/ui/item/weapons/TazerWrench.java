
package com.teamsweepy.greywater.ui.item.weapons;

import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.ui.item.IDs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TazerWrench extends Wrench {

	public TazerWrench() {
		sprite = new Sprite("tazerwrench");
	}

	@Override
	public void render(SpriteBatch g, float x, float y, float w, float h) {
		super.render(g, x, y, w, h);
	}

	@Override
	public int getID() {
		return IDs.TAZER_WRENCH.getID();
	}
}
