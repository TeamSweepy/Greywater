
package com.teamsweepy.greywater.entity;

import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2F;


public class PlayerMP extends Player {

	public final int ID;

	public PlayerMP(String name, float x, float y, int width, int height, float speed, Level level, int ID) {
		super(name, x, y, 35, 35, 1.75f, level, true);
		this.ID = ID;
		currentDirection = "South";
		this.walkCycleDuration = .5f;
		//graphicsComponent.setImage(3f, "Walk_South", Sprite.LOOP);
		armorRating = 14;
		System.out.println("a new player added");
	}

	@Override
	public void tick(float deltaTime) {
		super.tick(deltaTime);
		if (ID == localPlayerID)
			physicsComponent = getLocalPlayer().getPhysics();
	}

	public void setPath(Point2F start, Point2F end) {
		pather.createPath(Point2F.convertPoint2F(start), Point2F.convertPoint2F(end));
	}
}
