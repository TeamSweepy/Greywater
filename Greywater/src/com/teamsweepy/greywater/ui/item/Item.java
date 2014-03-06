
package com.teamsweepy.greywater.ui.item;

import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.ui.item.misc.VoltCell;
import com.teamsweepy.greywater.ui.item.potions.HealthPotion;
import com.teamsweepy.greywater.ui.item.potions.Mercury;
import com.teamsweepy.greywater.ui.item.weapons.TazerWrench;
import com.teamsweepy.greywater.ui.item.weapons.Wrench;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class Item {

	protected Sprite sprite;

	public Item() {

	}

	public void render(SpriteBatch g, float x, float y) {
		if (sprite == null) {
			System.out.println("[ERROR] There is no Sprite assigned to " + getClass() + " item");
			return;
		}
		render(g, x, y, sprite.getImageWidth(), sprite.getImageHeight());
	}

	public void render(SpriteBatch g, float x, float y, float w, float h) {
		if (sprite == null) {
			System.out.println("[ERROR] There is no Sprite assigned to " + getClass() + " item");
			return;
		}
		sprite.render(g, x, y, w, h);
	}

	public Sprite getSprite() {
		return sprite;
	}

	public abstract int getID();

	public static Item getByID(int crafted) {
		if (crafted == IDs.HEALTH_POTION.getID())
			return new HealthPotion();
		if (crafted == IDs.MERCURY.getID())
			return new Mercury();

		if (crafted == IDs.VOLT_CELL.getID())
			return new VoltCell();

		if (crafted == IDs.WRENCH.getID())
			return new Wrench();
		if (crafted == IDs.TAZER_WRENCH.getID())
			return new TazerWrench();

		System.out.println("Hello");
		return null;
	}

}
