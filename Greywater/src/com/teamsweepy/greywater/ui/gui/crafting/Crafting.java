
package com.teamsweepy.greywater.ui.gui.crafting;

import com.teamsweepy.greywater.ui.gui.subgui.CraftingSlot;
import com.teamsweepy.greywater.ui.item.Item;

import java.util.ArrayList;
import java.util.List;


public class Crafting {

	private static List<ShapelessRecipe> recipies = ShapelessRecipe.getAllRecipies();

	/**
	 * Called from Inventory to check if anything can be crafted.
	 * 
	 * Recipies are pulled from ShaplessRecipies enumeration class
	 * 
	 * There is a check if there are the right items in the slots, and it also checks that there is not too much items in there.
	 * */
	public static Item checkCrafting(ArrayList<CraftingSlot> craftingSlots) {

		ArrayList<Item> temp = getItemsFromSlots(craftingSlots); // check if there is even anything in the slots 
		temp = removeAllEmpty(temp);
		if (temp.isEmpty())
			return null;

		for (ShapelessRecipe recipie : recipies) {

			ArrayList<Item> items = getItemsFromSlots(craftingSlots);
			items = removeAllEmpty(items);
			/*int[] itemsGottenIDs = new int[items.size()];
			for (int i = 0; i < items.size(); i++) {
				itemsGottenIDs[i] = items.get(i).getID();
			}*/

			int[] itemsNeeded = recipie.getNeededItems(); //get needed items form the recipie

			if (itemsNeeded.length > items.size())
				continue;

			boolean canCraft = true;
			for (int i : itemsNeeded) {// check if it contains all the items

				Item containsIt = contains(items, i);
				items.remove(containsIt);
				if (containsIt == null)
					canCraft = false;

			}
			//System.out.println(canCraft);
			if (canCraft && items.isEmpty())
				return Item.getByID(recipie.getCrafted());
		}


		return null;
	}

	/** Checks if the array list contains a certain item */
	private static Item contains(ArrayList<Item> items, int itemID) {
		for (int a = 0; a < items.size(); a++) {
			Item i = items.get(a);

			if (i.getID() == itemID) {
				return i;
			}
		}
		return null;
	}

	/** Removes all the empty item slots from the arraylist */
	private static ArrayList<Item> removeAllEmpty(ArrayList<Item> items) {
		while (items.contains(null)) {
			for (int a = 0; a < items.size(); a++) {
				if (items.get(a) == null)
					items.remove(items.get(a));
			}
		}

		return items;
	}

	/** Gets an array of items from an array of itemslots */
	private static ArrayList<Item> getItemsFromSlots(ArrayList<CraftingSlot> craftingSlots) {
		ArrayList<Item> returnItems = new ArrayList<Item>();
		for (CraftingSlot slot : craftingSlots) {
			returnItems.add(slot.getItem());
		}
		return returnItems;
	}


}
