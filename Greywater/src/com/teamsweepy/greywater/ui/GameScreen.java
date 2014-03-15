/**
 * Used to control logic and rendering for the in-game action.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.ui;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.Engine;
import com.teamsweepy.greywater.entities.Player;
import com.teamsweepy.greywater.entities.level.Level;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.Cursor;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.HUD;
import com.teamsweepy.greywater.ui.gui.Inventory;
import com.teamsweepy.greywater.ui.gui.subgui.Dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

	private Engine engine;

	//testvar
	Level levelForTesting;
	SpriteBatch guiBatch;

	HUD playerHUD;
	Inventory playerInventory;
	Cursor playerCursor;
	boolean ticking = true;

	public GameScreen(Engine eng) {
		engine = eng;

		levelForTesting = new Level();
		playerInventory = new Inventory();
		playerHUD = new HUD();
		playerCursor = new Cursor();
		Player.getLocalPlayer().setInventory(playerInventory);
		GUI.addGUIComponent(playerHUD);
		GUI.addGUIComponent(playerInventory);
		GUI.addGUIComponent(playerCursor);
		GUI.addGUIComponent(new Dialog(500, 500, 600, 200));
		this.hide();
	}

	@Override
	public void render(float delta) {
		//scale from 1600x900 to whatever user screen is set to and clear graphics
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//translate sprite batch like camera
		Point2F offsetPoint = Camera.getDefault().getTranslatedMatrix();
		engine.batch.setProjectionMatrix(engine.batch.getProjectionMatrix().translate(offsetPoint.x, offsetPoint.y, 0));

		engine.batch.begin();// start render

		levelForTesting.render(engine.batch);
		GUI.render(engine.batch);

		engine.batch.end();// end render
	}

	public void tick(float delta) {
		if (!ticking)
			return;
		levelForTesting.tick(delta);
		GUI.tick(delta);
	}

	@Override
	public void show() {
		playerCursor.setVisible(true);
		playerHUD.setVisible(true);
		playerInventory.setVisible(true);

	}

	@Override
	public void hide() {
		playerCursor.setVisible(false);
		playerHUD.setVisible(false);
		playerInventory.setVisible(false);

	}

	@Override
	public void pause() {
		ticking = false;
		playerCursor.setTicking(false);
		playerHUD.setTicking(false);
		playerInventory.setTicking(false);

	}

	@Override
	public void resume() {
		ticking = true;
		playerCursor.setTicking(true);
		playerHUD.setTicking(true);
		playerInventory.setTicking(true);
	}

	/* **************** PROBABLY USELESS METHODS ********************* */

	@Override
	public void resize(int width, int height) {}

	@Override
	public void dispose() {}

}
