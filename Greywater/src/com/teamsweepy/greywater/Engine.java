/**
 * This drives the rest of the game.
 * 
 */
package com.teamsweepy.greywater;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Engine implements ApplicationListener {

	private OrthographicCamera camera;
	private SpriteBatch batch;

	public static final int NATIVE_WIDTH = 1600;
	public static final int NATIVE_HEIGHT = 900;
	public static final double NATIVE_ASPECT_RATIO = 16.0 / 9.0;


	/* *********** BULLSHIT TESTING VARIABLES ************* */
	private Texture texture;
	private Sprite sprite;
	private Sprite bork;


	/**
	 * Initialize core assets, automatically called by LibGDX
	 */
	@Override
	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, NATIVE_WIDTH, NATIVE_HEIGHT);

		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false); //binary texture sizes are for the 80's

		//set up bullshit testing variables TODO remove
		texture = new Texture(Gdx.files.internal("data/tavish_stand_south.png"));
		sprite = new Sprite(texture);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bork = new Sprite(new Texture(Gdx.files.internal("data/bork.png")));
	}

	/**
	 * Dispose of unmanaged assets such as the spritebatch and all textures
	 */
	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	/**
	 * Render is called 60 times per second by LibGDX.
	 * Render should call our own Render method that can be skipped if the system is lagging
	 * and it should also call the physics/logic update method, and never skip
	 */
	@Override
	public void render() {
		camera.update();
		camera.apply(Gdx.gl10);

		//scale from 1600x900 to whatever user screen is set to
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		batch.setProjectionMatrix(camera.combined); //openGL magic
		Gdx.gl.glClearColor(0, 0, 0, 1); //black background
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);


		//primary rendering area
		batch.begin();
		//TODO remove drawing BS images
		batch.draw(bork, 0, 0);
		batch.draw(sprite, 0, 0);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}
}
