
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.engine.input.InputHandler;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.GUIComponent;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Scrollbar extends GUIComponent {

	private boolean horizontal;
	public Rectangle scroller;
	private Sprite scrollerGraphic;
	public float scrollPercentage = 0f;

	public Scrollbar(boolean horizontal, float x, float y, float w, float h) {
		super(x, y, w, h);

		sprite = new Sprite("ui/scroller", null, Texture.class);
		scrollerGraphic = new Sprite("ui/scroller_button", null, Texture.class);

		scroller = new Rectangle(x, y, w, h);

		this.horizontal = horizontal;
	}

	@Override
	public void handleInput(Point2F mousePosition, int event, boolean rightClick) {
		if (event == InputHandler.MOUSE_DRAGGED || event == InputHandler.MOUSE_DOWN) {
			float offsetY = (pos.y + size.y) - mousePosition.y;
			updatePosition(0, offsetY);
		}

		super.handleInput(mousePosition, event, rightClick);
	}

	public void scroll(int amount) {
		wheelScroll(amount);
	}

	public void wheelScroll(int amount) {
		if (horizontal) {
			updatePosition(scroller.x + (amount * 5), 0);
		} else {
			updatePosition(0, scroller.y + (amount*5));
		}
	}

	public void updateBounds(float width, float height) {
		int difference = 100;
		setVisible(false);
		if (horizontal) {
			if (width > size.x) {
				difference = (int) ((size.x / width) * 100);
				setVisible(true);
			}
		} else {
			if (height > size.y) {
				difference = (int) ((size.y / height) * 100);
				setVisible(true); // This is not working...
			}
		}

		updatePercentage(difference);
	}

	public void updatePosition(float x, float y) {
		if (horizontal) {
			float dir = scroller.x - x;
			scroller.x = x;

			if (dir > 0) {
				if (scroller.x < 0) {
					scroller.x = 0;
				}
			} else {
				if (scroller.x > size.x - scroller.width) {
					scroller.x = (size.x - scroller.width);
				}
			}
		} else {
			float dir = scroller.y - y;
			scroller.y = y;
			if (dir > 0) { //scroll up
				if (scroller.y < 0) {
					scroller.y = 0;
				}
			} else { //scroll down
				if (scroller.y > size.y - scroller.height) {
					scroller.y = (size.y - scroller.height);
				}
			}
		}
	}

	public void updatePercentage(int percentage) {
		if (horizontal) {
			//TODO: Horizontal
			scroller.width = (int) (size.x * (percentage / 100f));
		} else {
			scroller.height = (int) (size.y * (percentage / 100f));
			float newPos = (size.y * (percentage / 100f));

			if (newPos < scroller.height)
				newPos = scroller.height;

			scroller.y = (int) (newPos - scroller.height);
		}
	}

	@Override
	public boolean intersects(Point2F mousePosition) {
		return getHitbox().intersects(mousePosition);
	}

	@Override
	public void render(SpriteBatch batch) {
		if (!visible)
			return;

		if (horizontal) {
			scrollPercentage = scroller.x / (size.x - scroller.width);
		} else {
			scrollPercentage = scroller.y / (size.y - scroller.height);
		}
		if (Float.isNaN(scrollPercentage))
			scrollPercentage = 0f;

		sprite.render(batch, pos.x, pos.y, size.x, size.y);
		scrollerGraphic.render(batch, scroller.x, pos.y - (scroller.y - size.y + scroller.height), scroller.width, scroller.height);

		// Render all the subcomponents
		for (GUIComponent child : subComponents) {
			child.render(batch);
		}
	}
}
