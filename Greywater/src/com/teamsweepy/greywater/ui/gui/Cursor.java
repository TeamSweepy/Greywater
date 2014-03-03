
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.engine.input.InputGUI;
import com.teamsweepy.greywater.ui.item.Item;
import com.teamsweepy.greywater.ui.item.potions.HealthPotion;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Cursor extends GUIComponent {

	protected Item item;

	public Cursor() {
		super();
		item = new HealthPotion();
	}

	@Override
	public void tick() {
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
