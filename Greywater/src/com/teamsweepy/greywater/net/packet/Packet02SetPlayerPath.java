
package com.teamsweepy.greywater.net.packet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2F;


public class Packet02SetPlayerPath extends Packet {

	public int ID;
	public Point2F start;
	public Point2F end;

	public void init(int ID, Point2F start, Point2F end) {
		this.ID = ID;
		this.start = start;
		this.end = end;
	}

	@Override
	public void processServer(Server server) {

	}

	@Override
	public void processClient(Client client) {
		System.out.println(start + " " + end);
		Level.level.getPlayerByID(ID).getPather().createPath(Point2F.convertPoint2F(start), Point2F.convertPoint2F(end));
		System.out.println("moving a player");
	}

}
