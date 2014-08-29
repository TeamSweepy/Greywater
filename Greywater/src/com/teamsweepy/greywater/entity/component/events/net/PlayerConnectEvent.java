
package com.teamsweepy.greywater.entity.component.events.net;

import com.teamsweepy.greywater.math.Point2F;


public class PlayerConnectEvent extends NetEvent {

	public int levelID;
	public int playerID;
	public Point2F position;

	public PlayerConnectEvent(int levelID, int playerID, Point2F position) {
		this.levelID = levelID;
		this.playerID = playerID;
		this.position = position;
	}

}
