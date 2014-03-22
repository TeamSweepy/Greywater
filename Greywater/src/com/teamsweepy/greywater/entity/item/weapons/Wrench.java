
package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.item.IDs;


public class Wrench extends Weapon {

	public Wrench() {
		super("tavwrench", "tavwrench", 8, 2, 3, 105f);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return IDs.WRENCH.getID();
	}

	@Override
	public void attack(Mob attacker, Mob victim) {
		// TODO Auto-generated method stub

	}

}
