
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.item.Chargeable;
import com.teamsweepy.greywater.entity.item.Item;
import com.teamsweepy.greywater.entity.item.weapons.Weapon;
import com.teamsweepy.greywater.ui.gui.Cursor;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.Inventory;


public class WeaponSlot extends ItemSlot {

	public WeaponSlot(Inventory inventory, float x, float y, float w, float h) {
		super(inventory, x, y, w, h);
		sprite = new Sprite("slot_weapon");
	}

	@Override
	/** Only accepts a weapon */
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
				if (getCharge() > 0) {
					if (((Chargeable) item).isCharger(cursor.getItem())) {
						if(((Chargeable) item).addCharge(cursor.getItem()))
						cursor.setItem(null);
						return;
					}
				}
				
				if (!(cursor.getItem() instanceof Weapon))// do the check
					return;
				Item temp = item;
				item = cursor.getItem();
				cursor.setItem(temp);
			}
		}
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
