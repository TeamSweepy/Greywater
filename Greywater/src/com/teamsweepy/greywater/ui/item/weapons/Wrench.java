
package com.teamsweepy.greywater.ui.item.weapons;

import com.teamsweepy.greywater.entities.components.Sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Wrench extends Weapon {

	public final int ID = 04;
	public final String name = "Wrench";

	public Wrench() {
		sprite = new Sprite("tavwrench");
	}

	@Override
	public void render(SpriteBatch g, float x, float y, float w, float h) {
		super.render(g, x, y, w, h);
	}
}
