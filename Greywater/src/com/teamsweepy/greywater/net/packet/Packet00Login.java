
package com.teamsweepy.greywater.net.packet;

import java.awt.Point;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.PlayerMP;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2F;



public class Packet00Login extends Packet {

	public int ID = -1;

	public void init(int ID) {
		this.ID = ID;
	}

	@Override
	public void processServer(Server server) {
		ID = Level.level.getFreeID();
		if (ID == -1) { // didn't work
			System.out.println("A valid ID wasn't given");
		}
	}

	@Override
	public void processClient(Client client) {
		Level.level.schedulePlayer(new Point2F(4, 9), ID);
	}
}
