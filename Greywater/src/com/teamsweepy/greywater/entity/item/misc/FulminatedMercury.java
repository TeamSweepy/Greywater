
package com.teamsweepy.greywater.entity.item.misc;

import com.teamsweepy.greywater.entity.item.IDs;
import com.teamsweepy.greywater.entity.item.Item;



public class FulminatedMercury extends Item {

	public FulminatedMercury() {
		super("FulminatedMercury", "FulminatedMercury_Floor", 0, 0, 62, 62);
	}

	@Override
	public int getID() {
		return IDs.FULMINATED_MERCURY.getID();
	}

}
