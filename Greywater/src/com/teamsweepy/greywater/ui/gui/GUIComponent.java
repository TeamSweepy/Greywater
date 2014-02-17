
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.input.InputGUI;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GUIComponent {

	public static float ratio = 1; // Gets calculated in HUD (since HUD extends to the full width)

	protected Point2F pos;
	protected Point2F size;
	protected Hitbox hitbox;

	public Sprite sprite;

	public GUIComponent() {
		sprite = new Sprite("HUD-1600");
		pos = new Point2F(0, 0);
		size = new Point2F(Gdx.graphics.getWidth(), sprite.getImageHeight());
		size = new Point2F(1600, sprite.getImageHeight());

		hitbox = new Hitbox((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, 0f);
	}

	public void input(Point2F mousePosition, int event) {
		if (event == InputGUI.MOUSE_MOVED)
			return;

		System.out.println("GOT THE INPUT " + event);
	}

	public void tick() {

	}

	public void render(SpriteBatch batch) {
		sprite.render(batch, -Camera.getDefault().xOffsetAggregate, -Camera.getDefault().yOffsetAggregate, size.getX(), size.getY());
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public boolean intersects(Point2F mousePosition) {
		mousePosition = mousePosition.div(ratio);// scale the input to the game size
		return hitbox.intersects(mousePosition);
	}
}
