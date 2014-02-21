
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class ButtonCircular extends Button {

	protected float radius;

	public ButtonCircular() {
		pos = new Point2F(0, 0);
		radius = 100 * ratio;
	}

	public ButtonCircular(float x, float y, float radius) {
		this.radius = radius * ratio;
		pos = new Point2F(x, y);
		size = size.mul(ratio);

		hitbox = new Hitbox((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, 0f);
	}

	public void render(SpriteBatch batch) {}

	public boolean intersects(Point2F mousePosition) {
		Point2F click = mousePosition.sub(pos);
		float distance = click.lenght();
		return distance < radius;
	}
}
