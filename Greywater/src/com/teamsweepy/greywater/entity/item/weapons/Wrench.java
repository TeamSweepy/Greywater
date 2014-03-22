
package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.item.IDs;


public class Wrench extends Weapon {

	public Wrench() {
		super("tavwrench", "tavwrench", 8, 2, 3, 105f);
	}

	@Override
	public int getID() {
		return IDs.WRENCH.getID();
	}
}
