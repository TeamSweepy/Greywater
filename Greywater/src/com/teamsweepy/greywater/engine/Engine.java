/**
 * Engine controls the rest of the game - main render/update loop are located here, as is the bulk of the initialization. Very little core
 * game logic should go here, however.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.engine;

import com.teamsweepy.greywater.ui.GameScreen;
import com.teamsweepy.greywater.ui.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Engine extends Game {

	/* ********************* SACRED GAME ANIMATION/WINDOW CONSTANTS ************************ */
	public static final short NATIVE_WIDTH = 1600;
	public static final short NATIVE_HEIGHT = 900;
	public static final double NATIVE_ASPECT_RATIO = 16.0 / 9.0;
	public static final int ANIMATION_PERIOD_NANOSEC = 16666666; // 60 FPS
	public static final byte MAX_FRAME_SKIPS = 40; //not more than 40 frames can be skipped due to lag.

	/* ********************* STATISTICS AND TIMEKEEPING VARIABLES ************************ */
	private double secondsElapsed = 0.0;
	private double excessTime = 0.0;
	private float deltaTime = 0f;
	private short frameCount = 0;
	private short tickCount = 0;
	private short skipCount = 0;

	//other stuff, will be sorted when there's more
	public OrthographicCamera camera;
	public SpriteBatch batch;
	private InputHandler inputHandler;
	private GameScreen gs;

	/**
	 * Initialize core assets, automatically called by LibGDX
	 */
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, NATIVE_WIDTH, NATIVE_HEIGHT);

		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false); //binary texture sizes are for the 80's

		inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);

		AssetLoader.init();
		this.setScreen(new MainMenuScreen(this));
	}

	/**
	 * Dispose of unmanaged assets such as the spritebatch and all textures
	 */
	@Override
	public void dispose() {
		batch.dispose();
	}

	/**
	 * Render is called 60 times per second by LibGDX. Render should call our own Render method that can be skipped if the system is lagging
	 * and it should also call the physics/logic update method, and never skip
	 */
	@Override
	public void render() {
		deltaTime = Gdx.graphics.getDeltaTime();
		secondsElapsed += deltaTime;
		excessTime += deltaTime * 1000000000 - ANIMATION_PERIOD_NANOSEC; //nano second accuracy!
		skipCount = 0;

		camera.update();
		camera.apply(Gdx.gl10);

		//scale from 1600x900 to whatever user screen is set to and clear graphics
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1); //black background
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		tick(deltaTime); //tick all subcomponents
		tickCount++;

		super.render(); //calls the current screen render method
		frameCount++;

		//if the frame is taking too long, update without rendering
		while ((excessTime > ANIMATION_PERIOD_NANOSEC) && (skipCount < MAX_FRAME_SKIPS)) {
			skipCount++;
			excessTime -= ANIMATION_PERIOD_NANOSEC;
			tick(deltaTime);
			tickCount++;
		}

		if (secondsElapsed > 10.0) 
			printStats();

	}

	public void tick(float deltaTime) {}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	/**
	 * Print Frames per second and Updates per second data and reset their counters.
	 */
	private void printStats() {
		System.out.println();
		System.out.println();
		System.out.println("*******************************");
		System.out.println("Ticks per sec:" + tickCount / secondsElapsed);
		System.out.println("Time elapsed: " + secondsElapsed);
		System.out.println("Ticks total " + tickCount);

		System.out.println("FPS:" + frameCount / secondsElapsed);
		System.out.println("Frames total " + frameCount);
		System.out.println("*******************************");
		System.out.println();
		System.out.println();

		tickCount = 0;
		secondsElapsed = 0;
		frameCount = 0;
	}

}
