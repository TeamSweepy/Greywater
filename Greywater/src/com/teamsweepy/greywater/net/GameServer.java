
package com.teamsweepy.greywater.net;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.net.packet.Packet;
import com.teamsweepy.greywater.net.packet.Packet00Login;
import com.teamsweepy.greywater.net.packet.Packet01Disconnect;
import com.teamsweepy.greywater.net.packet.Packet02SetPlayerPath;
import com.teamsweepy.greywater.net.packet.Packet03AddPlayer;
import com.teamsweepy.greywater.net.packet.Packet04RequestAllPlayers;
import com.teamsweepy.greywater.net.packet.Packet05PlayerSwitchLevel;


public class GameServer {

	public Server server;

	public GameServer() {
		server = new Server();
		registerPackets();
		server.addListener(new ServerListener(server));
		try {
			server.bind(54555, 54556);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.start();
	}

	private void registerPackets() {
		Kryo kryo = server.getKryo();
		kryo.register(Point2F.class);
		kryo.register(Packet00Login.class);
		kryo.register(Packet01Disconnect.class);
		kryo.register(Packet02SetPlayerPath.class);
		kryo.register(Packet03AddPlayer.class);
		kryo.register(Packet04RequestAllPlayers.class);
		kryo.register(Packet05PlayerSwitchLevel.class);
	}

	public void sendToAll(Object o) {
		server.sendToAllTCP(o);
	}

}

class ServerListener extends Listener {

	private Server server;

	public ServerListener(Server server) {
		this.server = server;
	}

	@Override
	public void connected(Connection c) {
		super.connected(c);
		System.out.println("[SERVER] Someone has connected from " + c.getRemoteAddressTCP());
	}

	@Override
	public void disconnected(Connection c) {
		super.disconnected(c);
		System.out.println("[SERVER] Someone has disconnected from " + c.getRemoteAddressTCP());
	}

	@Override
	public void received(Connection con, Object data) {
		if (data instanceof Packet) {
			System.out.println("[SERVER] received " + data.getClass().getName() + "  from " + con.getRemoteAddressTCP());
			Packet p = (Packet) data;
			p.processServer(server, con);
			if (p.sendToAll)
				server.sendToAllTCP(data);
		}
	}
}
