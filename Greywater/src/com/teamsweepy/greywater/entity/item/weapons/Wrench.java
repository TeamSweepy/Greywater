
package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.item.IDs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Wrench extends Weapon {

	public Wrench() {
		super("tavwrench", 0, 0, 62, 62);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return IDs.WRENCH.getID();
	}

}
