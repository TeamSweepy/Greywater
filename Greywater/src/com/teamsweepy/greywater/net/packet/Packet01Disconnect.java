
package com.teamsweepy.greywater.net.packet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.teamsweepy.greywater.entity.level.Level;

public class Packet01Disconnect extends Packet {

	public int ID;

	public void init(int ID) {
		this.ID = ID;
	}

	@Override
	public void processServer(Server server, Connection con) {}

	@Override
	public void processClient(Client client) {
		Level.level.removePlayer(Level.level.getPlayerByID(ID));
	}

}
