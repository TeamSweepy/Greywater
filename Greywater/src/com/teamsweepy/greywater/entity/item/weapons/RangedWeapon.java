package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.entity.Mob;


public abstract class RangedWeapon extends Weapon {

	public RangedWeapon(String inventoryImageName, String floorImageName, int damageDice, int toHitBuff, int damageBuff, float range) {
		super(inventoryImageName, floorImageName, damageDice, toHitBuff, damageBuff, range);
	}

	@Override
	public void attack(Mob attacker, Mob victim) {
		this.throwItemAtTarget(attacker, victim);
	}
}
