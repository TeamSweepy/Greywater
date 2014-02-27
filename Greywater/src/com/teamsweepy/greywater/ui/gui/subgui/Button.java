
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.engine.input.InputGUI;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.GUIComponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Button extends GUIComponent {

	public Button() {
		pos = new Point2F(0, 0);
		size = new Point2F(100, 100);
		size = size.mul(ratio);

		hitbox = new Hitbox((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, 0f);
	}

	public Button(float x, float y, float w, float h) {
		pos = new Point2F(x, y);
		size = new Point2F(w, h);
		size = size.mul(ratio);

		hitbox = new Hitbox((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, 0f);
	}


	public void handleInput(Point2F mousePosition, int event) {
		if (!intersects(mousePosition))
			return;

		switch (event) {
			case InputGUI.MOUSE_DOWN:
				clicked();
				break;
			case InputGUI.MOUSE_MOVED:
				break;
			case InputGUI.MOUSE_UP:
				break;
		}
	}

	/**
	 * The method should be overridden when creating the button
	 * */
	protected void clicked() {
		System.out.println("Button clicked");
	}

	public void render(SpriteBatch batch) {
		// Empty since we are curently rendering buttons with the rest of the GUI, or the parent of this
	}

	public boolean intersects(Point2F mousePosition) {
		return getHitbox().intersects(mousePosition);
	}
}
