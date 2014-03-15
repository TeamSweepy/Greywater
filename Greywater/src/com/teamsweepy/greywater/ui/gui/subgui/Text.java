package com.teamsweepy.greywater.ui.gui.subgui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.subgui.bbcode.BBCodeNode;
import com.teamsweepy.greywater.ui.gui.subgui.bbcode.BBCodeParser;
import com.teamsweepy.greywater.ui.gui.subgui.data.TextStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Point2F textPosition = new Point2F();

    // Used for the scissor
    public float x, y, w, h;

    // TODO: Add an background
    // TODO: Singleline!
    // TODO: Automatic resize
    public Text() {
        this(0, 0, 100, 100);
    }

    public Text(float x, float y, float w, float h) {
        super(x, y, w, h);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

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

        BBCodeNode[] nodes = BBCodeParser.parse(text);
        String newText = BBCodeParser.replaceText(text, nodes);

        if(style.wordStyle == TextStyle.WordStyle.MULTILINE) {
            cache.setMultiLineText(newText, 0, 0);
        } else if(style.wordStyle == TextStyle.WordStyle.WRAPPING) { // In case whe want more word styles
            cache.setWrappedText(newText, 0, 0, size.x);
        }

        // If there is no color tag, use the default given color
        cache.setColor(style.color);

        for(BBCodeNode node : nodes) {
            if(node.start.node.toLowerCase().equals("color")) {
                long hex = Long.decode(node.start.innerValue);
                cache.setColor(colorFromLong(hex), node.startPos, node.endPos);
            }
        }
        bounds = cache.getBounds();
    }

    // AARRGGBB are to long for integers...
    private Color colorFromLong(long hex) {
        float r = (hex & 0xFF000000L) >> 24;
        float g = (hex & 0xFF0000L) >> 16;
        float b = (hex & 0xFF00L) >> 8;
        float a = hex & 0xFFL;

        return new Color(r/255F, g/255F, b/255F, a/255F);
    }

    public void setStyle(TextStyle style) {
        this.style = style;

        cache = new BitmapFontCache(style.font);
    }

    public void setTextPosition(Point2F point) {
        textPosition = point;
    }

    public void setTextPosition(float x, float y) {
        setTextPosition(new Point2F(x, y));
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

        batch.end();
        Gdx.gl.glEnable(GL10.GL_SCISSOR_TEST);
        Gdx.gl.glScissor((int) (pos.x), (int) (pos.y), (int) (size.x), (int) (size.y));
        batch.begin();

        cache.setPosition(textPosition.x + offsetX, textPosition.y + offsetY + size.y);
        cache.draw(batch);

        batch.flush(); // Save the data
        Gdx.gl.glDisable(GL10.GL_SCISSOR_TEST);
    }
}
