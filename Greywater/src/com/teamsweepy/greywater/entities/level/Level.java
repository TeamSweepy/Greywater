/**
 * Used to control logic and rendering for the all tangible items in the world - tiles, characters, etc
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.entities.level;

import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.entities.Mob;
import com.teamsweepy.greywater.entities.Player;
import com.teamsweepy.greywater.entities.components.Entity;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Level {

	private TiledMap map;
	private Camera mainCamera;
	private ArrayList<Entity> depthSortList;
	private Tile[][] tileList;
	Mob TestTavishMob;

	private Comparator<Entity> spriteSorter = new Comparator<Entity>() {

		@Override
		public int compare(Entity e1, Entity e2) {
			if (e1.getDepth() < e2.getDepth()) return -1;
			if (e1.getDepth() > e2.getDepth()) return 1;
			return 0;
		}

	};


	public Level() {
		map = new TmxMapLoader().load("data/map.tmx");
		mainCamera = Camera.getDefault();
		depthSortList = new ArrayList<Entity>();
		convertTiledMapToEntities();
		while (AssetLoader.tick() < 1f) {

		}
		TestTavishMob = new Player(9, 94);
		depthSortList.add(TestTavishMob);
	}

	public void render(SpriteBatch batch) {
		for (int x = 0; x < tileList.length; x++) {
			for (int y = 0; y < tileList[x].length; y++) {
				if (tileList[x][y] != null) {
					tileList[x][y].render(batch);
				}
			}
		}
		
		Collections.sort(depthSortList, spriteSorter);
		for (Entity e : depthSortList) {
			e.render(batch);
		}

	}

	public void tick(float deltaTime) {
		
		TestTavishMob.tick(deltaTime);
	}


	private void convertTiledMapToEntities() {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		tileList = new Tile[layer.getWidth()][layer.getHeight()];

		//convert floor into Tile objects
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = layer.getHeight() - 1; y >= 0; y--) {
				TiledMapTileLayer.Cell cell = layer.getCell(x, y);
				if (cell == null) continue;
				TiledMapTile tile = cell.getTile();
				TextureRegion region = tile.getTextureRegion();
				region.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
				if (tile.getProperties().containsKey("ANIMATED")) {
					//TODO support animation in tiles
				} else {
					tileList[x][y] = new Tile(region, x * 50, y * 50, 50);
				}
			}
		}//end outer for

		//get walls and doodads
		layer = (TiledMapTileLayer) map.getLayers().get(1);

		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = layer.getHeight() - 1; y >= 0; y--) {
				TiledMapTileLayer.Cell cell = layer.getCell(x, y);
				if (cell == null) continue;
				TiledMapTile tile = cell.getTile();
				TextureRegion region = tile.getTextureRegion();
				region.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
				depthSortList.add(new Tile(region, x * 50, y * 50, 50));
			}
		}
	}
}
