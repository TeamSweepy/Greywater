/**
 * Input handler for GUI. This class gets the input events first. Upon using them the methods return true, which means that the Game input will not 
 * receive the event. Upon returning false, the event will be passed to the game Input class
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

import com.badlogic.gdx.Input.Keys;

import java.awt.Point;

public class InputGUI extends InputHandler {

	@Override
	/**
	 * Upon a mouse / pointer click this event occurs. It saves the mouse input data
	 * */
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		isDown = true;
		mousePosition = new Point(screenX, screenY);

		// TODO return true, if the mouse position intersects one of the GUI elements
		return false;
	}

	@Override
	/**
	 * Upon a mouse / pointer unclick (release) this event occurs. It saves the mouse input data
	 * */
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isDown = false;
		mousePosition = new Point(screenX, screenY);

		// TODO return true, if the mouse position intersects one of the GUI elements
		return false;
	}

	@Override
	/**
	 * Upon a mouse / pointer moved this event occurs. It saves the mouse input data
	 * */
	public boolean mouseMoved(int screenX, int screenY) {
		mousePosition = new Point(screenX, screenY);

		// TODO return true, if the mouse position intersects one of the GUI elements
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.W){
			Camera.getDefault().move(0,900);
		}
		if(keycode == Keys.A){
			Camera.getDefault().move(-900,0 );
		}
		if(keycode == Keys.S){
			Camera.getDefault().move(0,-900 );
		}
		if(keycode == Keys.D){
			Camera.getDefault().move(900,0 );
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
