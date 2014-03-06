
package com.teamsweepy.greywater.ui.item.misc;

import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.ui.item.IDs;
import com.teamsweepy.greywater.ui.item.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class VoltCell extends Item {

	public VoltCell() {
		sprite = new Sprite("voltcell");
	}

	@Override
	public void render(SpriteBatch g, float x, float y, float w, float h) {
		super.render(g, x, y, w, h);
	}

	@Override
	public int getID() {
		return IDs.VOLT_CELL.getID();
	}
}
