
package com.teamsweepy.greywater.entity.item.crafting;

import com.teamsweepy.greywater.entity.item.IDs;
import com.teamsweepy.greywater.entity.item.misc.FulminatedMercury;
import com.teamsweepy.greywater.entity.item.misc.VoltCell;
import com.teamsweepy.greywater.entity.item.weapons.Bomb;
import com.teamsweepy.greywater.entity.item.weapons.TazerWrench;

import java.util.Arrays;
import java.util.List;



public enum ShapelessRecipe {

	TAZER_WRENCH(new int[] { IDs.VOLT_CELL.getID(), IDs.WRENCH.getID() }, TazerWrench.class), //
	VOLT_CELL(new int[] { IDs.MERCURY.getID(), IDs.WIRES.getID() }, VoltCell.class), //
	BOMB(new int[]{IDs.FULMINATED_MERCURY.getID(), IDs.WIRES.getID(), IDs.VOLT_CELL.getID()}, Bomb.class),
	FULMINATED_MERCURY(new int[]{IDs.MERCURY.getID(), IDs.MERCURY.getID(), IDs.MERCURY.getID()}, FulminatedMercury.class);


	private int[] items;
	private Class crafted;

	private ShapelessRecipe(int items[], Class crafted) {
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

	public Class getCrafted() {
		return crafted;
	}

	public static List<ShapelessRecipe> getAllRecipies() {
		return Arrays.asList(ShapelessRecipe.values());
	}

}
