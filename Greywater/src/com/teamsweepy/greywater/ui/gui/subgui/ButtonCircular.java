
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class ButtonCircular extends SubGUIComponent {

	protected float radius;

	public ButtonCircular() {
		pos = new Point2F(0, 0);
		radius = 100;
	}

	public ButtonCircular(float x, float y, float radius) {
		this.radius = radius;
		pos = new Point2F(x, y);

		hitbox = new Hitbox();
	}

	public void render(SpriteBatch batch) {}

	public boolean intersects(Point2F mousePosition) {
		Point2F click = mousePosition.sub(pos);
		float distance = click.lenght();
		return distance < radius;
	}
}
