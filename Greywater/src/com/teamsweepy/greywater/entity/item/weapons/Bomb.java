
package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.effect.spell.AOESpell;
import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.item.Chargeable;
import com.teamsweepy.greywater.entity.item.IDs;
import com.teamsweepy.greywater.entity.item.Item;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2I;
import com.teamsweepy.greywater.ui.gui.Inventory;
import com.teamsweepy.greywater.ui.gui.subgui.ItemSlot;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;


public class Bomb extends RangedWeapon implements Chargeable {

	private int charge = 1;
	private int maxCharge = 100;
	private BitmapFontCache cache;

	public Bomb() {
		super("Bomb", "Bomb_floor", 100, 2, 30, 550f);
		cache = new BitmapFontCache(new BitmapFont());
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
		List<Point2I> tileIndices = new ArrayList<Point2I>();
        Point2I centralTile = Globals.toTileIndices(victim.getLocation());
//		tileIndices.add(centralTile);

		for (int x = -2; x <= 2; x++) {
			for (int y = 2; y >= -2; y--) {
				tileIndices.add(new Point2I(centralTile.x + x, centralTile.y + y));
			}
		}
		Level currentWorld = attacker.getLevel();
		AOESpell aoe = new AOESpell("particle/explosion.p", currentWorld.getTiles(tileIndices), currentWorld.getTile(centralTile.x, centralTile.y), 0, 200, currentWorld, attacker);
		victim.addSpell(aoe);
		((Sound) AssetLoader.getAsset(Sound.class, "electric_wrench.wav")).play(1f);
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

	@Override
	public Item getNoChargeItem() {
		return null;
	}
	
	public void render(SpriteBatch g, float x, float y) {
		cache.setText(charge+"", x + 3, y + 15);
		super.render(g, x, y);
		cache.draw(g);
		
	}
	
	public void use(Mob owner, ItemSlot holder, Inventory inventory) {
		if(inventory.getWeaponSlot() == null)
			return;
		if(inventory.getWeaponSlot().addChargeItem(this))
			holder.setItem(null);
	}



}
