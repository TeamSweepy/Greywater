
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.ItemSlot;
import com.teamsweepy.greywater.ui.gui.subgui.Plane;
import com.teamsweepy.greywater.ui.item.misc.VoltCell;
import com.teamsweepy.greywater.ui.item.potions.HealthPotion;
import com.teamsweepy.greywater.ui.item.potions.Mercury;
import com.teamsweepy.greywater.ui.item.weapons.Wrench;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;


public class Inventory extends GUIComponent {

	protected ArrayList<ItemSlot> itemSlots = new ArrayList<ItemSlot>();

	public Inventory() {
		sprite = new Sprite("inventory");
		pos = new Point2F(1000, 200);
		size = new Point2F(437, 608);
		initSubComponents();

		itemSlots.get(0).setItem(new HealthPotion());
		itemSlots.get(1).setItem(new Mercury());
		itemSlots.get(2).setItem(new Wrench());
		itemSlots.get(3).setItem(new VoltCell());

		visible = false;
	}

	@Override
	protected void initSubComponents() {
		subComponents.add(new Plane(pos.x, pos.y, size.x, size.y));
		//subComponents.add(new ItemSlot(this, pos.x + 100, pos.y + 100, 62, 62));// test slot
		int slotSize = 62;
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 5; y++) {
				ItemSlot newSlot = new ItemSlot(this, pos.x + x * slotSize + 33, pos.y + y * slotSize + 33, slotSize, slotSize);
				subComponents.add(newSlot);
				itemSlots.add(newSlot);
			}
		}
	}

	@Override
	public void handleInput(Point2F mousePosition, int event) {
		super.handleInput(mousePosition, event);
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
	}

}
