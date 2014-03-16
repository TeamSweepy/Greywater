package com.teamsweepy.greywater.entities.components.events;

import java.util.EventObject;

public class GameEvent extends EventObject {
	
	public Object Source;

	public GameEvent(Object source) {
		super(source);
	}
	
	

}
