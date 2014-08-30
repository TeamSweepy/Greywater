
package com.teamsweepy.greywater.net.packet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.entity.level.LevelHandler;
import com.teamsweepy.greywater.math.Point2F;


public class Packet02SetPlayerPath extends Packet {

	public int ID;
	public int levelID;
	public Point2F start;
	public Point2F end;

	public void init(int ID, Point2F start, Point2F end, int levelID) {
		this.ID = ID;
		this.levelID = levelID;
		this.start = start;
		this.end = end;
	}

	@Override
	public void processServer(Server server, Connection con) {

	}

	@Override
	public void processClient(Client client) {
		//System.out.println("[CLIENT] Setting path to player " + ID + ", from " + start + " to " + end);
		Player player = LevelHandler.getLevel(levelID).getPlayerByID(ID);
		if (player == null)
			return;
		player.getPather().createPath(Point2F.convertPoint2F(start), Point2F.convertPoint2F(end));
	}

}
