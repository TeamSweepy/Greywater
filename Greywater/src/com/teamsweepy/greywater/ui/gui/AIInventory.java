
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.ClockWorm;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.Sweepy;
import com.teamsweepy.greywater.entity.Vagrant;
import com.teamsweepy.greywater.entity.Watchman;
import com.teamsweepy.greywater.entity.item.misc.FulminatedMercury;
import com.teamsweepy.greywater.entity.item.misc.VoltCell;
import com.teamsweepy.greywater.entity.item.misc.Wires;
import com.teamsweepy.greywater.entity.item.potions.HealthPotion;
import com.teamsweepy.greywater.entity.item.potions.Mercury;
import com.teamsweepy.greywater.entity.item.weapons.Bomb;
import com.teamsweepy.greywater.ui.gui.subgui.Button;
import com.teamsweepy.greywater.ui.gui.subgui.ItemSlot;
import com.teamsweepy.greywater.ui.gui.subgui.WeaponSlot;


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
		addItem(new Wires());

		final AIInventory aiv = this;
		Button closeButton = new Button(pos.x + 320, pos.y + 320, "ui/cross") {

			@Override
			protected void clicked(boolean rightClick) {
				aiv.visible = false;
			}
		};
		subComponents.add(closeButton);
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
		if (owner instanceof ClockWorm) {
			if (Globals.rand.nextBoolean())
				addItem(new Wires());
			if (Globals.rand.nextBoolean())
				addItem(new VoltCell());
			if (Globals.rand.nextBoolean())
				addItem(new FulminatedMercury());
			if (Globals.rand.nextBoolean())
				addItem(new Wires());
			if (Globals.rand.nextBoolean())
				addItem(new VoltCell());
			if (Globals.rand.nextBoolean())
				addItem(new FulminatedMercury());
			if (Globals.rand.nextBoolean())
				addItem(new Wires());
			if (Globals.rand.nextBoolean())
				addItem(new VoltCell());
			if (Globals.rand.nextBoolean())
				addItem(new FulminatedMercury());
		}

		if (owner instanceof Vagrant) {
			if (Globals.rand.nextBoolean())
				addItem(new Mercury());
			if (Globals.rand.nextBoolean())
				addItem(new Bomb());
			if (Globals.rand.nextBoolean())
				addItem(new Wires());
			if (Globals.rand.nextBoolean())
				addItem(new HealthPotion());
			if (Globals.rand.nextBoolean())
				addItem(new VoltCell());
			if (Globals.rand.nextBoolean())
				addItem(new FulminatedMercury());
			if (Globals.rand.nextBoolean())
				addItem(new HealthPotion());
			if (Globals.rand.nextBoolean())
				addItem(new HealthPotion());
			if (Globals.rand.nextBoolean())
				addItem(new Wires());
			if (Globals.rand.nextBoolean())
				addItem(new Wires());
		}
		
		if (owner instanceof Watchman) {
			if (Globals.rand.nextBoolean())
				addItem(new Mercury());
			if (Globals.rand.nextBoolean())
				addItem(new Mercury());
			if (Globals.rand.nextBoolean())
				addItem(new Mercury());
			if (Globals.rand.nextBoolean())
				addItem(new Wires());
			if (Globals.rand.nextBoolean())
				addItem(new HealthPotion());
			if (Globals.rand.nextBoolean())
				addItem(new VoltCell());
			if (Globals.rand.nextBoolean())
				addItem(new VoltCell());
			if (Globals.rand.nextBoolean())
				addItem(new VoltCell());
		}

	}
	
	public WeaponSlot getWeaponSlot(){
		return null;
	}

	public void tick(float deltaTime) {}
}
