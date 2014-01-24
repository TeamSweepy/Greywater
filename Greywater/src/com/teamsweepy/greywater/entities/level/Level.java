/**
 * Used to control logic and rendering for the all tangible items in the world - tiles, characters, etc
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.entities.level;

import com.teamsweepy.greywater.engine.Engine;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;


public class Level {

	private TiledMap map;
	private IsometricTiledMapRenderer renderer;
	private Engine e;

	public Level(Engine eng) {
		e = eng;

		map = null; //TODO fix isometrictiledmaprenderer which doesn't draw characters ._.
		renderer = new IsometricTiledMapRenderer(map, eng.batch);
		renderer.setView(eng.camera);
	}

	public void render() {
		renderer.render();
	}
	
	public void tick(){
		
	}
}
