
package com.teamsweepy.greywater.entity.item;


public enum IDs {
	HEALTH_POTION(1),
	MERCURY(2),
	VOLT_CELL(3),
	WRENCH(4),
	TAZER_WRENCH(5),
	FULMINATED_MERCURY(6),
	BOMB(7),
	WIRES(8);

	private int ID;

	IDs(int id) {
		ID = id;
	}

	public int getID() {
		return ID;
	}
}
