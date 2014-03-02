
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Plane extends SubGUIComponent {

	public Plane() {
		super(0, 0, 100, 100);
	}

	public Plane(float x, float y, float w, float h) {
		super(x, y, w, h);
		sprite = new Sprite("healthpotion");
	}

	protected void clicked() {}

	public void render(SpriteBatch g) {}

	@Override
	public boolean intersects(Point2F mousePosition) {
		return getHitbox().intersects(mousePosition);
	}

	/* public boolean intersects(Point2F mousePosition) { return getHitbox().intersects(mousePosition); } */
}
