package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;


public class Player extends Mob{
	
	public Player(float x, float y){
		super.name = "Tavish";
		currentDirection = "South";
		this.graphicsComponent = new Sprite(name, "Stand_South");
//		x = (int) (x * 50);
//		y = (int) (y * 50);
		this.physicsComponent = new Hitbox(x, y, 35, 35, 1f);
		this.walkCycleDuration = 1;
	}

	@Override
	protected void getInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void attack(Mob enemy) {
		// TODO Auto-generated method stub
		
	}
	
	

}
