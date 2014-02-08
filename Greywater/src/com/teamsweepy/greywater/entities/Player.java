package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;

import com.badlogic.gdx.Gdx;


public class Player extends Mob{
	
	private float elTime = 0f;
	
	public Player(float x, float y){
		super.name = "Tavish";
		currentDirection = "South";
		this.graphicsComponent = new Sprite(name, "Stand_South");
//		x = (int) (x * 50);
//		y = (int) (y * 50);
		this.physicsComponent = new Hitbox(x, y, 35, 35, 1f);
		this.walkCycleDuration = 1;
		graphicsComponent.setImage(.6f, "Walk_South", Sprite.LOOP);
	}

	@Override
	protected void getInput() {
		// TODO Auto-generated method stub
		elTime += Gdx.graphics.getDeltaTime();
		System.out.println(elTime + "t");
		if(elTime > 10 && elTime < 15){
			graphicsComponent.setImage(.6f, "Walk_East", Sprite.LOOP);
		}
		if(elTime > 20){
			graphicsComponent.setImage(.6f, "Attack_East", Sprite.LOOP);
		}
		
	}

	@Override
	protected void attack(Mob enemy) {
		// TODO Auto-generated method stub
		
	}
	
	

}
