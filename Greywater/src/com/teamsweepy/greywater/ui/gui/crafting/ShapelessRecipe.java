
package com.teamsweepy.greywater.ui.gui.crafting;

import com.teamsweepy.greywater.ui.item.Item;
import com.teamsweepy.greywater.ui.item.misc.VoltCell;
import com.teamsweepy.greywater.ui.item.potions.HealthPotion;
import com.teamsweepy.greywater.ui.item.potions.Mercury;
import com.teamsweepy.greywater.ui.item.weapons.TazerWrench;
import com.teamsweepy.greywater.ui.item.weapons.Wrench;

import java.util.Arrays;
import java.util.List;



public enum ShapelessRecipe {
	/* NAME ( new Item{new NeededItem, new NeededItem...}, new CraftedItem); */
	TAZER_WRENCH(new Item[] { new VoltCell(), new Wrench() }, new TazerWrench()), // 
	MERCURY(new Item[] { new HealthPotion(), new TazerWrench() }, new Mercury()), //
	VOLT_CELL(new Item[] { new Mercury(), new HealthPotion(), new Wrench() }, new VoltCell()), //
	WRENCH(new Item[] { new VoltCell() }, new Wrench()), //
	HEALTH_POTION(new Item[] { new Mercury(), new Mercury(), new Mercury() }, new HealthPotion());

	private Item[] items;
	private Item crafted;

	private ShapelessRecipe(Item items[], Item crafted) {
		this.items = items;
		this.crafted = crafted;
	}

	public Item[] getNeededItems() {
		return items;
	}

	public Item getCrafted() {
		return crafted;
	}

	public static List<ShapelessRecipe> getAllRecipies() {
		return Arrays.asList(ShapelessRecipe.values());
	}
}
