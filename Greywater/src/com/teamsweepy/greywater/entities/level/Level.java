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
import com.teamsweepy.greywater.entities.NPC;
import com.teamsweepy.greywater.entities.Player;
import com.teamsweepy.greywater.entities.Watchman;
import com.teamsweepy.greywater.entities.components.Entity;
import com.teamsweepy.greywater.math.Point2F;

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
	private int[][] intWallList;
	private ArrayList<Mob> mobList;
	private ArrayList<Entity> interactiveList;

	Camera mainCamera;

	// test variable
	Mob TestTavishMob;
	Mob TestAIMob;

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

	public Level() {
		while (AssetLoader.tick() < 1f) {
			// do nothing TODO remove later
		}
		map = new TmxMapLoader().load("data/map.tmx");
		mainCamera = Camera.getDefault();
		convertTiledMapToEntities();
		setUpMapCosts();

		mobList = new ArrayList<Mob>();
		interactiveList = new ArrayList<Entity>();
		depthSortList = new ArrayList<Entity>();

		TestTavishMob = Player.initLocalPlayer(4f, 90f, this);
		mobList.add(TestTavishMob);
		//		//		for(int i = 0; i < 20; i ++){
		mobList.add(new Watchman(20, 70, this, TestTavishMob));
		mobList.add(new Watchman(20, 93, this, TestTavishMob));
		mobList.add(new NPC(4, 93, this));
		//		}
		interactiveList.addAll(mobList);
		Camera.getDefault().moveTo(Globals.toIsoCoord(TestTavishMob.getX(), TestTavishMob.getY()));

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
		removeOccludingWalls();
		for (Mob mob : mobList) {
			depthSortList.add(mob);
		}

		Collections.sort(depthSortList, spriteSorter);
		for (Entity e : depthSortList) {
			e.render(batch);
		}

		depthSortList.clear();
	}

	/** Tick logic of all components in the world - mobs, doodads, loot, etc */
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
		Camera.getDefault().moveTo(Globals.toIsoCoord(TestTavishMob.getX(), TestTavishMob.getY()));
	}

	/** Creates 2D grid that indicates if a tile is obstructed with a closed door/ wall/ etc */
	public boolean isTileWalkable(int x, int y) {
		if (wallList[x][y] != null)
			return false;
		return true;
	}

	/** Check to see if a given shape collides with the level geometry */
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
				Rectangle r = wallList[x][y].getHitbox();
				if (collisionVolume.intersects(r.x, r.y, r.width, r.height))
					return true;
			}
		}
		return false;
	}

	/** Finds if an entity's logical hitbox collides with another hitbox in objective coordinates */
	public Entity getCollidedEntity(Rectangle intersectionArea) {
		for (Entity e : interactiveList) {
			if (e.checkPhysicalIntersection(intersectionArea))
				return e;
		}
		return null;
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

	public void removeOccludingWalls() {
		Point pLoc = Player.getLocalPlayer().getTileLocation();
		for (int i = 0; i < 6; i++) {
			removeOccludingWalls(pLoc.x + i, pLoc.y, false);
			removeOccludingWalls(pLoc.x, pLoc.y - i, true);
		}
	}

	private void removeOccludingWalls(int x, int y, boolean xTransverse) {
		if (!isWall(x, y) || wallList[x][y].isTransparent()) { //if invalid tile
			return;
		}

		if (isWall(x, y + 1) || isWall(x, y - 1)) { //if corner, stop
			if (isWall(x + 1, y) || isWall(x - 1, y)) {
				return;
			}
		}
		wallList[x][y].setTransparency(true, xTransverse);
		if (xTransverse) {
			removeOccludingWalls(x - 1, y, xTransverse);
			removeOccludingWalls(x + 1, y, xTransverse);
		} else {
			removeOccludingWalls(x, y - 1, xTransverse);
			removeOccludingWalls(x, y + 1, xTransverse);
		}


	}

	private boolean isWall(int x, int y) {
		if (x < 0 || x >= wallList.length || y < 0 || y >= wallList[0].length || wallList[x][y] == null) //if invalid tile
			return false;
		return true;
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

		// get walls and doodads
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
				wallList[x][y] = new Tile(region, x * 50, y * 50, 50);
			}
		}
	}

	/** Returns point with map X and Y dimensions */
	public Point getMapDimensions() {
		return new Point(tileList.length, tileList[0].length);
	}


	public int[][] getMapAsCosts() {
		return intWallList;
	}

	public int[][] setUpMapCosts() {
		intWallList = new int[tileList.length][tileList[0].length];
		for (int x = 0; x < tileList.length; x++) {
			for (int y = 0; y < tileList[0].length; y++) {
				if (isTileWalkable(x, y)) {
					intWallList[x][y] = 0;
				} else {
					intWallList[x][y] = 1;
				}
			}
		}
		return intWallList;
	}

}
