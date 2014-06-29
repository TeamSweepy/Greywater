
package com.teamsweepy.greywater.net.packet;

import java.util.ArrayList;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.PlayerMP;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2F;


public class Packet04RequestAllPlayers extends Packet {

	public boolean sendToAll = false;

	@Override
	public void processServer(Server server, Connection con) {
		ArrayList<PlayerMP> players = Level.level.getAllPlayers();
		for (PlayerMP p : players) {
			Packet03AddPlayer packet = new Packet03AddPlayer();
			if (p.ID == Player.localPlayerID) {
				Player p1 = PlayerMP.getLocalPlayer();
				packet.init(p.ID, Point2F.convertPoint(p1.getTileLocation()));
			} else
				packet.init(p.ID, Point2F.convertPoint(p.getTileLocation()));
			System.out.println("SPAWNING SOMEONE AT :" + Point2F.convertPoint(p.getTileLocation()));
			System.out.println("[SERVER] Sending client " + con.getID() + " a player already on with ID " + p.ID);
			con.sendTCP(packet);
		}
	}

	@Override
	public void processClient(Client client) {}

}
