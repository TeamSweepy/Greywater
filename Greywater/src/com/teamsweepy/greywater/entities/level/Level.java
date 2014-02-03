/**
 * Used to control logic and rendering for the all tangible items in the world - tiles, characters, etc
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.entities.level;

import com.teamsweepy.greywater.engine.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;


public class Level {

	private TiledMap map;
	private Camera mainCamera;

	public Level() {
		map = new TmxMapLoader().load("data/map.tmx");

		mainCamera = Camera.getDefault();
	}

	public void render(SpriteBatch batch) {
		Camera.getDefault().move(-20, -20);
		
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		batch.begin();
		for (int x = layer.getWidth() - 1; x >= 0; x--) {
			for (int y = layer.getHeight() - 1; y >= 0; y--) {
				TiledMapTileLayer.Cell cell = layer.getCell(x, y);
				if (cell == null) continue;
				TiledMapTile tile = cell.getTile();
				TextureRegion region = tile.getTextureRegion();
				region.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

				Vector2 p = mainCamera.toIsoCoord(56f * x, 56f * y);
				batch.draw(region.getTexture(), p.x, p.y);
				

			}
		}
		
		batch.end();

	}

	public void tick() {}



}
