
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * SubGUIComponent used for
 * */
public class ProgressBarCircular extends SubGUIComponent {

	protected float minRotation;
	protected float maxRotation;

	protected float minValue;
	protected float maxValue;

	protected float value;
	protected float percentage;
	protected float rotation;


	public ProgressBarCircular(float x, float y) {
		sprite = new Sprite("health-dial-rotate0001");
		pos = new Point2F(x, y);
		visible = true;
	}

	@Override
	public boolean intersects(Point2F mousePosition) {
		return false;
	}

	@Override
	public void tick(float deltaTime) {
		if (maxValue == 0) {
			System.out.println("[ERROR] ProgressBar division MAX VALUE IS 0");
			return;
		}
		
		// TODO: smooth rotation after big value changes
		
		percentage = (float) value / maxValue;
		float dRotation = maxRotation - minRotation;
		rotation = minRotation +  dRotation * percentage;

		value++;
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.renderRotated(batch, pos.x, pos.y, rotation);
	}


	public void setMinRotation(float minRotation) {
		this.minRotation = minRotation;
	}

	public void setMaxRotation(float maxRotation) {
		this.maxRotation = maxRotation;
	}

	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	public void setValue(int value) {
		this.value = value;
	}


}
