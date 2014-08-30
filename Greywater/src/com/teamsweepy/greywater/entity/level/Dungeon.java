
package com.teamsweepy.greywater.entity.level;

import com.teamsweepy.greywater.entity.ClockWorm;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.Sweepy;


public class Dungeon extends Level {

	// empty for now since it has all the Level stuff

	public Dungeon(String mapPath) {
		super(mapPath);
		Sweepy sweepy = new Sweepy(30, 5, this);
		ClockWorm clockWorm = new ClockWorm(this, Player.getLocalPlayer());
		mobList.add(sweepy);
		mobList.add(clockWorm);
		interactiveList.add(sweepy);
		interactiveList.add(clockWorm);
	}

	@Override
	public void tick(float deltaTime) {
		super.tick(deltaTime);
	}

}
