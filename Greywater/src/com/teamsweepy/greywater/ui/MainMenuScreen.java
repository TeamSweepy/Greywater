
/**
 * Used to control logic and rendering for the main game menu.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.ui;

import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Engine;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.GUIComponent;
import com.teamsweepy.greywater.ui.gui.subgui.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.teamsweepy.greywater.utils.SoundManager;

public class MainMenuScreen extends EngineScreen {
	private GUIComponent mainMenu;

	public MainMenuScreen(Engine eng) {
        super(eng);

        SoundManager.playMusic("Escadre.wav");


		mainMenu = new GUIComponent();
		mainMenu.sprite = new Sprite("MenuBG", true);
		Button startButton = new Button(0, 0, "MenuItems", "NEWGAME") {

			@Override
			protected void clicked(boolean rightClick) {
				Engine.inGame = true;
			}
		};

		startButton.centerImage(800, 425);
		Button exitButton = new Button(0, 0, "MenuItems", "EXIT") {

			@Override
			protected void clicked(boolean rightClick) {
				Gdx.app.exit();
			}
		};
		exitButton.centerImage(800, 250);
		mainMenu.addGUIComponent(startButton);
		mainMenu.addGUIComponent(exitButton);
		mainMenu.setVisible(true);
		GUI.addGUIComponent(mainMenu);

	}

	@Override
	public void render(float delta) {
		engine.guiBatch.begin(); // begin rendering
		GUI.render(engine.guiBatch);
		engine.guiBatch.end();// end rendering
	}

    @Override
	public void tick(float delta) {
		GUI.tick(delta);
	}

    @Override
	public void show() {
		mainMenu.setVisible(true);
	}

	@Override
	public void hide() {
		mainMenu.setVisible(false);
        SoundManager.stopMusic();
	}

	@Override
	public void pause() {
		mainMenu.setTicking(false);
	}

	@Override
	public void resume() {
		mainMenu.setTicking(true);

	}

	/* **************** PROBABLY USELESS METHODS ********************* */

	@Override
	public void resize(int width, int height) {}

	@Override
	public void dispose() {}

}
