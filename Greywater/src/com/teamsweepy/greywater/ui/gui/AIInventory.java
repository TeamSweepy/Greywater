package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.ui.gui.subgui.ItemSlot;


public class AIInventory extends Inventory {
	
	public AIInventory(Mob owner){
		super(100, 500, 310, 310);
		this.owner = owner;
		int slotSize = 62;//pixels
		for(int x = 0; x < 5; x++){
			for(int y = 0; y < 5; y++){
				ItemSlot newSlot = new ItemSlot(this, pos.x + x * slotSize + 33, pos.y + y * slotSize + 33, slotSize, slotSize);
				subComponents.add(newSlot);
				itemSlots.add(newSlot);
			}
		}
		
	}

}
