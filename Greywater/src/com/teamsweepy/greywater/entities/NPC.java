
package com.teamsweepy.greywater.entities;

import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.entities.components.ai.PathfinderMotor;
import com.teamsweepy.greywater.entities.level.Level;


public class NPC extends Mob {

	public NPC(float x, float y, Level level) {
		super();
		name = "NPC_South";
		physicsComponent = new Hitbox(x * 50 + 25, y * 50 + 25, 35, 35, 0 * 50);
		this.graphicsComponent = new Sprite(getName());
		graphicsComponent.addAnimListener(this);
		world = level;
		pather = new PathfinderMotor(PathfinderMotor.Method.POTENTIAL_FIELD);
		friendly = true;
		pather.updateMap(level);
	}
	
	public void tick(float deltaTime){
		
	}

	@Override
	protected void getInput() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void attack(Mob enemy) {
		// TODO Auto-generated method stub

	}

	public void interact(Mob interlocutor) {

	}

	@Override
	public boolean interact() {
		// TODO Auto-generated method stub
		return false;
	}

}
