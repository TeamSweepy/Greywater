
package com.teamsweepy.greywater.ui.item;


public enum IDs {
	HEALTH_POTION(01),
	MERCURY(02),
	VOLT_CELL(03),
	WRENCH(04),
	TAZER_WRENCH(05);


	
	private int ID;

	IDs(int id) {
		ID = id;
	}

	public int getID() {
		return ID;
	}
}
