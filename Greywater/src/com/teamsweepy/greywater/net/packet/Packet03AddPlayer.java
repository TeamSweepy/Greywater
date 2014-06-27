
package com.teamsweepy.greywater.net.packet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2F;


public class Packet03AddPlayer extends Packet {

	public int ID = -1;
	public Point2F position;

	public void init(int ID, Point2F position) {
		this.ID = ID;
		this.position = position;
	}

	@Override
	public void processServer(Server server, Connection con) {}

	@Override
	public void processClient(Client client) {
		if (ID != Player.localPlayerID)
			Level.level.schedulePlayer(position, ID);
	}

}
