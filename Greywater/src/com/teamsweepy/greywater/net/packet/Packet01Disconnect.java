
package com.teamsweepy.greywater.net.packet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

//TODO whole disconnect thing
public class Packet01Disconnect extends Packet {

	public int ID;

	public void init(int ID) {
		this.ID = ID;
	}

	@Override
	public void processServer(Server server) {}

	@Override
	public void processClient(Client client) {}

}
