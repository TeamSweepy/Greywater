
package com.teamsweepy.greywater.ui.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.ButtonCircular;
import com.teamsweepy.greywater.ui.gui.subgui.Plane;

public class HUD extends GUIComponent {

	public HUD() {
		sprite = new Sprite("HUD-1600");
		pos = new Point2F(0, 0);

		size = new Point2F(Gdx.graphics.getWidth(), sprite.getImageHeight());

		initSubComponents();

		visible = true;
	}

	protected void initSubComponents() {
		subComponents.add(new Plane(0, 0, 1600, 180));// 160 is the aproximate height of the HUD - it is the background of the HUD

		subComponents.add(new ButtonCircular(800, 290 - 39, 37) {

			@Override
			protected void clicked() {
				GUI.getInventory().visible = !GUI.getInventory().visible;
			}
		});// The inventory open button

	}

	public void tick() {

	}

	public void render(SpriteBatch batch) {
		super.render(batch); //unnecessary unless you plan to add to this
	}
}
