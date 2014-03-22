package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.item.IDs;


public class Bomb extends RangedWeapon {

	public Bomb() {
		super("Bomb", "Bomb", 100, 2, 30, 550f);
	}

	@Override
	public int getID() {
		return IDs.BOMB.getID();
	}

	@Override
	public void attack(Mob attacker, Mob victim) {
		super.attack(attacker, victim);
		// TODO Auto-generated method stub
		
	}

}
