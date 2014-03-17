/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */
package com.teamsweepy.greywater.ui.gui.subgui.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class TextStyle {
    public enum WordStyle
    {
        WRAPPING, MULTILINE
    }

    public BitmapFont font;
    public Color color;
    public WordStyle wordStyle;

    public TextStyle(String fontName, int color, WordStyle wordStyle) {
        font = loadFont(fontName);
        this.color = new Color(color);
        this.wordStyle = wordStyle;
    }

    public TextStyle(BitmapFont font, int color, WordStyle wordStyle) {
        this.font = font;
        this.color = new Color(color);
        this.wordStyle = wordStyle;
    }

    private BitmapFont loadFont(String fontName) {
        return new BitmapFont(Gdx.files.internal(fontName));
    }
}