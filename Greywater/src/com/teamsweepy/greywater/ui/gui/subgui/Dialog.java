package com.teamsweepy.greywater.ui.gui.subgui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.GUIComponent;
import com.teamsweepy.greywater.ui.gui.subgui.data.TextStyle;
import net.biodiscus.debug.Debug;

/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */
public class Dialog extends SubGUIComponent {
    private TextStyle style;
    private Text text;
    private Scrollbar scrollBar;

    public Dialog() {
        this(0, 0, 100, 100);
    }

    public Dialog(float x, float y, float w, float h) {
        super(x, y, w, h);

        sprite = new Sprite("ui/menu", null, Texture.class);
        visible = true;

        style = new TextStyle("font/times.fnt", 0xFF0000FF, TextStyle.WordStyle.WRAPPING);

        text = new Text(x, y + h, w, h);
        text.setStyle(style);
        subComponents.add(text);

        scrollBar = new Scrollbar(false, x + w - 20, y, 20, h);
        subComponents.add(scrollBar);

        setText(Gdx.files.internal("data/dialog_text.txt").readString());
    }

    public void appendText(String text) {
        this.text.appendText(text);
    }

    public void setText(String text) {
        this.text.setText(text);

        float width = this.text.getBounds().width;
        float height = this.text.getBounds().height;

        if (height > size.y) {
            scrollBar.updateBounds(width, height);
        }
    }

    @Override
    public void handleInput(Point2F mousePosition, int event) {
//        for (GUIComponent guiC : subComponents) {
//            if (guiC.intersects(mousePosition)) {
//                guiC.handleInput(mousePosition, event);
//            }
//        }

        super.handleInput(mousePosition, event);
    }

    @Override
    public boolean intersects(Point2F mousePosition) {
        System.out.println("Intersecting with the dialog");
        return getHitbox().intersects(mousePosition);
    }

    // Override it so we can use a glScissor
    @Override
    public void render(SpriteBatch batch) {
        if (!visible)
            return;

        float currentX = pos.x - Camera.getDefault().xOffsetAggregate;
        float currentY = pos.y - Camera.getDefault().yOffsetAggregate;

        batch.end();
        Gdx.gl.glEnable(GL10.GL_SCISSOR_TEST);
        Gdx.gl.glScissor((int) (pos.x), (int) (pos.y), (int) (size.x), (int) (size.y));
        batch.begin();

        sprite.render(batch, currentX, currentY, size.x, size.y);

        // Render all the subcomponents
        for (SubGUIComponent child : subComponents) {
            child.render(batch);
        }

        batch.flush(); // Save the data
        Gdx.gl.glDisable(GL10.GL_SCISSOR_TEST);
    }
}
