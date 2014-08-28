/**
 * Used to control logic and rendering for the all tangible items in the world - tiles, characters, etc
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

package com.teamsweepy.greywater.entity.level;

import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.ClockWorm;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.Player;
import com.teamsweepy.greywater.entity.Tinkerer;
import com.teamsweepy.greywater.entity.Vagrant;
import com.teamsweepy.greywater.entity.component.Entity;
import com.teamsweepy.greywater.entity.item.Item;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;

import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Town extends Level {


	ArrayList<Entity> entityList;

	/** Used for depth sorting */
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

	public Town(String mapPath) {
		
		map = new TmxMapLoader().load(mapPath);
		while (AssetLoader.tick() < 1f) {
			// do nothing TODO remove later
		}
		mobList = new ArrayList<Mob>();
		entityList = new ArrayList<Entity>();
		floorItemsList = new ArrayList<Item>();
		interactiveList = new ArrayList<Entity>();
		depthSortList = new ArrayList<Entity>();
		exitTiles = new ArrayList<Tile>();
		convertTiledMapToEntities();

		mainCamera = Camera.getDefault();
		mapCostList = new int[tileList.length][tileList[0].length];
		setUpMapCosts();

		interactiveList.addAll(mobList);
		exitTiles.add(tileList[0][0]);
		currentLevel = this;


	}



	/** Render all components "in" the world - mobs, doodads, loot, etc */
	public void render(SpriteBatch batch) {
		for (int x = 0; x < tileList.length; x++) {
			for (int y = 0; y < tileList[x].length; y++) {
				if (tileList[x][y] != null)
					tileList[x][y].render(batch);

				if (wallList[x][y] != null)
					depthSortList.add(wallList[x][y]);
			}
		}
		for (Entity entity : entityList) {
			depthSortList.add(entity);
		}
		for (Mob mob : mobList) {
			depthSortList.add(mob);
		}
		for (Item item : floorItemsList) {
			depthSortList.add(item);
		}

		Collections.sort(depthSortList, spriteSorter);
		for (Entity e : depthSortList) {
			e.render(batch);
		}

		depthSortList.clear();
	}

	/** Tick logic of all components in the world - mobs, doodads, loot, etc */
	public void tick(float deltaTime) {
		super.tick(deltaTime);

		Point p;
		for (Mob mob : mobList) {
			if ((mob instanceof Player))
				continue;
			p = Globals.toTileIndices(mob.getLocation());
			mapCostList[p.x][p.y] = 1;
		}
	}


	/** Finds if an entity's logical hitbox collides with another hitbox in objective coordinates */
	public Entity getCollidedEntity(Rectangle intersectionArea) {
		for (Entity e : interactiveList) {
			if (e.checkPhysicalIntersection(intersectionArea))
				return e;
		}
		return null;
	}

	/** Finds if an entity's logical hitbox collides with another hitbox in objective coordinates */
	public List<Mob> getCollidedMobs(List<Rectangle> intersectionAreas) {
		ArrayList<Mob> collisions = new ArrayList<Mob>();
		for (Mob e : mobList) {
			for (Rectangle intersectionArea : intersectionAreas) {
				if (e.checkPhysicalIntersection(intersectionArea))
					collisions.add(e);
			}
		}
		return collisions;
	}

	/** Finds if an entity's sprite is clicked by a given screen location. Will not return the same entity who is searching */
	public Entity getClickedEntity(Point2F clickLocation, Entity clicker) {
		for (Entity e : interactiveList) {
			if (e.equals(clicker))
				continue;
			if (e.checkClickedInteraction(clickLocation))
				return e;
		}
		return null;
	}

	/** Converts map from Tiled Map Editor (TMX) into Entity Objects. You can probably ignore this method. */
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

		//		 get walls and doodads
		layer = (TiledMapTileLayer) map.getLayers().get(1);
		wallList = new Tile[layer.getWidth()][layer.getHeight()];
		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = layer.getHeight() - 1; y >= 0; y--) {
				TiledMapTileLayer.Cell cell = layer.getCell(x, y);
				//IF CELL IS INTERACTIVE
				//interactiveList.add(cell) TODO
				if (cell == null)
					continue;
				TiledMapTile tile = cell.getTile();
				TextureRegion region = tile.getTextureRegion();
				region.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
				if (tile.getProperties().get("Walkable") != null)
					wallList[x][y] = new Tile(region, x * 50, y * 50, 50, true);
				else
					wallList[x][y] = new Tile(region, x * 50, y * 50, 50);

				if (cell.getTile().getProperties() != null && cell.getTile().getProperties().containsKey("GATE")) {
					this.exitTiles.add(wallList[x][y]);
				}
			}
		}


		MapLayer objLayer = (MapLayer) map.getLayers().get("Object Layer 1");
		for (MapObject mo : objLayer.getObjects()) {
			if (mo.getProperties().containsKey("Tinkerer")) {
				float xLoc = Float.parseFloat(mo.getProperties().get("x").toString()) / 56;
				float yLoc = Float.parseFloat(mo.getProperties().get("y").toString()) / 56;
				mobList.add(new Tinkerer(xLoc, yLoc, this));
			}
		}


	}

	public int[][] getMapAsCosts() {
		return mapCostList;
	}

	@Override
	public int[][] setUpMapCosts() {
		for (int x = 0; x < tileList.length; x++) {
			for (int y = 0; y < tileList[0].length; y++) {
				if (isTileWalkable(x, y)) {
					mapCostList[x][y] = 0;
				} else {
					mapCostList[x][y] = 1;
				}
			}
		}
		return mapCostList;
	}

	public List<Tile> getTiles(List<Point> tileindices) {
		ArrayList<Tile> tileAList = new ArrayList<Tile>();
		for (Point tile : tileindices) {
			if (tile.x < 0 || tile.y < 0 || tile.x > tileList.length || tile.y > tileList[0].length)
				continue;
			tileAList.add(tileList[tile.x][tile.y]);
		}
		return tileAList;
	}

}
