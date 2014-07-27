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

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

public class InputGUI extends InputHandler {

	public static Point2F position = new Point2F();
    private Engine engine;

    public InputGUI(Engine engine) {
        this.engine = engine;
    }

	/** When the mouse wheel get's scrolled **/
	@Override
	public boolean scrolled(int amount) {
		// TODO: Shift scrolling, on trackpads you have 2 scroll direction. This behaviour also happends when pressing shift
		return GUI.handleInput(WHEEL_SCROLL, position, amount);
	}

	@Override
	/** Upon a mouse / pointer click this event occurs. It saves the mouse input data */
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		isDown = true;
		mousePosition = new Point2F(screenX, screenY);

		Point2F mousePositionInGame = Camera.getDefault().unproject(mousePosition);
		position = mousePositionInGame;

		boolean touchedGUI = GUI.handleInput(MOUSE_DOWN, mousePositionInGame, button == Buttons.RIGHT);
		if (!touchedGUI) { // throw item from the cursor
			return GUI.getCursor().handleClick();
		}

		return touchedGUI;
	}

	@Override
	/** Upon a mouse / pointer unclick (release) this event occurs. It saves the mouse input data */
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isDown = false;
		mousePosition = new Point2F(screenX, screenY);

		Point2F mousePositionInGame = Camera.getDefault().unproject(mousePosition);
		position = mousePositionInGame;

		return GUI.handleInput(MOUSE_UP, mousePositionInGame, button == Buttons.RIGHT);
	}

	@Override
	/** Upon a mouse / pointer moved this event occurs. It saves the mouse input data */
	public boolean mouseMoved(int screenX, int screenY) {
		mousePosition = new Point2F(screenX, screenY);

		Point2F mousePositionInGame = Camera.getDefault().unproject(mousePosition);
		position = mousePositionInGame;

		return GUI.handleInput(MOUSE_MOVED, mousePositionInGame, false);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mousePosition = new Point2F(screenX, screenY);

		Point2F mousePositionInGame = Camera.getDefault().unproject(mousePosition);
		position = mousePositionInGame;

		return GUI.handleInput(MOUSE_DRAGGED, mousePositionInGame, false);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE) {
            engine.pause(engine.getInGame());
//            Engine.pause(!Engine.inGame);
//			Engine.inGame = !Engine.inGame; //go to main menu
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
