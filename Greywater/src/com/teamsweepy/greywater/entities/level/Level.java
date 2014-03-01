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
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entities.Mob;
import com.teamsweepy.greywater.entities.Player;
import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.GUI;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Level {

	private TiledMap map;
	private ArrayList<Entity> depthSortList;
	private Tile[][] tileList;
	private Tile[][] wallList;
	private ArrayList<Mob> mobList;

	Camera mainCamera;

	// test variable
	Mob TestTavishMob;

	private Comparator<Entity> spriteSorter = new Comparator<Entity>() {

		@Override
		public int compare(Entity e1, Entity e2) {
			if (e1.getDepth() < e2.getDepth())
				return -1;
			if (e1.getDepth() > e2.getDepth())
				return 1;
			return 0;
		}

	};

	public Level() {
		while (AssetLoader.tick() < 1f) {
			// do nothing TODO remove later
		}
		map = new TmxMapLoader().load("data/map.tmx");
		mainCamera = Camera.getDefault();
		convertTiledMapToEntities();

		mobList = new ArrayList<Mob>();
		depthSortList = new ArrayList<Entity>();
		TestTavishMob = new Player(0, 0, this);
		mobList.add(TestTavishMob);

		

	}

	public void render(SpriteBatch batch) {
		for (int x = 0; x < tileList.length; x++) {
			for (int y = 0; y < tileList[x].length; y++) {
				if (tileList[x][y] != null)
					tileList[x][y].render(batch);

				if (wallList[x][y] != null)
					depthSortList.add(wallList[x][y]);
			}
		}

		for (Mob mob : mobList) {
			depthSortList.add(mob);
		}

		Collections.sort(depthSortList, spriteSorter);
		for (Entity e : depthSortList) {
			e.render(batch);
		}

		depthSortList.clear();
	}

	public void tick(float deltaTime) {
		for (int x = 0; x < tileList.length; x++) {
			for (int y = 0; y < tileList[x].length; y++) {
				if (tileList[x][y] != null)
					tileList[x][y].tick(deltaTime);

				if (wallList[x][y] != null)
					wallList[x][y].tick(deltaTime);
			}
		}
		for (Mob mob : mobList) {
			mob.tick(deltaTime);
		}
	}

	public boolean isTileWalkable(int x, int y) {
		if (wallList[x][y] != null)
			return false;
		return true;
	}

	/**
	 * Check to see if a given shape collides with the level geometry
	 */
	public boolean checkLevelCollision(Shape collisionVolume) {
		if (collisionVolume == null)
			return false;
		Point area = Globals.toTileIndices(collisionVolume.getBounds().x, collisionVolume.getBounds().y);

		// only check tiles near the shape, not the whole map
		int areaX = Math.round(area.x);
		int areaY = Math.round(area.y);
		if (areaX < 0)
			areaX = 0;
		if (areaY < 0)
			areaY = 0;
		int areaXEnd = areaX + 21;
		int areaYEnd = areaY + 21;
		if (areaXEnd > tileList.length)
			areaXEnd = tileList.length;
		if (areaYEnd > tileList[0].length)
			areaYEnd = tileList[0].length;

		for (int x = areaX; x < areaXEnd; x++) {
			for (int y = areaY; y < areaYEnd; y++) {
				if (wallList[x][y] == null)
					continue;
				Rectangle r = wallList[x][y].getPhysicsShape();
				if (collisionVolume.intersects(r.x, r.y, r.width, r.height))
					return true;
			}
		}
		return false;
	}

	/**
	 * Converts map from Tiled Map Editor (TMX) into Entity Objects. You can probably ignore this method.
	 */
	private void convertTiledMapToEntities() {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		tileList = new Tile[layer.getWidth()][layer.getHeight()];

		// convert floor into Tile objects
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = layer.getHeight() - 1; y >= 0; y--) {
				TiledMapTileLayer.Cell cell = layer.getCell(x, y);

				if (cell == null)
					continue;

				TiledMapTile tile = cell.getTile();
				TextureRegion region = tile.getTextureRegion();
				region.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
				if (tile.getProperties().containsKey("ANIMATED")) {
					// TODO support animation in tiles
				} else {
					tileList[x][y] = new Tile(region, x * 50, y * 50, 50);
				}
			}
		}// end outer for

		// get walls and doodads
		layer = (TiledMapTileLayer) map.getLayers().get(1);
		wallList = new Tile[layer.getWidth()][layer.getHeight()];
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = layer.getHeight() - 1; y >= 0; y--) {
				TiledMapTileLayer.Cell cell = layer.getCell(x, y);

				if (cell == null)
					continue;

				TiledMapTile tile = cell.getTile();
				TextureRegion region = tile.getTextureRegion();
				region.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
				wallList[x][y] = new Tile(region, x * 50, y * 50, 50);
			}
		}
	}

	public Point getMapDimensions() {
		return new Point(tileList.length, tileList[0].length);
	}
}
