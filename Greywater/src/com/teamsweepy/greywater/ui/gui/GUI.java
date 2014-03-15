/**
 * Static class, manages GUIComponents. GUIComponents have their sub-elements as children.
 * 
 * @author Ziga
 */

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
	public static void tick(float deltaTime) {
		for (int i = 0; i < guiComponents.size(); i++) {
			guiComponents.get(i).tick(deltaTime);
		}
	}

	/** Renders all the guicomponents */
	public static void render(SpriteBatch batch) {
		for (int i = 0; i < guiComponents.size(); i++) {
			guiComponents.get(i).render(batch);
		}
	}

	/** Passes input to all GUIComponents. If they do not handle it, returns false to indicate the game needs to deal with it */
	public static boolean handleInput(int event, Point2F mousePosition) {
		for (GUIComponent guiC : guiComponents) {
			if (guiC.intersects(mousePosition)) {
				guiC.handleInput(mousePosition, event);
				return true;
			}
		}
		return false;
	}

	public static HUD getHUD() {
		return (HUD) guiComponents.get(0);
	}

	public static Inventory getInventory() {
		return (Inventory) guiComponents.get(1);
	}

	public static Cursor getCursor() {
		return (Cursor) guiComponents.get(2);
	}

}
