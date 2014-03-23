
package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.effect.spell.PersonalSpell;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.item.Chargeable;
import com.teamsweepy.greywater.entity.item.IDs;

import com.teamsweepy.greywater.entity.item.Item;
import com.teamsweepy.greywater.entity.item.misc.VoltCell;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TazerWrench extends Weapon implements Chargeable {

	private int charge = 80;
	private int maxCharge = 100;

	public TazerWrench() {
		super("tazerwrench", "tazerwrench", 12, 3, 5, 105f);
	}

	@Override
	public int getID() {
		return IDs.TAZER_WRENCH.getID();
	}


	@Override
	public boolean attack(Mob attacker, Mob victim) {
		super.swing(attacker, victim);
		victim.addSpell(new PersonalSpell("particle/electric.p", 0, 10, victim, attacker));
		removeCharge(5);
		return !victim.isAlive();
	}

	@Override
	public void removeCharge(int remove) {
		charge -= remove;
	}

	@Override
	public boolean addCharge(Item add) {
		if(charge + 10 >=maxCharge)
			return false;
		charge+= 10;
		return true;
	}

	@Override
	public int getCharge() {
		return charge;
	}

	@Override
	public int maxCharge() {
		return maxCharge;
	}

	@Override
	public boolean isCharger(Item i) {
		if (i.getClass() == VoltCell.class)
			return true;
		return false;
	}
}
