
package com.teamsweepy.greywater.net.packet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;


public abstract class Packet {

	public boolean sendToAll = true;

	public abstract void processServer(Server server, Connection con);

	public abstract void processClient(Client client);
}
