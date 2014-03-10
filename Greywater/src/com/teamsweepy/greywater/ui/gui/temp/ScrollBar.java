package com.teamsweepy.greywater.ui.gui.temp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.input.InputHandler;

import java.awt.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: Robin de Jong
 * Date: 5:37 PM, 3/9/14
 */
public class ScrollBar{

    public float x, y, w, h;

    public boolean visible;

    private boolean horizontal;

    public Rectangle scroller;

    public InputProcessor inputHandler;

    public float scrollPercentage = 0f;

    private NinePatch background, scrollerBackground;

    public ScrollBar(boolean horizontal, float x, float y, float w, float h) {
        this(horizontal, true, x, y, w, h);
    }

    public ScrollBar(boolean visible, boolean horizontal, float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.visible = visible;
        this.horizontal = horizontal;

        scroller = new Rectangle((int)x, (int)y, (int)w, (int)h);

        background = new NinePatch(new Texture("ui/scroller.png"), 8, 8, 8, 8);
        scrollerBackground = new NinePatch(new Texture("ui/scroller_button.png"), 8, 8, 8, 8);

        inputHandler = new InputListener()
        {
            @Override
            public boolean scrolled(int amount)
            {
                wheelScroll(amount);
                return true;
            }
        };
    }

    public void wheelScroll(int amount) {
        if(horizontal) {
            updatePosition(scroller.x + (amount * 5), 0);
        } else  {
            updatePosition(0, scroller.y + -(amount * 5));
        }
    }

    public void updatePosition(int x, int y) {
        if(horizontal) {
            int dir = scroller.x - x;
            scroller.x = x;

            if(dir > 0) {
                if(scroller.x < 0) {
                    scroller.x = 0;
                }
            } else {
                if(scroller.x > w - scroller.width) {
                    scroller.x = (int)(w - scroller.width);
                }
            }
        } else {
            int dir = scroller.y - y;
            scroller.y = y;
//            System.out.println(scroller.y+", "+);
            if(dir > 0) {
//                if(scroller.y < (this.y - (h+scroller.height/2))) {
//                    scroller.y = (int)(this.y - (h+scroller.height/2));
//                }
//                {
//                    scroller.y = (int)(this.y + (h - scroller.height));
//                }
//                if(scroller.y > scroller.height) {
//                    scroller.y = scroller.height;
//                }

//                if(scroller.y < 0) {
//                    scroller.y = 0;
//                }
            } else {
//                if(scroller.y > h - scroller.height) {
//                    scroller.y = (int)(h - scroller.height);
//                }
//                if(scroller.y > 0) {
//                    scroller.y = 0;
//                }
//                if(scroller.y > (this.y + (h - scroller.height)))
//                {
//                    scroller.y = (int)(this.y + (h - scroller.height));
//                }
            }
        }
    }

    public void updatePercentage(int percentage) {
        if(horizontal) {
            scroller.width = (int)(w * (percentage/100f));
        } else {
            scroller.height = (int)(h * (percentage/100f));
            float newPos = (h * (percentage/100f));

            if(newPos < scroller.height) newPos = scroller.height;

            scroller.y = (int)(newPos - scroller.height);
        }
    }

    public void draw(SpriteBatch batch) {
        if(visible) {
            if(horizontal) {
                scrollPercentage = scroller.x / (x - scroller.width);
            } else {
                scrollPercentage = scroller.y / (h - scroller.height);
            }

            float newX = x - Camera.getDefault().xOffsetAggregate;
            float newY = y - Camera.getDefault().yOffsetAggregate;

            background.draw(batch, newX, newY, w, h);

            scrollerBackground.draw
                    (
                            batch,
                            scroller.x - Camera.getDefault().xOffsetAggregate,
//                            (scroller.height/2) + scroller.y - Camera.getDefault().yOffsetAggregate,
                            scroller.y - Camera.getDefault().yOffsetAggregate,
                            scroller.width,
                            scroller.height
                    );
        }
    }

}
