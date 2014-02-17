/**
 * Abstract class for all other Input classes.
 * 
 * The methods are static, since there will only be 1 instance of every input
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Ziga
 */

package com.teamsweepy.greywater.engine.input;

import com.teamsweepy.greywater.math.Point2F;

import java.awt.Point;

import com.badlogic.gdx.InputProcessor;

public abstract class InputHandler implements InputProcessor {

	/* ********************* MOUSE INPUT CONSTANTS ************************ */
	public static final int MOUSE_DOWN = 0;
	public static final int MOUSE_UP = 1;
	public static final int MOUSE_MOVED = 2;


	/* ********************* MOUSE INPUT VARIABLES ************************ */
	protected static Point2F mousePosition;
	protected static boolean isDown;

	/**
	 * Returns if the mouse is down
	 */
	public static boolean isDown() {
		return isDown;
	}

	/**
	 * Returns the mouse position as a Point
	 * 
	 * @return mousepostion
	 */
	public static Point2F getMousePosition() {
		return mousePosition;
	}

	/**
	 * Returns the x of the mouse position
	 * 
	 * @return mousepostion.x
	 */
	public static int getX() {
		return (int) mousePosition.x;
	}

	/**
	 * Returns if the mouse is down
	 * 
	 * @return mousepostion.y
	 */
	public static int getY() {
		return (int) mousePosition.y;
	}

	// ******** Abstract methods ******** //
	@Override
	/**
	 * Indicates when a key is pressed.
	 * @see com.badlogic.gdx.Input.Keys
	 */
	public abstract boolean keyDown(int keycode);

	@Override
	/**
	 * Indicates when a key is released.
	 * @see com.badlogic.gdx.Input.Keys
	 */
	public abstract boolean keyUp(int keycode);

	@Override
	/**
	 * Indicates when a unicode character is produced.
	 */
	public abstract boolean keyTyped(char character);

	@Override
	/**
	 * Reports when a touchscreen is pressed or when a mousebutton is pressed.
	 * LibGDX treats touchscreens and mice as the same device, though touches 
	 * only have one button (left)
	 * @see com.badlogic.gdx.Input.Buttons
	 */
	public abstract boolean touchDown(int screenX, int screenY, int pointer, int button);

	@Override
	/**
	 * Reports when a touchscreen is released or when a mousebutton is released.
	 * LibGDX treats touchscreens and mice as the same device, though touches 
	 * only have one button (left)
	 * @see com.badlogic.gdx.Input.Buttons
	 */
	public abstract boolean touchUp(int screenX, int screenY, int pointer, int button);

	@Override
	/**
	 * Reports mouse movement with no buttons pressed. 
	 * Will never be called natively by touchscreen devices 
	 * (they can't track fingers not-on-screen)
	 */
	public abstract boolean mouseMoved(int screenX, int screenY);

	// ******** End of methods ******** //

	// ******** Will not be used ******** //

	@Override
	/**
	 * Reports when a touchscreen is dragged or when a mousebutton is click-and-dragged.
	 * LibGDX treats touchscreens and mice as the same device, though touches 
	 * only have one button (left)
	 */
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	/**
	 * Reports mouse scroll wheel as either -1 or 1 for direction.
	 * Will never be called natively by touchscreen devices,
	 * they have no concept of scrollwheels
	 */
	public boolean scrolled(int amount) {
		return false;
	}

}
