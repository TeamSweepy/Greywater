
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.ItemSlot;
import com.teamsweepy.greywater.ui.gui.subgui.Plane;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Inventory extends GUIComponent {

	public Inventory() {
		sprite = new Sprite("inventory");
		pos = new Point2F(1000, 200);
		size = new Point2F(437, 608);
		initSubComponents();

		visible = false;
	}

	@Override
	protected void initSubComponents() {
		subComponents.add(new Plane(pos.x, pos.y, size.x, size.y));
		subComponents.add(new ItemSlot(this, pos.x + 100, pos.y + 100, 70, 70));// test slot
	}

	@Override
	public void handleInput(Point2F mousePosition, int event) {
		super.handleInput(mousePosition, event);
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
	}

}
