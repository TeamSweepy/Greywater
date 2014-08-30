
package com.teamsweepy.greywater.entity.component.events.net;

/**
 * Contains data for player level switching
 * */
public class PlayerSwitchLevelEvent extends NetEvent {

	public int playerID;
	public int fromLevelID;
	public int toLevelID;

	public PlayerSwitchLevelEvent(int playerID, int fromLevelID, int toLevelID) {
		this.playerID = playerID;
		this.fromLevelID = fromLevelID;
		this.toLevelID = toLevelID;
	}

}
