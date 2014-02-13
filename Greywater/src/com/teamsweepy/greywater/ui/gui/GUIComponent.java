
package com.teamsweepy.greywater.ui.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;

public class GUIComponent {

	public static float ratio = 1;

	protected Point2F pos;
	protected Point2F size;

	public Sprite sprite;

	public GUIComponent() {
		sprite = new Sprite("HUD-1600");
		pos = new Point2F(0, 0);
		size = new Point2F(Gdx.graphics.getWidth(), sprite.getImageHeight());
		size = new Point2F(1600, sprite.getImageHeight());
	}

	public void tick() {

	}

	public void render(SpriteBatch batch) {
		sprite.render(batch, -Camera.getDefault().xOffsetAggregate , -Camera.getDefault().yOffsetAggregate, size.getX(), size.getY());
	}
}
