
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.engine.input.InputGUI;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.item.Item;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Cursor extends GUIComponent {

	protected Item item;

	public Cursor() {
		super();
		item = null;
		visible = true;
	}

	public boolean handleClick() {
		boolean inputTaken = false;
		if (item != null) {
			throwItem();
			inputTaken = true;
		}
		return inputTaken;
	}

	public void throwItem() {
		Point2F throwPoint = Globals.calculateRandomLocation(Player.getLocalPlayer().getLocation(), Player.getLocalPlayer().getLevel(), .7f);
//		item.throwOnGround(throwPoint, Player.getLocalPlayer());
		item.throwOnGround(Globals.toNormalCoord(pos), Player.getLocalPlayer());
		item = null;
	}

	@Override
	public void tick(float deltaTime) {
		pos = InputGUI.position.sub(31);
	}

	@Override
	public void render(SpriteBatch batch) {
		if (item == null)
			return;
		sprite = item.getSprite();
		super.render(batch);
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
