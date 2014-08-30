
package com.teamsweepy.greywater.net.packet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.component.events.net.PlayerConnectEvent;
import com.teamsweepy.greywater.entity.level.LevelHandler;
import com.teamsweepy.greywater.math.Point2F;


public class Packet03AddPlayer extends Packet {

	public int ID = -1;// player ID
	public int levelID;
	public Point2F position;

	public void init(int ID, Point2F position, int levelID) {
		this.ID = ID;
		this.levelID = levelID;
		this.position = position;
	}

	@Override
	public void processServer(Server server, Connection con) {}

	@Override
	public void processClient(Client client) {
		if (ID != Player.localPlayer.ID)
			LevelHandler.getLevel(levelID).addNetEvent(new PlayerConnectEvent(levelID, ID, position));
	}

}
