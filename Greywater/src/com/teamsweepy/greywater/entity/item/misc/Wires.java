
package com.teamsweepy.greywater.entity.item.misc;

import com.teamsweepy.greywater.entity.item.IDs;
import com.teamsweepy.greywater.entity.item.Item;


public class Wires extends Item {

	public Wires() {
		super("Wires", "Wires_Floor", 0, 0, 62, 62);
	}

	@Override
	public int getID() {
		return IDs.WIRES.getID();
	}

}
