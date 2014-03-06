
package com.teamsweepy.greywater.ui.gui.crafting;

import com.teamsweepy.greywater.ui.item.IDs;

import java.util.Arrays;
import java.util.List;



public enum ShapelessRecipe {

	TAZER_WRENCH(new int[] { IDs.VOLT_CELL.getID(), IDs.WRENCH.getID() }, IDs.TAZER_WRENCH.getID()), //
	MERCURY(new int[] { IDs.HEALTH_POTION.getID(), IDs.TAZER_WRENCH.getID() }, IDs.MERCURY.getID()), //
	VOLT_CELL(new int[] { IDs.MERCURY.getID(), IDs.HEALTH_POTION.getID(), IDs.WRENCH.getID() }, IDs.VOLT_CELL.getID()), //
	WRENCH(new int[] { IDs.VOLT_CELL.getID() }, IDs.WRENCH.getID()), //
	HEALTH_POTION(new int[] { IDs.MERCURY.getID(), IDs.MERCURY.getID(), IDs.MERCURY.getID() }, IDs.HEALTH_POTION.getID());

	private int[] items;
	private int crafted;

	private ShapelessRecipe(int items[], int crafted) {
		this.items = items;
		this.crafted = crafted;
	}

	public int[] getNeededItems() {
		return items;
	}

	public String neededItemsToString() {
		String output = "";
		for (int i = 0; i < items.length; i++) {
			output += items[i];
		}
		return output;
	}

	public int getCrafted() {
		return crafted;
	}

	public static List<ShapelessRecipe> getAllRecipies() {
		return Arrays.asList(ShapelessRecipe.values());
	}

}
