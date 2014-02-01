/**
 * Input handler for Gane view. It is the last one that accepts the events. If others did not absorb the event, it gets passed to this listener.
 * All methods return true, since it will always take the input.
 * 
 * The methods are static, since there will only be 1 instance of every input
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Ziga
 */

package com.teamsweepy.greywater.engine.input;

import java.awt.Point;

import com.badlogic.gdx.Input.Keys;

public class InputGame extends InputHandler {

	@Override
	/**
	 * Upon a mouse / pointer click this event occurs. It saves the mouse input data
	 * */
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		isDown = true;
		mousePosition = new Point(screenX, screenY);

		return true;
	}

	@Override
	/**
	 * Upon a mouse / pointer unclick (release) this event occurs. It saves the mouse input data
	 * */
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isDown = false;
		mousePosition = new Point(screenX, screenY);

		return true;
	}

	@Override
	/**
	 * Upon a mouse / pointer moved this event occurs. It saves the mouse input data
	 * */
	public boolean mouseMoved(int screenX, int screenY) {
		mousePosition = new Point(screenX, screenY);

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Implement the keys
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Implement the keys
		return true;
	}

	@Override
	public boolean keyDown(int keycode) {

		// Exit hotkey //
		if (keycode == Keys.ESCAPE) {
			System.exit(0);
		}

		// TODO Implement the keys
		return true;
	}

}
