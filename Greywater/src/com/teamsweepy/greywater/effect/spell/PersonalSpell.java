package com.teamsweepy.greywater.effect.spell;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.component.events.KillEvent;
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
			if(!victim.isAlive()) //dont bother affecting the dead
				return;
			
			victim.changeHP((deltaTime*damagePerSecond));
			if(!victim.isAlive()){ //but if the victim just died, do shit.
				creator.fireEvent(new KillEvent(creator, victim));
				creator.getKillList().add(victim);
				this.stop();
			}
		}
		Point2F p = Globals.toIsoCoord(victim.getX(), victim.getY());
		updatePosition(p.x - 10, p.y + 30);
	}

}
