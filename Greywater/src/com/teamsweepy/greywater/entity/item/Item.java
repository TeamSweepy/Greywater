
package com.teamsweepy.greywater.entity.item;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.component.Entity;
import com.teamsweepy.greywater.entity.component.Hitbox;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.item.misc.VoltCell;
import com.teamsweepy.greywater.entity.item.potions.HealthPotion;
import com.teamsweepy.greywater.entity.item.potions.Mercury;
import com.teamsweepy.greywater.entity.item.weapons.Bomb;
import com.teamsweepy.greywater.entity.item.weapons.TazerWrench;
import com.teamsweepy.greywater.entity.item.weapons.Wrench;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class Item extends Entity {

	protected boolean onGround = false;
	private Sprite groundSprite;
	private String name;

	private boolean dropped = false;
	private boolean thrown = false;
	private Point2F landingLoc, startLoc;

	private float x, y, z;
	private float angle;
	private float maxDistance;


	public Item(String inventoryImageName, String floorImageName, float x, float y, int width, int height) {
		this.name = inventoryImageName;
		physicsComponent = new Hitbox(x * 50 + 25, y * 50 + 25, width, height, 0);
		this.graphicsComponent = new Sprite(inventoryImageName);
		this.groundSprite = new Sprite(floorImageName);
	}
	
	public Item() {
	}



	public void throwOnGround(Point2F destination, Mob thrower) {
		dropped = true;
		landingLoc = destination;
		startLoc = thrower.getLocation();
		physicsComponent.setLocation(startLoc.x, startLoc.y);
		thrower.getLevel().addNewFloorItem(this);
		
		angle = startLoc.angle(landingLoc);
		x = y = z = 0;
		maxDistance = startLoc.distance(landingLoc);
	}
	
	public void throwItemAtTarget(Mob thrower, Entity target) {
		thrown = true;
		landingLoc = target.getLocation();
		startLoc = thrower.getLocation();
		physicsComponent.setLocation(startLoc.x, startLoc.y);

		thrower.getLevel().addNewThrownItem(this);
		
		angle = startLoc.angle(landingLoc);
		x = y = z = 0;
		maxDistance = startLoc.distance(landingLoc);
	}

	public void pickup() {
		onGround = false;
		dropped = false;
	}

	@Override
	public void tick(float deltaTime) {
		if (dropped || thrown) {
			float dx = 0;
			float dy = 0;
			if (dropped) {
				dx = (float) (Math.cos(angle) * 6F);
				dy = (float) (Math.sin(angle) * 6F);
			}

			if (thrown) {
				dx = (float) (Math.cos(angle) * 28F);
				dy = (float) (Math.sin(angle) * 28F);
			}

			float curDistance = startLoc.distance(getX(), getY());

			x = getX() + dx;
			y = getY() + dy;

			if (dropped)
				z = getArcHeight(curDistance, maxDistance, 150);

			if (thrown)
				z = getArcHeight(curDistance, maxDistance, 40) + 160;//160 so it starts at chest height of thrower

			physicsComponent.setLocation(x, y);
			if (curDistance >= maxDistance) {
				if(dropped){
					onGround = true;
					physicsComponent.setLocation(landingLoc.x, landingLoc.y);
				}
				dropped = false;
				thrown = false;
			}
		}
		super.tick(deltaTime);
	}

	private float getArcHeight(float currentDistance, float maxDistance, float maxLength) {
		float mid = maxDistance / 2;
		if (currentDistance > mid) {
			// Height is falling
			float dif = (mid - (currentDistance - mid)) / mid;
			return maxLength * dif;
		} else {
			float dif = 1 - ((mid - currentDistance) / mid);
			return (maxLength * dif);
		}
	}
	
	public boolean isWorldItem(){
		return (dropped || thrown || onGround);
	}



	/** Calls the render method below with sprite's dimensions */
	public void render(SpriteBatch g, float x, float y) {
		if (!onGround && !thrown) {
			graphicsComponent.render(g, x, y);
		} else {
			System.out.println("WARNING " + name + " is out of state! A floor item is being drawn like a inventory item.");
		}
	}

	public void render(SpriteBatch g) {
		if (onGround || thrown || dropped) {
			Point2F p = Globals.toIsoCoord(getX(), getY());
			if (dropped || thrown) {
				p.y += z;
			}
			groundSprite.render(g, p.x, p.y);
		} else {
			System.out.println("WARNING " + name + " is out of state! A inventory item is being drawn like a floor item.");
		}
	}

	public Sprite getSprite() {
		return graphicsComponent;
	}

	public abstract int getID();

	public String getName() {
		return name;
	}

	/** Finds if given point is within current image's bounding box, meant for Ziga to override for items */
	protected boolean didPointHitImage(Point2F point) {
		Point2F p = Globals.toIsoCoord(getX(), getY());
		return groundSprite.getImageRectangleAtOrigin(p.x + mainCamera.xOffsetAggregate, p.y + mainCamera.yOffsetAggregate).contains(point.x, point.y);
	}

}
