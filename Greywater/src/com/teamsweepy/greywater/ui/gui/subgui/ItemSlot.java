
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.Cursor;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.Inventory;
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
	/**
	 * Handle on click. Move the items from and to the cursor when needed
	 * */
	protected void clicked() {
		Cursor cursor = GUI.getCursor();

		if (item == null) {

			if (cursor.getItem() != null) { // move the item from the cursor into the slot
				this.item = cursor.getItem();
				cursor.setItem(null);
			}

		} else if (item != null) { // there is an item in the slot

			if (cursor.getItem() == null) {// move the item from the slot to the cursor
				cursor.setItem(item);
				this.item = null;

			} else { // swap the items
				Item temp = item;
				item = cursor.getItem();
				cursor.setItem(temp);
			}
		}
	}

	@Override
	public void render(SpriteBatch g) {
		visible = inventory.isVisible();
		if (!visible)
			return;

		// render the slot
		sprite.render(g, pos.x, pos.y);

		// render the item in the slot
		if (item == null)
			return;
		item.render(g, pos.x, pos.y);
	}

	@Override
	public boolean intersects(Point2F mousePosition) {
		return getHitbox().intersects(mousePosition);
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
