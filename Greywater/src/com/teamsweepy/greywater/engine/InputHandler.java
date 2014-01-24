/**
 * Handles all keyboard/mouse/touchscreen input.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.engine;

import com.badlogic.gdx.InputProcessor;


public class InputHandler implements InputProcessor {

	@Override
	/**
	 * Indicates when a key is pressed.
	 * @see com.badlogic.gdx.Input.Keys
	 */
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	/**
	 * Indicates when a key is released.
	 * @see com.badlogic.gdx.Input.Keys
	 */
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	/**
	 * Indicates when a unicode character is produced.
	 */
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	/**
	 * Reports when a touchscreen is pressed or when a mousebutton is pressed.
	 * LibGDX treats touchscreens and mice as the same device, though touches 
	 * only have one button (left)
	 * @see com.badlogic.gdx.Input.Buttons
	 */
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	/**
	 * Reports when a touchscreen is released or when a mousebutton is released.
	 * LibGDX treats touchscreens and mice as the same device, though touches 
	 * only have one button (left)
	 * @see com.badlogic.gdx.Input.Buttons
	 */
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

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
	 * Reports mouse movement with no buttons pressed. 
	 * Will never be called natively by touchscreen devices 
	 * (they can't track fingers not-on-screen)
	 */
	public boolean mouseMoved(int screenX, int screenY) {
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
