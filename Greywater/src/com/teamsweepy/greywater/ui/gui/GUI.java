
package com.teamsweepy.greywater.ui.gui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

}
