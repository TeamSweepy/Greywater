
package com.teamsweepy.greywater.net;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.net.packet.Packet;
import com.teamsweepy.greywater.net.packet.Packet00Login;
import com.teamsweepy.greywater.net.packet.Packet01Disconnect;
import com.teamsweepy.greywater.net.packet.Packet02SetPlayerPath;
import com.teamsweepy.greywater.net.packet.Packet03AddPlayer;
import com.teamsweepy.greywater.net.packet.Packet04RequestAllPlayers;


public class GameClient {

	public Client client;

	public GameClient(boolean isServer) {
		client = new Client();
		registerPackets();
		client.addListener(new ClientListener(client));

		client.start();

		String ipAdress = JOptionPane.showInputDialog("Enter server's ip adress", "localhost");
		System.out.println("[CLIENT] trying to connect to " + ipAdress);
		try {
			client.connect(500, ipAdress, 54555, 54556);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void registerPackets() {
		Kryo kryo = client.getKryo();
		kryo.register(Point2F.class);
		kryo.register(Packet00Login.class);
		kryo.register(Packet01Disconnect.class);
		kryo.register(Packet02SetPlayerPath.class);
		kryo.register(Packet03AddPlayer.class);
		kryo.register(Packet04RequestAllPlayers.class);
	}

	public void send(Object o) {
		client.sendTCP(o);
	}
}

class ClientListener extends Listener {

	private Client client;

	public ClientListener(Client client) {
		this.client = client;
	}

	public void connected(Connection c) {
		super.connected(c);
		System.out.println("[CLIENT] connected to " + c.getRemoteAddressTCP());
		client.sendTCP(new Packet00Login());
	}

	@Override
	public void disconnected(Connection c) {
		super.disconnected(c);
		System.out.println("[CLIENT] disconnected from " + c.getRemoteAddressTCP());
	}


	@Override
	public void received(Connection con, Object data) {
		if (data instanceof Packet) {
			System.out.println("[CLIENT] received " + data.getClass().getName() + "  from " + con.getRemoteAddressTCP());
			Packet p = (Packet) data;
			p.processClient(client);
		}
	}
}
