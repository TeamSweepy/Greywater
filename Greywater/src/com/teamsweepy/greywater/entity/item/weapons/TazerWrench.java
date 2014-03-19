
package com.teamsweepy.greywater.entity.item.weapons;

import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.item.IDs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TazerWrench extends Weapon {

	public TazerWrench() {
		super("tazerwrench", 0, 0, 62, 62);

	}


	@Override
	public int getID() {
		return IDs.TAZER_WRENCH.getID();
	}
}
