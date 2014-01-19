
package com.teamsweepy.greywater.ui;

import com.teamsweepy.greywater.engine.Engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class MainMenuScreen implements Screen {

	private Engine engine;
	private Texture texture;
	private Sprite sprite;

	public MainMenuScreen(Engine eng) {
		engine = eng;

		texture = new Texture(Gdx.files.internal("data/tavish_stand_south.png"));
		sprite = new Sprite(texture);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	@Override
	public void render(float delta) {
		engine.batch.begin(); //begin rendering
		
		
		engine.batch.draw(sprite, 0, 0);
		
		
		engine.batch.end();//end rendering


	}

	@Override
	public void resize(int width, int height) {
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

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
