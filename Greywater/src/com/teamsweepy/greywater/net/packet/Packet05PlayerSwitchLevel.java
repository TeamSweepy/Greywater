
package com.teamsweepy.greywater.net.packet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.teamsweepy.greywater.entity.component.events.net.PlayerSwitchLevelEvent;
import com.teamsweepy.greywater.entity.level.LevelHandler;


public class Packet05PlayerSwitchLevel extends Packet {

	private int playerID;
	private int fromLevelID;
	private int toLevelID;

	public void init(int playerID, int fromLevelID, int toLevelID) {
		this.playerID = playerID;
		this.fromLevelID = fromLevelID;
		this.toLevelID = toLevelID;
	}

	@Override
	public void processServer(Server server, Connection con) {}

	@Override
	public void processClient(Client client) {
		System.out.println("[CLIENT] Player " + playerID + " switched to level " + toLevelID);
		LevelHandler.getLevel(fromLevelID).addNetEvent(new PlayerSwitchLevelEvent(playerID, fromLevelID, toLevelID));
	}

}
