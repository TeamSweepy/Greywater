/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.data.TextStyle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// import net.biodiscus.debug.Debug;


public class Dialog extends SubGUIComponent {

	private TextStyle style;
	private Text text;
	private Text titleText;
	private Scrollbar scrollBar;
	private Button closeButton;
	private int titleOffset = 90; //default size of title area

	public Dialog() {
		this(0, 0, 100, 100);
	}

	public Dialog(float x, float y, float w, float h) {
		super(x, y, w, h);

		sprite = new Sprite("ui/menu", null, Texture.class);
		visible = true;

		style = new TextStyle("data/font/times.fnt", 0xFF0000FF, TextStyle.WordStyle.WRAPPING);

		text = new Text(x + 4, y + 4, w - 20, h - titleOffset);
		text.setStyle(style);
		subComponents.add(text);
		
		titleText = new Text(x, y, w - 20 , h);
		titleText.setStyle(style);
		titleText.setText("");
		titleText.centerOnPosition(w/2,  0);
		subComponents.add(titleText);

		scrollBar = new Scrollbar(false, x + w - 20, y, 20, h- titleOffset);
		subComponents.add(scrollBar);

		setText(Gdx.files.internal("data/dialog_text.txt").readString());

		final Dialog dialog = this; // Used for the button

		closeButton = new Button(x + w - 20, y + h - 15, "ui/cross") {

			@Override
			protected void clicked() {
				dialog.visible = false;
			}
		};
		subComponents.add(closeButton);
	}

	public void appendText(String text) {
		this.text.appendText(text);
	}

	public void setText(String text) {
		this.text.setText(text);

		float width = this.text.getBounds().width;
		float height = this.text.getBounds().height;
		scrollBar.updateBounds(width, height);
	}
	
	public void setTitle(String title){
		this.titleText.setText(title);
		titleText.centerOnPosition(size.x/2,  0);
	}
	@Override
	public void handleInput(Point2F mousePosition, int amount, int event) {
		scrollBar.scroll(amount * 5);

		super.handleInput(mousePosition, amount, event);
	}

	@Override
	public void handleInput(Point2F mousePosition, int event) {

		super.handleInput(mousePosition, event);
	}

	@Override
	public boolean intersects(Point2F mousePosition) {
		if (visible) {
			return getHitbox().intersects(mousePosition);
		} else {
			return false;
		}
	}

	// Override it so we can use a glScissor
	@Override
	public void render(SpriteBatch batch) {
		if (!visible)
			return;

		float currentX = pos.x - Camera.getDefault().xOffsetAggregate;
		float currentY = pos.y - Camera.getDefault().yOffsetAggregate;

		float yOffset = scrollBar.scrollPercentage * (text.getBounds().height - (size.y - titleOffset));
		text.setTextPosition(0, yOffset);

		sprite.render(batch, currentX, currentY, size.x, size.y);

		// Render all the subcomponents
		for (SubGUIComponent child : subComponents) {
			child.render(batch);
		}
	}
}
