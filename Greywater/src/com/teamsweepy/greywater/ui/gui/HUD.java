/**
 * 
 */

package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.ButtonCircular;
import com.teamsweepy.greywater.ui.gui.subgui.Plane;
import com.teamsweepy.greywater.ui.gui.subgui.ProgressBarCircular;

import com.badlogic.gdx.Gdx;

public class HUD extends GUIComponent {

	public static ProgressBarCircular hpBar;
	public static ProgressBarCircular manaBar;

	public HUD() {
		sprite = new Sprite("HUD-1600", true);
		pos = new Point2F(0, 0);
		size = new Point2F(Gdx.graphics.getWidth(), sprite.getImageHeight());
		initSubComponents();
		visible = true;
	}

	protected void initSubComponents() {
		subComponents.add(new Plane(0, 0, 1600, 180));// 1600 is the approximate height of the HUD - it is the background of the HUD

		subComponents.add(new ButtonCircular(800, 290 - 39, 37) {

			@Override
			protected void clicked(boolean rightClick) {
				Player.getLocalPlayer().getInventory().toggleVisibility();

			}
		});// The inventory open button

		{// Player bars for health and mana
			
			hpBar = new ProgressBarCircular(64, 12);// health bar
			subComponents.add(hpBar);
			hpBar.setMaxRotation(0);
			hpBar.setMinRotation(270);
			hpBar.setMaxValue(100);
			hpBar.setMinValue(0);

			manaBar = new ProgressBarCircular(1336, 12);// mana bar
			subComponents.add(manaBar);
			manaBar.setMaxRotation(0);
			manaBar.setMinRotation(270);
			manaBar.setMaxValue(100);
			manaBar.setMinValue(0);

		}
	}
}
