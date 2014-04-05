
package com.teamsweepy.greywater.entity.component.events;

import com.teamsweepy.greywater.entity.Mob;

import java.awt.Point;


public class EscortEvent extends GameEvent {

	public Mob escort;

	public EscortEvent(Mob source, Mob escort) {
		super(source);
		this.escort = escort;
	}



}
