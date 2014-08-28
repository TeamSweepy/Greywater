
package com.teamsweepy.greywater.net.packet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.entity.level.LevelHandler;

public class Packet01Disconnect extends Packet {

	public int ID;
	public int levelID;

	public void init(int ID, int levelID) {
		this.ID = ID;
		this.levelID = levelID;
	}

	@Override
	public void processServer(Server server, Connection con) {}

	@Override
	public void processClient(Client client) {
		LevelHandler.getLevel(levelID).removePlayer(LevelHandler.getLevel(levelID).getPlayerByID(ID));
	}

}
