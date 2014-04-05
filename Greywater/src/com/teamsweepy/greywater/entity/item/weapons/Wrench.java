
package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.item.IDs;


public class Wrench extends Weapon {

	public Wrench() {
		super("wrench", "wrench_floor", 20, 4, 3, 105f);
	}

	@Override
	public int getID() {
		return IDs.WRENCH.getID();
	}
}
