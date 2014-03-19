
package com.teamsweepy.greywater.entity.item;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.component.Entity;
import com.teamsweepy.greywater.entity.component.Hitbox;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.item.misc.VoltCell;
import com.teamsweepy.greywater.entity.item.potions.HealthPotion;
import com.teamsweepy.greywater.entity.item.potions.Mercury;
import com.teamsweepy.greywater.entity.item.weapons.TazerWrench;
import com.teamsweepy.greywater.entity.item.weapons.Wrench;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class Item extends Entity {

	protected boolean onGround = false;
	private Sprite groundSprite;
	private String name;

	public Item(String name, float x, float y, int width, int height) {
		this.name = name;
		physicsComponent = new Hitbox(x * 50 + 25, y * 50 + 25, width, height, 0);
		this.graphicsComponent = new Sprite(getName());
		this.groundSprite = new Sprite(getName());
	}

	public void throwOnGround(Point2F objectiveCoordinates, Mob thrower) {
		onGround = true;
		physicsComponent.setLocation(objectiveCoordinates.x, objectiveCoordinates.y);
		thrower.getLevel().addNewFloorItem(this);
	}

	public void pickup() {
		onGround = false;
	}

	/** Calls the render method below with sprite's dimensions */
	public void render(SpriteBatch g, float x, float y) {
		if (!onGround) {
			graphicsComponent.render(g, x, y);
		} else {
			System.out.println("WARNING " + name + " is out of state! A floor item is being drawn like a inventory item.");
		}

	}

	public void render(SpriteBatch g) {
		if (onGround) {
			Point2F p = Globals.toIsoCoord(getX(), getY());
			groundSprite.render(g, p.x, p.y);
		} else {
			System.out.println("WARNING " + name + " is out of state! A inventory item is being drawn like a floor item.");
		}
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

	public String getName() {
		return name;
	}

	/** Finds if given point is within current image's bounding box, meant for Ziga to override for items */
	protected boolean didPointHitImage(Point2F point) {
		Point2F p = Globals.toIsoCoord(getX(), getY());
		return groundSprite.getImageRectangleAtOrigin(p.x + mainCamera.xOffsetAggregate, p.y + mainCamera.yOffsetAggregate).contains(point.x, point.y);
	}

}
