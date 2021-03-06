/**
 * Static class, manages GUIComponents. GUIComponents have their sub-elements as children.
 * 
 * @author Ziga
 */

package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.engine.input.InputHandler;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GUI {

	/** LAYERS **/
	public static final int MID_LAYER = 0;
	public static final int TOP_LAYER = 1;

	public static ArrayList<GUIComponent> midGuiComponents = new ArrayList<GUIComponent>();
	public static ArrayList<GUIComponent> topGuiComponents = new ArrayList<GUIComponent>();

	public static void addGUIComponent(GUIComponent component) {
		addGUIComponent(component, MID_LAYER);
	}

	public static void addGUIComponent(GUIComponent component, int layer) {
		if (layer == MID_LAYER) {
			midGuiComponents.add(component);
		} else if (layer == TOP_LAYER) {
			topGuiComponents.add(component);
		} else {
			// TODO: Handle the errors
		}
	}

	/** Updates all the guicomponents */
	public static void tick(float deltaTime) {
		for (GUIComponent guiC : midGuiComponents) {
			guiC.tick(deltaTime);
		}

		for (GUIComponent guiC : topGuiComponents) {
			guiC.tick(deltaTime);
		}
	}

	/** Renders all the guicomponents */
	public static void render(SpriteBatch batch) {
		for (GUIComponent guiC : midGuiComponents) {
			guiC.render(batch);
		}
		for (GUIComponent guiC : topGuiComponents) {
			guiC.render(batch);
		}
	}

	/** Used when dealing with different types of input **/
	public static boolean handleInput(int event, Point2F mousePosition, int amount) {
		// guiC.visible is protected, how can we reference it from here....
		for (GUIComponent guiC : topGuiComponents) {
			if (guiC.isVisible() && guiC.intersects(mousePosition)) {
				guiC.handleInput(mousePosition, amount, event);
				return true;
			}
		}
		// Try to invert the loop
		for (int i = midGuiComponents.size() - 1; i >= 0; i--) {
			GUIComponent guiC = midGuiComponents.get(i);
			if (guiC.isVisible() && guiC.intersects(mousePosition)) {
				guiC.handleInput(mousePosition, amount, event);
				return true;
			}
		}
		return false;
	}

	/** Passes input to all GUIComponents. If they do not handle it, returns false to indicate the game needs to deal with it */
	public static boolean handleInput(int event, Point2F mousePosition, boolean rightClick) {
		for (GUIComponent guiC : topGuiComponents) {
			if (guiC.isVisible() && guiC.intersects(mousePosition)) {
				guiC.handleInput(mousePosition, event, rightClick);
				return true;
			}
		}
		if (event == InputHandler.WHEEL_SCROLL) {
			System.out.println("Scrolling");
		}
		// Try to invert the loop
		for (int i = midGuiComponents.size() - 1; i >= 0; i--) {
			GUIComponent guiC = midGuiComponents.get(i);
			if (guiC.isVisible() && guiC.intersects(mousePosition)) {
				guiC.handleInput(mousePosition, event, rightClick);

				if (event == InputHandler.MOUSE_DOWN) {
					GUIComponent tempGUI = guiC;
					midGuiComponents.remove(tempGUI);
					midGuiComponents.add(guiC);
				}

				return true;
			}
		}
		return false;
	}

	public static HUD getHUD() {
		for (GUIComponent guiC : topGuiComponents) {
			if (guiC instanceof HUD) {
				return (HUD) guiC;
			}
		}
		return null;
	}

	public static Inventory getInventory() {
		for (GUIComponent guiC : midGuiComponents) {
			if (guiC instanceof Inventory) {
				return (Inventory) guiC;
			}
		}
		return null;
	}

	public static Cursor getCursor() {
		for (GUIComponent guiC : topGuiComponents) {
			if (guiC instanceof Cursor) {
				return (Cursor) guiC;
			}
		}
		return null;
	}

	public static void setVisibility(boolean visible) {
		for (GUIComponent guiC : topGuiComponents) {
			guiC.setVisible(false);
		}

		for (GUIComponent guiC : midGuiComponents) {
			guiC.setVisible(false);
		}

	}
	
	public static void setTicking(boolean ticking) {
		for (GUIComponent guiC : topGuiComponents) {
			guiC.setVisible(false);
		}

		for (GUIComponent guiC : midGuiComponents) {
			guiC.setVisible(false);
		}

	}

}
