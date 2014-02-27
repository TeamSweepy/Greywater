
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GUI {

	public static ArrayList<GUIComponent> guiComponents = new ArrayList<GUIComponent>();

	public static void addGUIComponent(GUIComponent component) {
		guiComponents.add(component);
	}

	/** Updates all the guicomponents */
	public static void tick() {
		for (int i = 0; i < guiComponents.size(); i++) {
			guiComponents.get(i).tick();
		}
	}

	/** Renders all the guicomponents */
	public static void render(SpriteBatch batch) {
		for (int i = 0; i < guiComponents.size(); i++) {
			guiComponents.get(i).render(batch);
		}
	}

	public static void initGUI() {
		addGUIComponent(new HUD());
	}

	public static boolean handleInput(int event, Point2F mousePosition) {
		for (GUIComponent guiC : guiComponents) {
			if (guiC.intersects(mousePosition)) {
				guiC.handleInput(mousePosition, event);
				return true;
			}
		}

		return false;
	}

}
