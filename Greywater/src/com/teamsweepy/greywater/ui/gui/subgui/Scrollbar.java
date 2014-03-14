package com.teamsweepy.greywater.ui.gui.subgui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.input.InputHandler;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.temp.InputListener;

/**
 * Created with IntelliJ IDEA.
 * User: Robin de Jong
 * Date: 6:38 PM, 3/14/14
 */
public class Scrollbar extends SubGUIComponent {
    private boolean horizontal;
    public Rectangle scroller;
    private Sprite scrollerGraphic;
    public float scrollPercentage = 0f;

    public Scrollbar(boolean horizontal, float x, float y, float w, float h) {
        super(x, y, w, h);

        sprite = new Sprite("ui/scroller", null, Texture.class);
        scrollerGraphic = new Sprite("ui/scroller_button", null, Texture.class);

        scroller = new Rectangle(x, y, w, h);

        this.horizontal = horizontal;
        visible = true;
    }

    @Override
    public void handleInput(Point2F mousePosition, int event)
    {
        if(event == InputHandler.MOUSE_DRAGGED || event == InputHandler.MOUSE_DOWN) {
            float offsetY = (pos.y + size.y) - mousePosition.y;
            updatePosition(0, offsetY);
        }

        super.handleInput(mousePosition, event);
    }

    public void scroll(int amount) {
        wheelScroll(amount);
    }

    public void wheelScroll(int amount) {
        if (horizontal) {
            updatePosition(scroller.x + (amount * 5), 0);
        } else {
            updatePosition(0, scroller.y + (amount * 5));
        }
    }

    public void updateBounds(float width, float height) {
        int difference;
        if(horizontal) {
            difference = (int)((size.x / width) * 100);
        } else {
            difference = (int)((size.y / height) * 100);
        }

        updatePercentage(difference);
    }

    public void updatePosition(float x, float y) {
        if (horizontal) {
            float dir = scroller.x - x;
            scroller.x = x;

            if (dir > 0) {
                if (scroller.x < 0) {
                    scroller.x = 0;
                }
            } else {
                if (scroller.x > size.x - scroller.width) {
                    scroller.x = (size.x - scroller.width);
                }
            }
        } else {
            float dir = scroller.y - y;
            scroller.y = y;
            if (dir > 0) {
                if (scroller.y < 0) {
                    scroller.y = 0;
                }
            } else {
                if (scroller.y > size.y - scroller.height) {
                    scroller.y = (size.y - scroller.height);
                }
            }
        }
    }

    public void updatePercentage(int percentage) {
        if (horizontal) {
            //TODO: Horizontal
            scroller.width = (int) (size.x * (percentage / 100f));
        } else {
            scroller.height = (int) (size.y * (percentage / 100f));
            float newPos = (size.y * (percentage / 100f));

            if (newPos < scroller.height)
                newPos = scroller.height;

            scroller.y = (int) (newPos - scroller.height);
        }
    }

    @Override
    public boolean intersects(Point2F mousePosition) {
        return getHitbox().intersects(mousePosition);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!visible)
            return;

        float offsetX = Camera.getDefault().xOffsetAggregate;
        float offsetY = Camera.getDefault().yOffsetAggregate;
        float currentX = pos.x - offsetX;
        float currentY = pos.y - offsetY;

        if (horizontal) {
            scrollPercentage = scroller.x / (size.x - scroller.width);
        } else {
            scrollPercentage = scroller.y / (size.y - scroller.height);
        }

        sprite.render(batch, currentX, currentY, size.x, size.y);
        scrollerGraphic.render(
                batch,
                scroller.x - offsetX,
                //y - (scroller.y - h + (scroller.height))
                pos.y - (scroller.y - size.y + scroller.height) - offsetY,
                scroller.width,
                scroller.height
        );

        // Render all the subcomponents
        for (SubGUIComponent child : subComponents) {
            child.render(batch);
        }
    }
}
