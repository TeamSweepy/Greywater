/**
 * Used to control logic and rendering for the all tangible items in the world - tiles, characters, etc
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.entities.level;

import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.math.Point2F;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class Level {

	private TiledMap map;
	private Camera mainCamera;

	public Level() {
		map = new TmxMapLoader().load("data/map.tmx");

		mainCamera = Camera.getDefault();
	}

	public void render(SpriteBatch batch) {

		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
	
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = layer.getHeight() - 1; y >= 0; y--) {
				TiledMapTileLayer.Cell cell = layer.getCell(x, y);
				if (cell == null) continue;
				TiledMapTile tile = cell.getTile();
				TextureRegion region = tile.getTextureRegion();
				region.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

				Point2F p = mainCamera.toIsoCoord(56f * y, 56f * -x);
				batch.draw(region.getTexture(), p.x, p.y);
			}
		}

		layer = (TiledMapTileLayer) map.getLayers().get(1);
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = layer.getHeight() - 1; y >= 0; y--) {
				TiledMapTileLayer.Cell cell = layer.getCell(x, y);
				if (cell == null) continue;
				TiledMapTile tile = cell.getTile();
				TextureRegion region = tile.getTextureRegion();
				region.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

				Point2F p = mainCamera.toIsoCoord(56f * y , 56f * -x );
				batch.draw(region.getTexture(), p.x, p.y);
			}
		}
	}

	public void tick() {}



}
