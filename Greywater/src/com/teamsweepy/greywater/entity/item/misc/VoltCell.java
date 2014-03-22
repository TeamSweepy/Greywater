
package com.teamsweepy.greywater.entity.item.misc;

import com.teamsweepy.greywater.entity.item.IDs;
import com.teamsweepy.greywater.entity.item.Item;


public class VoltCell extends Item {

	public VoltCell() {
		super("voltcell", "voltcell", 0, 0, 62, 62);
	}

	@Override
	public int getID() {
		return IDs.VOLT_CELL.getID();
	}
}
