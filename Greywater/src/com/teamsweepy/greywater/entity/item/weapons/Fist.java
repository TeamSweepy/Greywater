package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.entity.Mob;


public class Fist extends Weapon {

	public Fist() {
		super(6, 5, 2, 75);
	}

	@Override
	public int getID() {
		return -666; //satan fists
	}
}
