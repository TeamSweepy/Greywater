/**
 * Input handler for GUI. This class gets the input events first. Upon using them the methods return true, which means that the Game input
 * will not receive the event. Upon returning false, the event will be passed to the Game Input class
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
import com.teamsweepy.greywater.engine.Engine;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.GUI;

import com.badlogic.gdx.Input.Keys;

public class InputGUI extends InputHandler {

	public static Point2F position = new Point2F();

	@Override
	/** Upon a mouse / pointer click this event occurs. It saves the mouse input data */
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		isDown = true;
		mousePosition = new Point2F(screenX, screenY);

		Point2F mousePositionInGame = Camera.getDefault().unproject(mousePosition);
		position = mousePositionInGame;

		return GUI.handleInput(MOUSE_DOWN, mousePositionInGame);
	}

	@Override
	/** Upon a mouse / pointer unclick (release) this event occurs. It saves the mouse input data */
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isDown = false;
		mousePosition = new Point2F(screenX, screenY);

		Point2F mousePositionInGame = Camera.getDefault().unproject(mousePosition);
		position = mousePositionInGame;

		return GUI.handleInput(MOUSE_UP, mousePositionInGame);
	}

	@Override
	/** Upon a mouse / pointer moved this event occurs. It saves the mouse input data */
	public boolean mouseMoved(int screenX, int screenY) {
		mousePosition = new Point2F(screenX, screenY);

		Point2F mousePositionInGame = Camera.getDefault().unproject(mousePosition);
		position = mousePositionInGame;

		return GUI.handleInput(MOUSE_MOVED, mousePositionInGame);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE) {
			Engine.inGame = !Engine.inGame; //go to main menu
		}

		//move the camera for testing, TODO remove later
		if (keycode == Keys.W) {
			Camera.getDefault().move(0, 200);
		}
		if (keycode == Keys.A) {
			Camera.getDefault().move(-200, 0);
		}
		if (keycode == Keys.S) {
			Camera.getDefault().move(0, -200);
		}
		if (keycode == Keys.D) {
			Camera.getDefault().move(200, 0);
		}
		// TODO Implement the keys
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Implement the keys
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Implement the keys
		return false;
	}

}
