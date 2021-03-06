/**
 * 
 */

package com.teamsweepy.greywater.ui.gui.subgui;

import com.teamsweepy.greywater.entity.component.Hitbox;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.GUIComponent;
import com.teamsweepy.greywater.ui.gui.subgui.data.TextStyle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Button extends GUIComponent {

	private Text text;
	
	public Button() {
		super(0, 0, 100, 100);
	}

	public Button(float x, float y, float w, float h) {
		super(x, y, w, h);
	}

	/** Constructor for visible buttons using texture atlases! */
	public Button(float x, float y, String atlasName, String imageName) {
		this.sprite = new Sprite(atlasName, imageName, true);
		visible = true;
		pos = new Point2F(x, y);
		size = new Point2F(sprite.getImageWidth(), sprite.getImageHeight());
		hitbox = new Hitbox(x, y, (int) sprite.getImageWidth(), (int) sprite.getImageHeight(), 0f, true);
	}

	/** Constructor for visible buttons using textures! */
	public Button(float x, float y, String imageName) {
		this.sprite = new Sprite(imageName, true);
		visible = true;
		pos = new Point2F(x, y);
		size = new Point2F(sprite.getImageWidth(), sprite.getImageHeight());
		hitbox = new Hitbox(x, y, (int) sprite.getImageWidth(), (int) sprite.getImageHeight(), 0f, true);
	}

	public Button(float x, float y, float maxW, float maxH, String txt) {
		super(x, y, maxW, maxH, true);
		visible = true;
		TextStyle style = new TextStyle("data/font/times.fnt", 0xD6B36EFF, TextStyle.WordStyle.MULTILINE);

		text = new Text(pos.x, pos.y, size.x, size.y);
		text.setStyle(style);
		text.setText(txt);
		text.centerTextOnPosition(size.x/2, 0);
		text.setVisible(true);
		//subComponents.add(text);

	}


	/** The method should be overridden when creating the button */
	protected void clicked(boolean rightClick) {
		System.out.println("Button clicked");
	}

	public void centerImage(float x, float y) {
		float xLoc = x - size.x / 2;
		float yLoc = y - size.y / 2;
		pos = new Point2F(xLoc, yLoc);
		hitbox.setLocation(xLoc, yLoc);

	}

	public boolean intersects(Point2F mousePosition) {
		if (sprite != null) { //animate buttons with sprites
			if (getHitbox().intersects(mousePosition)) {
				sprite.changeSeriesPosition(100, -1);
			} else {
				sprite.changeSeriesPosition(-100, -1);
			}
		}
		return getHitbox().intersects(mousePosition);
	}

	@Override
	public void render(SpriteBatch batch) {
		if (sprite != null)
			sprite.render(batch, pos.x, pos.y, size.x, size.y);
		if (text != null) {
			text.render(batch);
		}
		for (GUIComponent GuiC : subComponents) {
			GuiC.render(batch);
		}
	}

	@Override
	public void tick(float deltaTime) {
		if (sprite != null)
			sprite.tick(deltaTime);
		for (GUIComponent GuiC : subComponents) {
			GuiC.tick(deltaTime);
		}
	}
}
