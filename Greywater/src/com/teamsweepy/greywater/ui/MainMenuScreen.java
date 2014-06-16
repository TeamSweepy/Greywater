/**
 * Used to control logic and rendering for the main game menu.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.ui;

import javax.swing.JOptionPane;

import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Engine;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.net.GameClient;
import com.teamsweepy.greywater.net.GameServer;
import com.teamsweepy.greywater.ui.gui.GUI;
import com.teamsweepy.greywater.ui.gui.GUIComponent;
import com.teamsweepy.greywater.ui.gui.subgui.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

public class MainMenuScreen implements Screen {

	private Engine engine;
	private GUIComponent mainMenu;

	public MainMenuScreen(Engine eng) {
		((Music) AssetLoader.getAsset(Music.class, "Escadre.wav")).play();
		engine = eng;

		mainMenu = new GUIComponent();
		mainMenu.sprite = new Sprite("MenuBG", true);
		Button startButton = new Button(0, 0, "MenuItems", "NEWGAME") {

			@Override
			protected void clicked(boolean rightClick) {
				initMultiplayer();
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

	/**
	 * Prompt the player to start the server -> start server. Connect his client to a server
	 * 
	 * Always start the client
	 * */
	private void initMultiplayer() {
		// ask if the player wants to run the server;
		int temp = JOptionPane.showConfirmDialog(null, "Do you want to start the server?"); // yes = 0
		boolean startServer = temp == 0;

		if (startServer)
			engine.initServer();

		engine.initClient(startServer);

	}

	@Override
	public void render(float delta) {
		engine.guiBatch.begin(); // begin rendering
		GUI.render(engine.guiBatch);
		engine.guiBatch.end();// end rendering
	}

	public void tick(float delta) {
		GUI.tick(delta);
	}

	public void show() {
		mainMenu.setVisible(true);
	}

	@Override
	public void hide() {
		mainMenu.setVisible(false);
		((Music) AssetLoader.getAsset(Music.class, "Escadre.wav")).stop();
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
