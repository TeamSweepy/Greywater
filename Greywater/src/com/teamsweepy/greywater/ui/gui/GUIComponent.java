
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.SubGUIComponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GUIComponent {

	protected boolean visible = true; // GUIComponents can be hidden - they are visible by default

	protected Point2F pos;
	protected Point2F size;

	public Sprite sprite;

	protected ArrayList<SubGUIComponent> subComponents = new ArrayList<SubGUIComponent>();

	// add a plane in order to get inputs

	public GUIComponent() {
		pos = new Point2F();
		size = new Point2F();
	}

	protected void initSubComponents() {}

	public void handleInput(Point2F mousePosition, int event) {
		SubGUIComponent childOnTop = null;
		for (SubGUIComponent child : subComponents) {
			if (child.intersects(mousePosition)) {
				childOnTop = child;
			}
		}
		if (childOnTop == null) // shouldn't ever happen
			return;
		childOnTop.handleInput(mousePosition, event);
	}

	public void tick() {}

	public void render(SpriteBatch batch) {
		if (!visible)
			return;

		sprite.render(batch, pos.x - Camera.getDefault().xOffsetAggregate, pos.y - Camera.getDefault().yOffsetAggregate);


		// Render all the subcomponents
		for (SubGUIComponent child : subComponents) {
			child.render(batch);
		}
	}

	public boolean intersects(Point2F mousePosition) {
		if (!visible)
			return false;
		for (SubGUIComponent child : subComponents) {
			if (child.intersects(mousePosition))
				return true;
		}
		return false;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
