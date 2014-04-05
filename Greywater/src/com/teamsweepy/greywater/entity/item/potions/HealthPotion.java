
package com.teamsweepy.greywater.entity.item.potions;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.item.IDs;
import com.teamsweepy.greywater.entity.item.Item;
import com.teamsweepy.greywater.ui.gui.Inventory;
import com.teamsweepy.greywater.ui.gui.subgui.ItemSlot;


public class HealthPotion extends Item {

	public HealthPotion() {
		super("healthpotion", "healthpotion_floor", 0, 0, 62, 62);
	}


	@Override
	public int getID() {
		return IDs.HEALTH_POTION.getID();
	}

	public void use(Mob owner, ItemSlot holder, Inventory inventory) {
		if(owner.getHP() == owner.maxHP())
			return;
		owner.changeHP(-25);
		holder.setItem(null);
	}


}
