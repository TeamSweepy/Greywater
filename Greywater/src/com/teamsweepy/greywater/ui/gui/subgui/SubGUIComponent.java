
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.engine.input.InputGUI;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.GUIComponent;


public class SubGUIComponent extends GUIComponent {

	public void handleInput(Point2F mousePosition, int event) {
		switch (event) {
			case InputGUI.MOUSE_DOWN:
				clicked();
				break;
			case InputGUI.MOUSE_MOVED:
				break;
			case InputGUI.MOUSE_UP:
				break;
		}
	}

	protected void clicked() {

	}

}
