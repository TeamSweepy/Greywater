
package com.teamsweepy.greywater.entity.component.events;

import com.teamsweepy.greywater.entity.Mob;


public class KillEvent extends GameEvent {

	public Mob deadVictim;

	public KillEvent(Object source, Mob victim) {
		super(source);
		deadVictim = victim;
	}

}



