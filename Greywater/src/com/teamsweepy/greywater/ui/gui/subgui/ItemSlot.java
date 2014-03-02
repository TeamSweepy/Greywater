
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.Inventory;
import com.teamsweepy.greywater.ui.item.HealthPotion;
import com.teamsweepy.greywater.ui.item.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class ItemSlot extends SubGUIComponent {

	protected Inventory inventory; // inventory that the itemslot is in

	protected Item item; // item in the item slot

	public ItemSlot(Inventory inventory, float x, float y, float w, float h) {
		super(x, y, w, h);
		this.inventory = inventory;
		sprite = new Sprite("slot");
	}

	@Override
	protected void clicked() {
		System.out.println("Hi" + "clicked on the item slot");
		item = new HealthPotion();
	}

	@Override
	public void render(SpriteBatch g) {
		visible = inventory.isVisible();
		if (!visible)
			return;

		// render the slot
		float xOff = Camera.getDefault().xOffsetAggregate;
		float yOff = Camera.getDefault().yOffsetAggregate;
		sprite.render(g, pos.x - xOff, pos.y - yOff);

		// render the item in the slot
		if (item == null)
			return;
		item.render(g, pos.x - xOff, pos.y - yOff, size.x, size.y);
	}

	@Override
	public boolean intersects(Point2F mousePosition) {
		return getHitbox().intersects(mousePosition);
	}
}
