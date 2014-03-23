
package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.GUIComponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * SubGUIComponent used for
 * */
public class ProgressBarCircular extends GUIComponent {

	protected float minRotation;
	protected float maxRotation;

	protected float minValue;
	protected float maxValue;

	protected float value;
	protected float percentage;
	protected float rotation;
	float arcofRotation;


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
		float dR = (percentage*arcofRotation - rotation) * .08f;
		rotation = rotation + dR;


	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.renderRotated(batch, pos.x, pos.y, rotation + minRotation);
	}


	public void setMinRotation(float minRotation) {
		this.minRotation = minRotation;
		arcofRotation = maxRotation - minRotation;
	}

	public void setMaxRotation(float maxRotation) {
		this.maxRotation = maxRotation;
		arcofRotation = maxRotation - minRotation;
	}

	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	public void setValue(float value) {
		if (value < minValue)
			value = minValue;
		else if (value > maxValue)
			value = maxValue;
		else
			this.value = value;
		percentage = (float) value / maxValue;
	}


}
