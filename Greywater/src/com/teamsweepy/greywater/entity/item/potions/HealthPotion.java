
package com.teamsweepy.greywater.entity.item.potions;

import com.teamsweepy.greywater.entity.item.IDs;
import com.teamsweepy.greywater.entity.item.Item;


public class HealthPotion extends Item {

	public HealthPotion() {
		super("healthpotion", "healthpotion", 0, 0, 62, 62);
	}


	@Override
	public int getID() {
		return IDs.HEALTH_POTION.getID();
	}


}
