package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.effect.spell.PersonalSpell;
import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.item.IDs;

import com.badlogic.gdx.audio.Sound;


public class Bomb extends RangedWeapon {

	public Bomb() {
		super("Bomb", "Floor-Bomb", 100, 2, 30, 550f);
	}

	@Override
	public int getID() {
		return IDs.BOMB.getID();
	}

	@Override
	public boolean attack(Mob attacker, Mob victim) {
		super.attack(attacker, victim);
		victim.addSpell(new PersonalSpell("particle/explosion.p", 0, 100, victim, attacker));
		((Sound)AssetLoader.getAsset(Sound.class, "bomb.wav")).play(1f);
		return !victim.isAlive();
	}

}
