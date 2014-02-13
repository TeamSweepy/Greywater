package com.teamsweepy.greywater.ui.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;

public class HUD extends GUIComponent {

	public HUD() {
		sprite = new Sprite("HUD-1600");
		pos = new Point2F(0, 0);

		ratio = Gdx.graphics.getWidth() / sprite.getImageWidth();

		size = new Point2F(Gdx.graphics.getWidth(), sprite.getImageHeight() * ratio);
	}

	public void tick() {

	}

	public void render(SpriteBatch batch) {
		super.render(batch);
	}
}
