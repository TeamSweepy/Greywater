
package com.teamsweepy.greywater.ui.item;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.entities.level.Level;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.item.misc.VoltCell;
import com.teamsweepy.greywater.ui.item.potions.HealthPotion;
import com.teamsweepy.greywater.ui.item.potions.Mercury;
import com.teamsweepy.greywater.ui.item.weapons.TazerWrench;
import com.teamsweepy.greywater.ui.item.weapons.Wrench;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class Item extends Entity {

	protected boolean onGround = false;

	public Item() {
		physicsComponent = new Hitbox(0, 0, 64, 64, 0);
	}

	public void throwOnGround(Point2F onScreen) {
		onScreen = Globals.toNormalCoord(onScreen);
		onGround = true;
		physicsComponent = new Hitbox(onScreen.x, onScreen.y, 64, 64, 0);
		Level.getLevel().addNewFloorItem(this);
	}

	@Override
	/**From entity - don't render it if it isn't on ground*/
	public void render(SpriteBatch g) {
		if (!onGround)
			return;
		super.render(g);
	}

	/** Calls the render method below with sprite's dimenstions */
	public void render(SpriteBatch g, float x, float y) {
		render(g, x, y, graphicsComponent.getImageWidth(), graphicsComponent.getImageHeight());
	}

	/** Render on screen at a certain location and size */
	public void render(SpriteBatch g, float x, float y, float w, float h) {
		if (graphicsComponent == null) {
			System.out.println("[ERROR] There is no Sprite assigned to " + getClass() + " item");
			return;
		}
		if (onGround)
			return;
		graphicsComponent.render(g, x, y, w, h);
	}



	public Sprite getSprite() {
		return graphicsComponent;
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

		System.out.println("No ID Found in Item.java getByID");
		return null;
	}

	public void setOnGround(boolean b) {
		onGround = b;
	}

}
