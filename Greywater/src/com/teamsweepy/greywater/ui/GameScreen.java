/**
 * Used to control logic and rendering for the in-game action.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.ui;

import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.Engine;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.entity.level.LevelHandler;
import com.teamsweepy.greywater.entity.level.Town;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.Cursor;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.HUD;
import com.teamsweepy.greywater.ui.gui.Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import java.awt.Point;

public class GameScreen implements Screen {

	private Engine engine;
	Sprite fog;

	//testvar
	Level currentLevel;
	LevelHandler levelHandler;
	HUD playerHUD;
	Inventory playerInventory;
	Cursor playerCursor;
	boolean ticking = true;

	public GameScreen(Engine eng) {
		while (AssetLoader.tick() < 1f) {
			// do nothing TODO remove later
		}
		fog = new Sprite("light", true);
		//Mob TestTavishMob = Player.initLocalPlayer(4f, 90f, null);
		engine = eng;

		// Initiate all the levels
		levelHandler = new LevelHandler();

		/*Level town = new Town("data/map/Greywater.tmx");
		currentLevel = town;
		Level dungeon = new Level("data/map/Dungeon.tmx");
		currentLevel.setSwapLevel(dungeon);
		dungeon.setSwapLevel(town);*/

		playerHUD = new HUD();
		playerCursor = new Cursor();
		GUI.addGUIComponent(playerHUD, GUI.TOP_LAYER);
		GUI.addGUIComponent(playerCursor, GUI.TOP_LAYER);

		this.hide();
	}

	@Override
	public void render(float delta) {
		//scale from 1600x900 to whatever user screen is set to and clear graphics
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//translate sprite batch like camera
		Point2F offsetPoint = Camera.getDefault().getTranslatedMatrix();
		engine.gameBatch.setProjectionMatrix(engine.gameBatch.getProjectionMatrix().translate(offsetPoint.x, offsetPoint.y, 0));

		engine.gameBatch.begin();// start render
		{// get the level the local player is on
			if (Player.localPlayer != null)
				if (Player.localPlayer.getLevel() != null)
					Player.localPlayer.getLevel().render(engine.gameBatch);
		}
		engine.gameBatch.end();// end render

		engine.guiBatch.begin();
		fog.render(engine.guiBatch, 0, 0);
		GUI.render(engine.guiBatch);
		engine.guiBatch.end();
	}

	public void tick(float delta) {
		if (!ticking)
			return;
		//currentLevel = currentLevel.getCurrentLevel();
		//currentLevel.tick(delta);
		levelHandler.tick(delta);
		GUI.tick(delta);
	}

	@Override
	public void show() {
		playerCursor.setVisible(true);
		playerHUD.setVisible(true);
	}

	@Override
	public void hide() {
		GUI.setVisibility(false);
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
