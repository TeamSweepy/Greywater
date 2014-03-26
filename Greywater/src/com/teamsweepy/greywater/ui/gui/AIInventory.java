
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.Sweepy;
import com.teamsweepy.greywater.entity.item.misc.VoltCell;
import com.teamsweepy.greywater.entity.item.potions.HealthPotion;
import com.teamsweepy.greywater.entity.item.potions.Mercury;
import com.teamsweepy.greywater.entity.item.weapons.Bomb;
import com.teamsweepy.greywater.entity.item.weapons.Wrench;
import com.teamsweepy.greywater.ui.gui.subgui.ItemSlot;


public class AIInventory extends Inventory {

	public AIInventory(Sweepy owner) {
		super(100, 300, 310, 310);
		this.owner = owner;
		int slotSize = 62;//pixels
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				ItemSlot newSlot = new ItemSlot(this, pos.x + x * slotSize + 33, pos.y + y * slotSize + 33, slotSize, slotSize) {

					protected void clicked() {
						if (Player.getLocalPlayer().getInventory().hasSpace()) {
							Player.getLocalPlayer().getInventory().addItem(this.getItem());
							item = null;
						}
					}
				};
				subComponents.add(newSlot);
				itemSlots.add(newSlot);
			}
		}
		addItem(new HealthPotion());
		addItem(new VoltCell());
		addItem(new Bomb());
		GUI.addGUIComponent(this);
	}
	
	public AIInventory(Mob owner) {
		super(100, 300, 310, 310);
		this.owner = owner;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				ItemSlot newSlot = new ItemSlot(this) {

					protected void clicked() {
						if (Player.getLocalPlayer().getInventory().hasSpace()) {
							Player.getLocalPlayer().getInventory().addItem(this.getItem());
							item = null;
						}
					}
				};
				subComponents.add(newSlot);
				itemSlots.add(newSlot);
			}
		}
		//TODO add random items based on class
		{// Adding some items in the inventory for testing purposes
 			int i = 0;
 			itemSlots.get(0).setItem(new HealthPotion());
 			itemSlots.get(1).setItem(new Mercury());
 			itemSlots.get(2).setItem(new Wrench());
 			itemSlots.get(3).setItem(new VoltCell());
 			i = 5;
 			itemSlots.get(0 + i).setItem(new HealthPotion());
 			itemSlots.get(1 + i).setItem(new Bomb());
 			itemSlots.get(2 + i).setItem(new Wrench());
 			itemSlots.get(3 + i).setItem(new VoltCell());

 		}
	}

	public void tick(float deltaTime) {}
}
