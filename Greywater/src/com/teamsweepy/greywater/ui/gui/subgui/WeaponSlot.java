
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.ui.gui.Cursor;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.Inventory;
import com.teamsweepy.greywater.ui.item.Item;
import com.teamsweepy.greywater.ui.item.weapons.Weapon;


public class WeaponSlot extends ItemSlot {

	public WeaponSlot(Inventory inventory, float x, float y, float w, float h) {
		super(inventory, x, y, w, h);
		sprite = new Sprite("slot_weapon");
	}

	@Override
	/**
	 * Only accepts a weapon
	 * */
	protected void clicked() {
		Cursor cursor = GUI.getCursor();

		if (item == null) {

			if (cursor.getItem() != null) { // move the item from the cursor into the slot
				if (!(cursor.getItem() instanceof Weapon))// do the check
					return;
				this.item = cursor.getItem();
				cursor.setItem(null);
			}

		} else if (item != null) { // there is an item in the slot

			if (cursor.getItem() == null) {// move the item from the slot to the cursor
				cursor.setItem(item);
				this.item = null;

			} else { // swap the items
				if (!(cursor.getItem() instanceof Weapon))// do the check
					return;
				Item temp = item;
				item = cursor.getItem();
				cursor.setItem(temp);
			}
		}
	}

}
