package com.teamsweepy.greywater.ui.gui.temp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.math.Point2F;

/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

public class Label {
    public float x, y, w, h;
    private NinePatch patch;
    private BitmapFontCache cache;
    private BitmapFont font;

    private Scrollbar scrollbarX;
    private Scrollbar scrollbarY;

    public Label(float x, float y, float w, float h) {
        // Use the default font
        this("font/times.fnt", new Texture("ui/menu.png"), x, y, w, h);
    }

    public Label(String fontPath, Texture ninePatch, float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        patch = new NinePatch(ninePatch, 8, 8, 8, 8);
        font = new BitmapFont(Gdx.files.internal(fontPath));
        cache = new BitmapFontCache(font);
        cache.setUseIntegerPositions(false);

        scrollbarY = new Scrollbar(false, false, x+w, y, 20, h);

        setText(Gdx.files.internal("data/dialog_text.txt").readString());
    }


    public void setText(String text) {
        cache.setWrappedText(text, 0, h, w);

        BitmapFont.TextBounds bounds = cache.getBounds();
        if(bounds.height > h) {
            int difference = (int)((h / bounds.height) * 100);
            scrollbarY.updatePercentage(difference);
            scrollbarY.visible = true;
        }
    }

    public void setListener(InputMultiplexer inputs) {
        inputs.addProcessor(scrollbarY.inputHandler);
    }


    public void draw(SpriteBatch batch) {
        float newX = x - Camera.getDefault().xOffsetAggregate;
        float newY = y - Camera.getDefault().yOffsetAggregate;

        patch.draw(batch, newX, newY, w, h);

        float yOffset = scrollbarY.scrollPercentage * (h - cache.getBounds().height);

        batch.end();
        // Disable so whe can cut of the bitmapFont
        // This way whe can use scrollbars
//        Gdx.gl.glEnable(GL10.GL_SCISSOR_TEST);
//        Gdx.gl.glScissor((int) (x), (int) (y), (int) (w), (int) (h));
        batch.begin();

        cache.setPosition(
                newX,
                yOffset+newY
        );

        cache.draw(batch);

        batch.flush();
        Gdx.gl.glDisable(GL10.GL_SCISSOR_TEST);

        if(scrollbarX != null) scrollbarX.draw(batch);
        if(scrollbarY != null) scrollbarY.draw(batch);
    }
}
