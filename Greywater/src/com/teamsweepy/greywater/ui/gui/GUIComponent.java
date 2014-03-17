/**
 * 
 */

package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.SubGUIComponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GUIComponent {

	protected boolean visible = true; // GUIComponents can be hidden - they are visible by default
	protected boolean ticking = true;
	protected Point2F pos;
	protected Point2F size;

	public Sprite sprite;

	protected ArrayList<SubGUIComponent> subComponents = new ArrayList<SubGUIComponent>();

	// add a plane in order to get inputs

	public GUIComponent() {
		pos = new Point2F();
		size = new Point2F();
		visible = false; // no need for empty render method, just flag imageless components
	}
	
	protected void initSubComponents() {}

	public void handleInput(Point2F mousePosition, int event) {
		SubGUIComponent childOnTop = null;

		for (SubGUIComponent child : subComponents) {
			if (child.intersects(mousePosition)) {
				childOnTop = child;
			}
		}
		if (childOnTop == null)
			return; // shouldn't ever happen

		childOnTop.handleInput(mousePosition, event);
	}

    public void handleInput(Point2F mousePosition, int amount, int event) {
        SubGUIComponent childOnTop = null;

        for (SubGUIComponent child : subComponents) {
            if (child.intersects(mousePosition)) {
                childOnTop = child;
            }
        }
        if (childOnTop == null)
            return; // shouldn't ever happen

        childOnTop.handleInput(mousePosition, amount, event);
    }

	public void tick(float deltaTime) {
		for (SubGUIComponent child : subComponents) {
			child.tick(deltaTime);
		}
	}

	public void render(SpriteBatch batch) {
		if (!visible)
			return;
		if (sprite != null)
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
		for (SubGUIComponent child : subComponents) {
			child.setVisible(visible);
		}
	}
	
	public void toggleVisibility(){
		visible = !visible;
		for (SubGUIComponent child : subComponents) {
			child.toggleVisibility();
		}
	}

	public boolean isTicking() {
		return ticking;
	}

	public void setTicking(boolean ticking) {
		this.ticking = ticking;
		for (SubGUIComponent child : subComponents) {
			child.setTicking(ticking);
		}
	}

	public void addGUIComponent(SubGUIComponent component) {
		subComponents.add(component);
	}


}
