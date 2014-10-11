/**
 * Input handler for Gane view. It is the last one that accepts the events. If others did not absorb the event, it gets passed to this
 * listener. All methods return true, since it will always take the input.
 * 
 * The methods are static, since there will only be 1 instance of every input
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Ziga
 */

package com.teamsweepy.greywater.engine.input;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class InputGame extends InputHandler {

	@Override
	/** Upon a mouse / pointer click this event occurs. It saves the mouse input data */
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		isDown = true;
		mousePosition = Camera.getDefault().unproject(new Point2F(screenX, screenY));
		Player.handleInput(mousePosition, isDown, -69);
		return true;
	}

	@Override
	/** Upon a mouse / pointer unclick (release) this event occurs. It saves the mouse input data */
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isDown = false;
		Player.handleInput(mousePosition, isDown, -69);
		return true;
	}

	@Override
	/** Upon a mouse / pointer moved this event occurs. It saves the mouse input data */
	public boolean mouseMoved(int screenX, int screenY) {
		mousePosition = Camera.getDefault().unproject(new Point2F(screenX, screenY));
		Player.handleInput(mousePosition, false, -69);
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
		if (keycode == Keys.BACKSPACE) {
			Gdx.app.exit();
		}

		// TODO Implement the keys
		return true;
	}

}
