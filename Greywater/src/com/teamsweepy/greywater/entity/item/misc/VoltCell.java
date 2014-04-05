
package com.teamsweepy.greywater.entity.item.misc;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.item.IDs;
import com.teamsweepy.greywater.entity.item.Item;
import com.teamsweepy.greywater.ui.gui.Inventory;
import com.teamsweepy.greywater.ui.gui.subgui.ItemSlot;


public class VoltCell extends Item {

	public VoltCell() {
		super("voltcell", "voltcell_floor", 0, 0, 62, 62);
	}

	@Override
	public int getID() {
		return IDs.VOLT_CELL.getID();
	}

	public void use(Mob owner, ItemSlot holder, Inventory inventory) {
		if(inventory.getWeaponSlot() == null)
			return;
		if(inventory.getWeaponSlot().addChargeItem(this))
			holder.setItem(null);
	}
}
