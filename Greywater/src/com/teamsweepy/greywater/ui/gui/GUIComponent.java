
package com.teamsweepy.greywater.ui.gui;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GUIComponent {

	protected Point2F pos;
	protected Point2F size;
	protected Hitbox hitbox;

	public Sprite sprite;

	protected ArrayList<GUIComponent> subComponents = new ArrayList<GUIComponent>();

	public GUIComponent() {
		sprite = new Sprite("HUD-1600");
		pos = new Point2F(0, 0);
		size = new Point2F(Gdx.graphics.getWidth(), sprite.getImageHeight());
		size = new Point2F(1600, sprite.getImageHeight());

		hitbox = new Hitbox((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, 0f);
	}

	public void handleInput(Point2F mousePosition, int event) {
		for (GUIComponent child : subComponents) {
			if (child.intersects(mousePosition))
				child.handleInput(mousePosition, event);
		}

		//System.out.println("GOT THE INPUT " + event);
	}

	public void tick() {}

	public void render(SpriteBatch batch) {
		sprite.render(batch, -Camera.getDefault().xOffsetAggregate, -Camera.getDefault().yOffsetAggregate);
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public boolean intersects(Point2F mousePosition) {
		for (GUIComponent child : subComponents) {
			if (child.intersects(mousePosition))
				return true;
		}
		return false;
	}
}
