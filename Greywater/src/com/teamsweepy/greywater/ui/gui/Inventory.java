
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.crafting.Crafting;
import com.teamsweepy.greywater.ui.gui.subgui.CraftingSlot;
import com.teamsweepy.greywater.ui.gui.subgui.ItemSlot;
import com.teamsweepy.greywater.ui.gui.subgui.OutputSlot;
import com.teamsweepy.greywater.ui.gui.subgui.Plane;
import com.teamsweepy.greywater.ui.gui.subgui.WeaponSlot;
import com.teamsweepy.greywater.ui.item.misc.VoltCell;
import com.teamsweepy.greywater.ui.item.potions.HealthPotion;
import com.teamsweepy.greywater.ui.item.potions.Mercury;
import com.teamsweepy.greywater.ui.item.weapons.Wrench;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;


public class Inventory extends GUIComponent {

	/** Main part of the inventory, the so called storage part. 6 * 5 crafting slots */
	protected ArrayList<ItemSlot> itemSlots = new ArrayList<ItemSlot>();

	/** 4 crafting slots for basic crafting + output slot for crafting result */
	protected ArrayList<CraftingSlot> craftingSlots = new ArrayList<CraftingSlot>();
	protected OutputSlot outputSlot;

	/** Currently only a weapon slot, but this will later include armor slots */
	protected ArrayList<WeaponSlot> weaponSlots = new ArrayList<WeaponSlot>();


	public Inventory() {
		sprite = new Sprite("inventory");
		pos = new Point2F(1000, 250);
		size = new Point2F(437, 608);

		initSubComponents();

		{// Adding some items in the inventory for testing purposes
			int i = 0;
			itemSlots.get(0).setItem(new HealthPotion());
			itemSlots.get(1).setItem(new Mercury());
			itemSlots.get(2).setItem(new Wrench());
			itemSlots.get(3).setItem(new VoltCell());
			i = 5;
			itemSlots.get(0 + i).setItem(new HealthPotion());
			itemSlots.get(1 + i).setItem(new Mercury());
			itemSlots.get(2 + i).setItem(new Wrench());
			itemSlots.get(3 + i).setItem(new VoltCell());
			i = 10;
			itemSlots.get(0 + i).setItem(new HealthPotion());
			itemSlots.get(1 + i).setItem(new Mercury());
			itemSlots.get(2 + i).setItem(new Wrench());
			itemSlots.get(3 + i).setItem(new VoltCell());
		}

		visible = true;
	}

	@Override
	/**
	 * Create all the comopnents of the inventory, such as background and itemslots
	 * */
	protected void initSubComponents() {
		subComponents.add(new Plane(pos.x, pos.y, size.x, size.y));

		int slotSize = 62;
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 5; y++) {
				ItemSlot newSlot = new ItemSlot(this, pos.x + x * slotSize + 33, pos.y + y * slotSize + 33, slotSize, slotSize);
				subComponents.add(newSlot);
				itemSlots.add(newSlot);
			}
		}

		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				CraftingSlot newSlot = new CraftingSlot(this, pos.x + x * slotSize + 180, pos.y + y * slotSize + 400, slotSize, slotSize);
				subComponents.add(newSlot);
				craftingSlots.add(newSlot);
			}
		}

		outputSlot = new OutputSlot(this, pos.x + 330, pos.y + 400 + slotSize * .5f, slotSize, slotSize);
		subComponents.add(outputSlot);

		WeaponSlot weaponSlot = new WeaponSlot(this, pos.x + 65, pos.y + 400 + slotSize * 0.5f, slotSize, slotSize);
		subComponents.add(weaponSlot);
	}

	@Override
	/**
	 * Attempt to craft stuff
	 * */
	public void tick() {
		outputSlot.setItem(Crafting.checkCrafting(craftingSlots));
	}

	/**
	 * Called from WeaponSlot to clear the crafting table after crafthing an item
	 * */
	public void emptyCraftingSlots() {
		for (CraftingSlot cs : craftingSlots) {
			cs.setItem(null);
		}
	}

	@Override
	public void handleInput(Point2F mousePosition, int event) {
		super.handleInput(mousePosition, event);
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
	}

}
