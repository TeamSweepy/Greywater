
package com.teamsweepy.greywater.entity.item.potions;

import com.teamsweepy.greywater.entity.item.IDs;
import com.teamsweepy.greywater.entity.item.Item;


public class Mercury extends Item {

	public Mercury() {
		super("mercury", "mercury_floor", 0, 0, 62, 62);
	}


	@Override
	public int getID() {
		return IDs.MERCURY.getID();
	}

}
