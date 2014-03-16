/**
 * Quest interface to allow quests to know about when mobs do stuff.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Barnes
 */

package com.teamsweepy.greywater.entities.components.events;


public interface GameEventListener {
	
	public void handleGameEvent(GameEvent ge);

}
