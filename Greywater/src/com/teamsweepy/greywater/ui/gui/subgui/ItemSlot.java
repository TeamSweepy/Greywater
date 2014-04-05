
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.component.events.FetchEvent;
import com.teamsweepy.greywater.entity.item.Chargeable;
import com.teamsweepy.greywater.entity.item.Item;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.Cursor;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.GUIComponent;
import com.teamsweepy.greywater.ui.gui.Inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class ItemSlot extends GUIComponent {

	protected Inventory inventory; // inventory that the itemslot is in

	protected Item item; // item in the item slot

	public ItemSlot(Inventory inventory, float x, float y, float w, float h) {
		super(x, y, w, h);
		this.inventory = inventory;
		sprite = new Sprite("slot", true);
	}

	public ItemSlot(Inventory inventory) {
		this.inventory = inventory;
	}


	@Override
	/**
	 * Handle on click. Move the items from and to the cursor when needed
	 * */
	protected void clicked(boolean rightClick) {
		Cursor cursor = GUI.getCursor();

		if (item == null) {
			if (cursor.getItem() != null) { // move the item from the cursor into the slot
				this.item = cursor.getItem();
				cursor.setItem(null);
			}

		} else if (item != null) { // there is an item in the slot
			if (cursor.getItem() == null && !rightClick) {// move the item from the slot to the cursor
				cursor.setItem(item);
				this.item = null;

			} else if (cursor.getItem() == null) {
				item.use(inventory.getOwner(), this, inventory);
			} else { // swap the items

				if (getCharge() > 0) {
					if (((Chargeable) item).isCharger(cursor.getItem())) {
						if (((Chargeable) item).addCharge(cursor.getItem()))
							cursor.setItem(null);
						return;
					}
				}

				Item temp = item;
				item = cursor.getItem();
				cursor.setItem(temp);
			}
		}
	}

	@Override
	public void render(SpriteBatch g) {
		if (!visible)
			return;

		// render the slot
		sprite.render(g, pos.x, pos.y);

		// render the item in the slot
		if (item == null)
			return;
		item.render(g, pos.x, pos.y);
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
		if (item != null)
			inventory.getOwner().fireEvent(new FetchEvent(inventory.getOwner(), item));
	}

	public Item removeItem() {
		Item temp = item;
		item = null;
		return temp;
	}

	public boolean isEmpty() {
		if (item != null)
			return false;
		return true;
	}

	public int getCharge() {
		if (item != null) {
			if (item instanceof Chargeable) {
				int charge = ((Chargeable) item).getCharge();
				if (charge <= 0) {
					item = ((Chargeable) item).getNoChargeItem();
				}
				return charge;
			}
		}
		return 0;
	}
}
