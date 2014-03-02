
package com.teamsweepy.greywater.ui.item;

import com.teamsweepy.greywater.entities.components.Sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class HealthPotion extends Item {

	public final int ID = 01;
	public final String name = "Health Potion";

	public HealthPotion() {
		sprite = new Sprite("healthpotion");
	}

	@Override
	public void render(SpriteBatch g, float x, float y, float w, float h) {
		super.render(g, x, y, w, h);
	}
}
