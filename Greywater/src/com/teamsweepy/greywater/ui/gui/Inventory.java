
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.component.events.FetchEvent;
import com.teamsweepy.greywater.entity.item.Item;
import com.teamsweepy.greywater.entity.item.crafting.Crafting;
import com.teamsweepy.greywater.entity.item.misc.VoltCell;
import com.teamsweepy.greywater.entity.item.potions.HealthPotion;
import com.teamsweepy.greywater.entity.item.potions.Mercury;
import com.teamsweepy.greywater.entity.item.weapons.Bomb;
import com.teamsweepy.greywater.entity.item.weapons.Weapon;
import com.teamsweepy.greywater.entity.item.weapons.Wrench;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.CraftingSlot;
import com.teamsweepy.greywater.ui.gui.subgui.ItemSlot;
import com.teamsweepy.greywater.ui.gui.subgui.OutputSlot;
import com.teamsweepy.greywater.ui.gui.subgui.WeaponSlot;

import java.util.ArrayList;


public class Inventory extends GUIComponent {

	/** Main part of the inventory, the so called storage part. 6 * 5 crafting slots */
	protected ArrayList<ItemSlot> itemSlots = new ArrayList<ItemSlot>();

	/** 4 crafting slots for basic crafting + output slot for crafting result */
	protected ArrayList<CraftingSlot> craftingSlots = new ArrayList<CraftingSlot>();
	protected OutputSlot outputSlot;

	/** Currently only a weapon slot, but this will later include armor slots */
	protected ArrayList<WeaponSlot> weaponSlots = new ArrayList<WeaponSlot>();

	protected Mob owner;

	public Inventory(Mob owner) {
		super(1000, 250, 437, 608);
		this.owner = owner;
		sprite = new Sprite("inventory", true);

		initSubComponents();

		{// Adding some items in the inventory for testing purposes
 			int i = 0;
 			itemSlots.get(0).setItem(new HealthPotion());
 			itemSlots.get(1).setItem(new Mercury());
 			itemSlots.get(1).setItem(new Mercury());
 			itemSlots.get(2).setItem(new Wrench());
 			itemSlots.get(3).setItem(new VoltCell());
 		}
		
		visible = true;
	}

	public Inventory(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	/** Create all the comopnents of the inventory, such as background and itemslots */
	protected void initSubComponents() {
		//subComponents.add(new Plane(pos.x, pos.y, size.x, size.y));

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
		weaponSlots.add(weaponSlot);
		subComponents.add(weaponSlot);
	}

	public boolean addItem(Item i) {
		int a = getEmptySlot();
		if (a == -1)
			return false;
		itemSlots.get(a).setItem(i);
		owner.fireEvent(new FetchEvent(owner, i));
		return true;
	}


	@Override
	/** Attempt to craft stuff */
	public void tick(float deltaTime) {
		outputSlot.setItem(Crafting.checkCrafting(craftingSlots));
		weaponSlots.get(0).getCharge();
	}

	/**
	 * Called from WeaponSlot to clear the crafting table after crafthing an item
	 * */
	public void emptyCraftingSlots() {
		for (CraftingSlot cs : craftingSlots) {
			cs.setItem(null);
		}
	}

	public Weapon getWeapon() {
		if (weaponSlots.size() != 1) {
			return null;
		}
		return (Weapon) weaponSlots.get(0).getItem();
	}

	public boolean hasSpace() {
		for (ItemSlot i : itemSlots) {
			if (i.getItem() == null)
				return true;
		}
		return false;
	}

	public int getCharge() {
		return weaponSlots.get(0).getCharge();
	}


	public void dumpSlots() {
		if (weaponSlots != null && !weaponSlots.isEmpty()) {
			for (ItemSlot slot : weaponSlots) {
				if (!slot.isEmpty()) {
					Point2F throwPoint = Globals.calculateRandomLocation(owner.getLocation(), owner.getLevel(), .7f);
					slot.removeItem().throwOnGround(throwPoint, owner);
				}
			}
		}
		if (craftingSlots != null && !craftingSlots.isEmpty()) {
			for (ItemSlot slot : craftingSlots) {
				if (!slot.isEmpty()) {
					Point2F throwPoint = Globals.calculateRandomLocation(owner.getLocation(), owner.getLevel(), .7f);
					slot.removeItem().throwOnGround(throwPoint, owner);
				}
			}
		}
		if (outputSlot != null && !outputSlot.isEmpty()) {
			if (!outputSlot.isEmpty()) {
				Point2F throwPoint = Globals.calculateRandomLocation(owner.getLocation(), owner.getLevel(), .7f);
				outputSlot.removeItem().throwOnGround(throwPoint, owner);
			}

		}
		if (itemSlots != null && !itemSlots.isEmpty()) {
			for (ItemSlot slot : itemSlots) {
				if (!slot.isEmpty()) {
					Point2F throwPoint = Globals.calculateRandomLocation(owner.getLocation(), owner.getLevel(), .7f);
					slot.removeItem().throwOnGround(throwPoint, owner);
				}
			}
		}

	}

	protected int getEmptySlot() {
		for (int i = 0; i < itemSlots.size(); i++) {
			if (itemSlots.get(i).getItem() == null)
				return i;
		}
		return -1;
	}
	
	public WeaponSlot getWeaponSlot(){
		return weaponSlots.get(0);
	}
	
	public Mob getOwner(){
		return owner;
	}

}
