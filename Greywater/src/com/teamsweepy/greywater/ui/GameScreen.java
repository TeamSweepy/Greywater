/**
 * Used to control logic and rendering for the game.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.Engine;
import com.teamsweepy.greywater.entities.components.Sprite;
import com.teamsweepy.greywater.entities.level.Level;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.GUI;

public class GameScreen implements Screen {

	private Engine engine;

	//testvar
	private Sprite aTestSprite;
	Level levelForTesting;
	int rotatingIncrement = 0;

	public GameScreen(Engine eng) {
		engine = eng;
		levelForTesting = new Level();
		GUI.initGUI();
	}

	@Override
	public void render(float delta) {
		//scale from 1600x900 to whatever user screen is set to and clear graphics
		Gdx.gl.glViewport((int) Camera.getDefault().xOffset, (int) Camera.getDefault().yOffset, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//this replaces the camera.translate function that doesn't work. 		
		Point2F offsetPoint = Camera.getDefault().getTranslatedForMatrix();
		engine.batch.setProjectionMatrix(engine.batch.getProjectionMatrix().translate(offsetPoint.x, offsetPoint.y, 0));

		engine.batch.begin();// start render

		levelForTesting.render(engine.batch);

		GUI.render(engine.batch);
		engine.batch.end();// end render
	}

	public void tick(float delta) {
		levelForTesting.tick(delta);
		GUI.tick();
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
