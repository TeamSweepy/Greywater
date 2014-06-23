
package com.teamsweepy.greywater.net.packet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;


public abstract class Packet {

	public abstract void processServer(Server server);

	public abstract void processClient(Client client);
}
