
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.engine.input.InputGUI;
import com.teamsweepy.greywater.entities.level.Level;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.item.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class Cursor extends GUIComponent {

	protected Item item;

	public Cursor() {
		super();
		item = null;
		visible = true;
	}

	public boolean clicked() {
		boolean inputTaken = false;
		if (item != null) {
			throwItem();
			inputTaken = true;
		}

		else {
			Point2F inGame = Globals.toNormalCoord(pos);
			for (int a = 0; a < Level.getLevel().getFloorItems().size(); a++) {
				Item i = Level.getLevel().getFloorItems().get(a);
				if (i.getHitbox().contains(new Vector2(inGame.x, inGame.y))) {
					if (!GUI.getInventory().hasSpace())
						continue;
					GUI.getInventory().addItem(i);
					Level.getLevel().removeFloorItem(i);
					inputTaken = true;
					i.setOnGround(false);
				}
			}
		}
		return inputTaken;
	}

	public void throwItem() {
		item.throwOnGround(pos);
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
