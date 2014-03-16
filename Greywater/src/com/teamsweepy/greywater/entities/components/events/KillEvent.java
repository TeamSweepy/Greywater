
package com.teamsweepy.greywater.entities.components.events;

import com.teamsweepy.greywater.entities.Mob;


public class KillEvent extends GameEvent {

	public Mob deadVictim;

	public KillEvent(Object source, Mob victim) {
		super(source);
		deadVictim = victim;
	}

}



