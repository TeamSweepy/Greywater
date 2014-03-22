
package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.component.events.KillEvent;
import com.teamsweepy.greywater.entity.item.Item;

public abstract class Weapon extends Item {

	protected int dmgDice;
	protected int toHitBuff;
	protected int dmgBuff;
	protected float range;


	public Weapon(String inventoryImageName, String floorImageName, int damageDice, int toHitBuff, int damageBuff, float range) {
		super(inventoryImageName, floorImageName, 0, 0, 62, 62);
		this.range = range;
		dmgDice = damageDice;
		this.toHitBuff = toHitBuff;
		dmgBuff = damageBuff;
	}

	public Weapon(int damageDice, int toHitBuff, int damageBuff, float range) {
		super();
		this.range = range;
		dmgDice = damageDice;
		this.toHitBuff = toHitBuff;
		dmgBuff = damageBuff;
	}

	public float getRange() {
		return range;
	}

	public boolean swing(Mob attacker, Mob victim) {
		if (Globals.D(20) + toHitBuff >= victim.getArmor()) {
			return true;
		}
		return false;
	}

	public boolean attack(Mob attacker, Mob victim) {
		victim.changeHP(Globals.D(dmgDice) + dmgBuff);
		if (!victim.isAlive()) {
			attacker.getKillList().add(victim);
			attacker.fireEvent(new KillEvent(attacker, victim));
		}
		return !victim.isAlive();
	}

	public abstract int getID();

}
