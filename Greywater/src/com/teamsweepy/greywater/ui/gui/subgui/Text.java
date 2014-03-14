package com.teamsweepy.greywater.ui.gui.subgui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.data.TextStyle;

/**
 * The text class will use a BitmapFontCache, no sprite is needed. This is why the Text class will have his own renderer
 *
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */
public class Text extends SubGUIComponent {
    private String currentText; // Is a stringbuilder faster?
    private TextStyle style;
    private BitmapFontCache cache;
    private BitmapFont.TextBounds bounds;

    // TODO: Add an background
    // TODO: Singleline!
    // TODO: Automatic resize
    public Text() {
        this(0, 0, 100, 100);
    }

    public Text(float x, float y, float w, float h) {
        super(x, y, w, h);
        visible = true;
    }

    public BitmapFont.TextBounds getBounds() {
        return bounds;
    }

    public String getText() {
        return currentText;
    }

    public void appendText(String text) {
        setText(currentText+text);
    }

    public void setText(String text) {
        if(style == null) return;

        currentText = text;

        if(style.wordStyle == TextStyle.WordStyle.MULTILINE) {
            cache.setMultiLineText(currentText, 0, 0);
        } else if(style.wordStyle == TextStyle.WordStyle.WRAPPING) { // In case whe want more word styles
            cache.setWrappedText(currentText, 0, 0, size.x);
        }

        cache.setColor(style.color);

        bounds = cache.getBounds();
    }

    public void setStyle(TextStyle style) {
        this.style = style;

        cache = new BitmapFontCache(style.font);
    }

    @Override
    public boolean intersects(Point2F mousePosition) {
        return getHitbox().intersects(mousePosition);
    }

    @Override
    public void render(SpriteBatch batch) {
        if(cache == null) return;

        float offsetX = pos.x - Camera.getDefault().xOffsetAggregate;
        float offsetY = pos.y - Camera.getDefault().yOffsetAggregate;

        cache.setPosition(offsetX, offsetY);
        cache.draw(batch);
    }
}
