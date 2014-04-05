/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.GUIComponent;
import com.teamsweepy.greywater.ui.gui.subgui.data.TextStyle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// import net.biodiscus.debug.Debug;


public class Dialog extends GUIComponent {

	private TextStyle style;
	private Text text;
	private Text titleText;
	private Scrollbar scrollBar;
	private Button closeButton;
	private int titleOffset = 90; //default size of title area
	
	public Dialog(float x, float y, float w, float h, boolean centered) {
		super(x, y, w, h, centered);

		sprite = new Sprite("ui/menu", null, Texture.class);
		visible = true;

		style = new TextStyle("data/font/times.fnt", 0xD6B36EFF, TextStyle.WordStyle.WRAPPING);

		text = new Text(pos.x + 4, pos.y + 4, w - 35, h - titleOffset);
		text.setStyle(style);
		subComponents.add(text);
		
		titleText = new Text(pos.x, pos.y, w - 20 , h);
		titleText.setStyle(style);
		titleText.setText("");
		titleText.centerTextOnPosition(w/2,  0);
		subComponents.add(titleText);

		scrollBar = new Scrollbar(false, pos.x + w - 24, pos.y + 3, 20, h- titleOffset);
		subComponents.add(scrollBar);
		setText("");
		final Dialog dialog = this; // Used for the button

		closeButton = new Button(pos.x + w - 25, pos.y + h - 25, "ui/cross") {
			@Override
			protected void clicked(boolean rightClick) {
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
		titleText.centerTextOnPosition(size.x/2,  0);
	}
	@Override
	public void handleInput(Point2F mousePosition, int amount, int event) {
		scrollBar.scroll(amount * 5);

		super.handleInput(mousePosition, amount, event);
	}

	// Override it so we can use a glScissor
	@Override
	public void render(SpriteBatch batch) {
		if (!visible)
			return;

		float yOffset = scrollBar.scrollPercentage * (text.getBounds().height - (size.y - titleOffset));
		text.setTextPosition(0, yOffset);

		sprite.render(batch, pos.x,  pos.y, size.x, size.y);

		// Render all the subcomponents
		for (GUIComponent child : subComponents) {
			child.render(batch);
		}
	}
	
}
