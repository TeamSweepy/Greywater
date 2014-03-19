/**
 * 
 */
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entity.component.Hitbox;
import com.teamsweepy.greywater.math.Point2F;


public class ButtonCircular extends SubGUIComponent {

	protected float radius;

	public ButtonCircular() {
		pos = new Point2F(0, 0);
		radius = 100;
		visible = false; // no need for empty render method, just flag imageless components
	}

	public ButtonCircular(float x, float y, float radius) {
		this.radius = radius;
		pos = new Point2F(x, y);

		hitbox = new Hitbox();
		visible = false;
	}

	public boolean intersects(Point2F mousePosition) {
		Point2F click = mousePosition.sub(pos);
		float distance = click.length();
		return distance < radius;
	}
}
