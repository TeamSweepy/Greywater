
package com.teamsweepy.greywater.net.packet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.teamsweepy.greywater.entity.component.events.net.PlayerConnectEvent;
import com.teamsweepy.greywater.entity.level.LevelHandler;
import com.teamsweepy.greywater.math.Point2F;



public class Packet00Login extends Packet {

	public int ID = -1;
	public int levelID;

	public void init(int PlayerID, int levelID) {
		this.ID = PlayerID;
		this.levelID = levelID;
	}

	@Override
	public void processServer(Server server, Connection con) {
		ID = LevelHandler.getLevel(levelID).getFreeID();
		if (ID == -1) { // didn't work
			System.out.println("A valid ID wasn't given");
		}
	}

	@Override
	public void processClient(Client client) {
		LevelHandler.getLevel(levelID).fireEvent(new PlayerConnectEvent(levelID, ID, new Point2F(4, 9)));
	}
}
