
package com.teamsweepy.greywater.entity.item;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.component.Entity;
import com.teamsweepy.greywater.entity.component.Hitbox;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.item.misc.VoltCell;
import com.teamsweepy.greywater.entity.item.potions.HealthPotion;
import com.teamsweepy.greywater.entity.item.potions.Mercury;
import com.teamsweepy.greywater.entity.item.weapons.TazerWrench;
import com.teamsweepy.greywater.entity.item.weapons.Wrench;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class Item extends Entity {

	protected boolean onGround = false;
	private Sprite groundSprite;
	private String name;

    /** Item dropping TEST **/
    private boolean bounce = false;
    private Point2F gotoPoint, basePos;

    private float x, y, z;
    private float angle;
    private float toRadians = (float)(Math.PI / 180);


	public Item(String name, float x, float y, int width, int height) {
		this.name = name;
		physicsComponent = new Hitbox(x * 50 + 25, y * 50 + 25, width, height, 0);
		this.graphicsComponent = new Sprite(getName());
		this.groundSprite = new Sprite(getName());
	}



    public void throwOnGround(Point2F objectiveCoordinates, Mob thrower) {
		onGround = true;
//		physicsComponent.setLocation(objectiveCoordinates.x, objectiveCoordinates.y);
        gotoPoint = objectiveCoordinates;
        basePos = thrower.getLocation();
        physicsComponent.setLocation(basePos.x, basePos.y);

        System.out.println(gotoPoint+" | "+basePos);

        thrower.getLevel().addNewFloorItem(this);

        bounce = true;
        angle = basePos.angle(gotoPoint);
        x = y = z = 0;
    }

	public void pickup() {
		onGround = false;
	}

    @Override
    public void tick(float deltaTime) {
        if (onGround) {
            if(bounce) {
                float dx = (float)(Math.cos(angle) * 1F);
                float dy = (float)(Math.sin(angle) * 1F);

                float curDistance = basePos.distance(getX(), getY());
                float maxDistance = basePos.distance(gotoPoint);

                x = getX() + dx;
                y = getY() + dy;

                z = getArcHeight(curDistance, maxDistance, 100);

                physicsComponent.setLocation(x, y);
                if(curDistance > maxDistance) {
                    bounce = false;
                }
            }
        }
        super.tick(deltaTime);
    }

    private float getArcHeight(float currentDistance, float maxDistance, float maxLength) {
        float mid = maxDistance / 2;
        if(currentDistance > mid) {
            // Height is falling
            float dif = (mid - (currentDistance - mid)) / mid;
            return maxLength * dif;
        } else {
            float dif = 1 - ((mid - currentDistance) / mid);
            System.out.println(dif+", "+currentDistance+", "+maxLength);
            return (maxLength * dif);
        }

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
//			Point2F p = Globals.toIsoCoord(getX(), getY());
			Point2F p = Globals.toIsoCoord(x, y);
			groundSprite.render(g, p.x, p.y + z);
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
