
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entity.item.weapons.TazerWrench;
import com.teamsweepy.greywater.ui.gui.Cursor;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.Inventory;

/**
 * Output slot makes sure you can't place anything in it.
 * */
public class OutputSlot extends ItemSlot {

	public OutputSlot(Inventory inventory, float x, float y, float w, float h) {
		super(inventory, x, y, w, h);
	}

	@Override
	protected void clicked() {
		Cursor cursor = GUI.getCursor();

		if (item != null) { // there is an item in the slot

			if (cursor.getItem() == null) {// move the item from the slot to the cursor
				cursor.setItem(item);
				this.item = null;

				inventory.emptyCraftingSlots();
			}
		}
	}


}
