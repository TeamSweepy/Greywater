/**
 * 
 */

package com.teamsweepy.greywater.ui.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.Button;
import com.teamsweepy.greywater.ui.gui.subgui.ButtonCircular;
import com.teamsweepy.greywater.ui.gui.subgui.Plane;
import com.teamsweepy.greywater.ui.gui.subgui.ProgressBarCircular;

public class HUD extends GUIComponent {

	public HUD() {
		sprite = new Sprite("HUD-1600");
		pos = new Point2F(0, 0);

		size = new Point2F(Gdx.graphics.getWidth(), sprite.getImageHeight());

		initSubComponents();

		visible = true;
	}

	protected void initSubComponents() {
		subComponents.add(new Plane(0, 0, 1600, 180));// 1600 is the aproximate height of the HUD - it is the background of the HUD

		subComponents.add(new ButtonCircular(800, 290 - 39, 37) {

			@Override
			protected void clicked() {
				GUI.getInventory().visible = !GUI.getInventory().visible;
			}
		});// The inventory open button

		ProgressBarCircular hpBar = new ProgressBarCircular(64, 12);// health bar
		subComponents.add(hpBar);
		hpBar.setMaxRotation(0);
		hpBar.setMinRotation(270);
		hpBar.setMaxValue(100);
		hpBar.setMinValue(0);

		ProgressBarCircular manaBar = new ProgressBarCircular(1336, 12);// mana bar
		subComponents.add(manaBar);
		manaBar.setMaxRotation(0);
		manaBar.setMinRotation(270);
		manaBar.setMaxValue(100);
		manaBar.setMinValue(0);

		/*Button button = new Button(100, 100, 100, 100, "EXIT") {

			@Override
			protected void clicked() {
				System.out.println("JeremySucks");
			}
		};
		subComponents.add(button);*/
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch); //unnecessary unless you plan to add to this
	}
}
