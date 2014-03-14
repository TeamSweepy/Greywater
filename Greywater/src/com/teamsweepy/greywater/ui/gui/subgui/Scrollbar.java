package com.teamsweepy.greywater.ui.gui.subgui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entities.components.Hitbox;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.temp.InputListener;

import java.awt.*;

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

        scroller = new Rectangle((int) x, (int) y, (int) w, (int) h);

        this.horizontal = horizontal;
        visible = true;
//
//        inputHandler = new InputListener() {
//
//            @Override
//            public boolean scrolled(int amount) {
//                wheelScroll(amount);
//                return true;
//            }
//        };
    }

    @Override
    public void handleInput(Point2F mousePosition, int event) {
        super.handleInput(mousePosition, event);
    }

    @Override
    public boolean intersects(Point2F mousePosition) {
        System.out.println("Intersecting with the scrollbar");
        return getHitbox().intersects(mousePosition);
    }

    public void updateBounds(float width, float height) {
        int difference;
        if(horizontal) {
            difference = (int)((size.x / width) * 100);
        } else {
            difference = (int)((size.y / height) * 100);
        }

        updatePercentage(difference);

        System.out.println("Difference: "+difference);
    }

    public void updatePosition(int x, int y) {
        if (horizontal) {
            int dir = scroller.x - x;
            scroller.x = x;

            if (dir > 0) {
                if (scroller.x < 0) {
                    scroller.x = 0;
                }
            } else {
                if (scroller.x > size.x - scroller.width) {
                    scroller.x = (int) (size.x - scroller.width);
                }
            }
        } else {
            int dir = scroller.y - y;
            scroller.y = y;
            if (dir > 0) {
                if (scroller.y < 0) {
                    scroller.y = 0;
                }
            } else {
                if (scroller.y > size.y - scroller.height) {
                    scroller.y = (int) (size.y - scroller.height);
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
            scrollPercentage = scroller.y / (size.x - scroller.height);
        }

        sprite.render(batch, currentX, currentY, size.x, size.y);
        scrollerGraphic.render(
                batch,
                scroller.x - offsetX,
                pos.y - (scroller.y - size.y + scroller.height) - offsetY,
                size.x,
                size.y
        );

        // Render all the subcomponents
        for (SubGUIComponent child : subComponents) {
            child.render(batch);
        }
    }
}
