
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.engine.input.InputGUI;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.GUIComponent;


public class SubGUIComponent extends GUIComponent {

	protected Hitbox hitbox;

	public SubGUIComponent() {
		super();
	}

	public SubGUIComponent(float x, float y, float w, float h) {
		pos = new Point2F(x, y);
		size = new Point2F(w, h);
		hitbox = new Hitbox((int) x, (int) y, (int) w, (int) h, 0f);
	}

	public void handleInput(Point2F mousePosition, int event) {
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
	
	@Override
	public boolean intersects(Point2F mousePosition) {
		System.out.println("[ERROR] this method needs to be overridden ( intersects(Point2F mouseposition) )" + this);
		return false;
	}

	protected void clicked() {

	}

	public Hitbox getHitbox() {
		return hitbox;
	}

}
