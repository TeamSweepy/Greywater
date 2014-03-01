
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.GUIComponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class ProgressBar extends SubGUIComponent {

	public ProgressBar() {
		pos = new Point2F(0, 0);
		size = new Point2F(100, 100);

		hitbox = new Hitbox((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, 0f);
	}

	public ProgressBar(float x, float y, float w, float h) {
		pos = new Point2F(x, y);
		size = new Point2F(w, h);

		hitbox = new Hitbox((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, 0f);
	}



	public void input(Point2F mousePosition, int event) {}

	public void tick() {}

	public void render(SpriteBatch batch) {}

	public boolean intersects(Point2F mousePosition) {
		return getHitbox().intersects(mousePosition);
	}
}
