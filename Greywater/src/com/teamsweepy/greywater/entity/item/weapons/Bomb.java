
package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.effect.spell.AOESpell;
import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.item.Chargeable;
import com.teamsweepy.greywater.entity.item.IDs;
import com.teamsweepy.greywater.entity.item.Item;
import com.teamsweepy.greywater.entity.level.Level;

import com.badlogic.gdx.audio.Sound;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Bomb extends RangedWeapon implements Chargeable {

	private int charge = 1;
	private int maxCharge = 100;

	public Bomb() {
		super("Bomb", "Floor-Bomb", 100, 2, 30, 550f);
	}

	@Override
	public int getID() {
		return IDs.BOMB.getID();
	}

	public boolean swing(Mob attacker, Mob victim) {
		this.throwItemAtTarget(attacker, victim);
		return true;
	}

	@Override
	public boolean attack(Mob attacker, Mob victim) {
		removeCharge(1);
		List<Point> tileIndices = new ArrayList<Point>();
		Point centralTile = Globals.toTileIndices(victim.getLocation());
//		tileIndices.add(centralTile);

		for (int x = -2; x <= 2; x++) {
			for (int y = 2; y >= -2; y--) {
				tileIndices.add(new Point(centralTile.x + x, centralTile.y + y));
			}
		}
		Level currentWorld = attacker.getLevel();
		AOESpell aoe = new AOESpell("particle/explosion.p", currentWorld.getTiles(tileIndices), currentWorld.getTile(centralTile.x, centralTile.y), 0, 200, currentWorld, attacker);
		victim.addSpell(aoe);
		((Sound) AssetLoader.getAsset(Sound.class, "bomb.wav")).play(1f);
		return !victim.isAlive();
	}

	@Override
	public void removeCharge(int remove) {
		charge -= remove;
	}

	@Override
	public boolean addCharge(Item add) {
		if (charge + ((Bomb) add).getCharge() > maxCharge)
			return false;
		charge += ((Bomb) add).getCharge();
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
		if (i.getClass() == Bomb.class) {
			return true;
		}
		return false;
	}



}
