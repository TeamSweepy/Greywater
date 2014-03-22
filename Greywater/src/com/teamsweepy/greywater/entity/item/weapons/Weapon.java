
package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.item.Item;

// just a class to do the hierarchy for the moment, later on adding damage and durability and so on
public abstract class Weapon extends Item {

	int dmgDice;
	int toHitBuff;
	int dmgBuff;
	float range;

	public Weapon(String inventoryImageName, String floorImageName, int damageDice, int toHitBuff, int damageBuff, float range) {
		super(inventoryImageName, floorImageName, 0, 0, 62, 62);
		this.range = range;
		dmgDice = damageDice;
		this.toHitBuff= toHitBuff;
		dmgBuff = damageBuff;
	}
	
	public float getRange(){
		return range;
	}

	public abstract void attack(Mob attacker, Mob victim);

	public abstract int getID();

}
