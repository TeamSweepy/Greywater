package com.teamsweepy.greywater.effect.spell;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.math.Point2F;


public class PersonalSpell extends Spell {

	Mob victim;
	Mob creator;
	
	public PersonalSpell(String effect, float repeatDurationSeconds, float dps, Mob affectedMob, Mob creator) {
		super(effect, repeatDurationSeconds, dps, creator.getLevel());
		victim = affectedMob;
		this.creator = creator;
		Point2F p = Globals.toIsoCoord(affectedMob.getX(), affectedMob.getY());
		updatePosition(p.x - 10, p.y + 30);
	}
	
	public void tick(float deltaTime){
		super.tick(deltaTime);
		if(super.duration > 0 || !spellEffect.isComplete()){
			System.out.println(deltaTime*damagePerSecond);
			victim.changeHP((int) (deltaTime*damagePerSecond));
		}
		Point2F p = Globals.toIsoCoord(victim.getX(), victim.getY());
		updatePosition(p.x - 10, p.y + 30);
	}

}
