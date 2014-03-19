
package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.entity.item.Item;

// just a class to do the hierarchy for the moment, later on adding damage and durability and so on
public abstract class Weapon extends Item {
	
	public Weapon(String name, float x, float y, int width, int height){
		super(name, x, y, width, height);
	}

	public abstract int getID();

}
