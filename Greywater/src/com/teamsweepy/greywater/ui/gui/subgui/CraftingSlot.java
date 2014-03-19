
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.ui.gui.Inventory;


public class CraftingSlot extends ItemSlot {

	public CraftingSlot(Inventory inventory, float x, float y, float w, float h) {
		super(inventory, x, y, w, h);
		sprite = new Sprite("slot_craft");
	}
	
	

}
