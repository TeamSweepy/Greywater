package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.entities.level.Level;


public class Sweepy extends Mob {

	public Sweepy(String name, float x, float y, int width, int height, float speed, Level level, boolean isAStar) {
		super(name, x, y, width, height, speed, level, isAStar);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getInput() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void attack(Mob enemy) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean interact() {
		// TODO Auto-generated method stub
		return false;
	}

}
