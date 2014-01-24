/**
 * Used to control logic and rendering for the game.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.ui;

import com.teamsweepy.greywater.engine.Engine;

import com.badlogic.gdx.Screen;


public class GameScreen implements Screen {

	private Engine engine;


	public GameScreen(Engine eng) {
		engine = eng;
	}


	@Override
	public void render(float delta) {
		engine.batch.begin();//start render

		engine.batch.end();//end render
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	/* **************** PROBABLY USELESS METHODS ********************* */

	@Override
	public void resize(int width, int height) {}

	@Override
	public void dispose() {}


}
