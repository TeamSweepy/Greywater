
package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.effect.spell.PersonalSpell;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.item.IDs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TazerWrench extends Weapon {

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
		return !victim.isAlive();
	}
}
