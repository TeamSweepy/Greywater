
package com.teamsweepy.greywater.entity.item.potions;

import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.item.IDs;
import com.teamsweepy.greywater.entity.item.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Mercury extends Item {

	public Mercury() {
		super("mercury", 0, 0, 62, 62);
	}


	@Override
	public int getID() {
		return IDs.MERCURY.getID();
	}

}
