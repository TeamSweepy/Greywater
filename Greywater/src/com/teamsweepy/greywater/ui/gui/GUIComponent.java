/**
 * 
 */

package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.engine.input.InputGUI;
import com.teamsweepy.greywater.entity.component.Hitbox;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.Text;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GUIComponent {

	protected boolean visible = true; // GUIComponents can be hidden - they are visible by default
	protected boolean ticking = true;

	protected Hitbox hitbox;
	protected Point2F pos;
	protected Point2F size;
	public Sprite sprite;

	protected ArrayList<GUIComponent> subComponents = new ArrayList<GUIComponent>();

	public GUIComponent() {
		pos = new Point2F();
		size = new Point2F();
		hitbox = new Hitbox();
		visible = false; // no need for empty render method, just flag imageless components
	}

	/** Creates a subcomponent centered on the X and Y given */
	public GUIComponent(float x, float y, float w, float h, boolean centered) {
		size = new Point2F(w, h);
		if (centered) {
			pos = centerOnPosition(x, y);
			hitbox = new Hitbox(pos.x, pos.y, (int) w, (int) h, 0f, true);
		} else {
			pos = new Point2F(x, y);
			hitbox = new Hitbox((int) x, (int) y, (int) w, (int) h, 0f, true);
		}
		visible = false; // no need for empty render method, just flag imageless components
	}

	public GUIComponent(float x, float y, float w, float h) {
		pos = new Point2F(x, y);
		size = new Point2F(w, h);
		hitbox = new Hitbox((int) x, (int) y, (int) w, (int) h, 0f, true);
		visible = false; // no need for empty render method, just flag imageless components
	}

	protected void initSubComponents() {}

	public void handleInput(Point2F mousePosition, int event, boolean rightClick) {
		GUIComponent childOnTop = null;

		for (GUIComponent child : subComponents) {
			if (child.intersects(mousePosition)) {
				childOnTop = child;
			}
		}
		if (childOnTop != null)
			childOnTop.handleInput(mousePosition, event, rightClick);
		else {
			switch (event) {
				case InputGUI.MOUSE_DOWN:
					clicked(rightClick);
					break;
				case InputGUI.MOUSE_MOVED:
					break;
				case InputGUI.MOUSE_UP:
					break;
			}
		}
	}

	protected void clicked(boolean rightClick) {
		System.out.println("Click function not defined for GUIComponent");
	}

	public void handleInput(Point2F mousePosition, int amount, int event) {
		GUIComponent childOnTop = null;

		for (GUIComponent child : subComponents) {
			if (child.intersects(mousePosition)) {
				childOnTop = child;
			}
		}
		if (childOnTop == null)
			return; // shouldn't ever happen

		childOnTop.handleInput(mousePosition, amount, event);
	}

	public void tick(float deltaTime) {
		for (GUIComponent child : subComponents) {
			child.tick(deltaTime);
		}
	}

	public void render(SpriteBatch batch) {
		if (!visible)
			return;
		if (sprite != null)
			sprite.render(batch, pos.x, pos.y);

		// Render all the subcomponents
		for (GUIComponent child : subComponents) {
			child.render(batch);
		}
	}

	public boolean intersects(Point2F mousePosition) {
		if (!visible)
			return false;
		for (GUIComponent child : subComponents) {
			if (child.intersects(mousePosition))
				return true;
		}
		return getHitbox().intersects(mousePosition);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		for (GUIComponent child : subComponents) {
			child.setVisible(visible);
		}
	}

	public void toggleVisibility() {
		visible = !visible;
		for (GUIComponent child : subComponents) {
			child.toggleVisibility();
		}
	}

	public boolean isTicking() {
		return ticking;
	}

	public void setTicking(boolean ticking) {
		this.ticking = ticking;
		for (GUIComponent child : subComponents) {
			child.setTicking(ticking);
		}
	}

	public void addGUIComponent(GUIComponent component) {
		subComponents.add(component);
	}

	public void removeGUIComponent(GUIComponent component) {
		subComponents.remove(component);
	}

	public void removeAllGUIComponent() {
		subComponents.clear();
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public Point2F centerOnPosition(Point2F location) {
		return centerOnPosition(location.x, location.y);
	}

	public Point2F centerOnPosition(float x, float y) {
		return new Point2F(x - size.x / 2, y - size.y / 2);

	}


}
