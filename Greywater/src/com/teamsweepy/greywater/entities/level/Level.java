/**
 * Used to control logic and rendering for the all tangible items in the world - tiles, characters, etc
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.entities.level;

import com.teamsweepy.greywater.engine.Engine;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.awt.Point;


public class Level {

	private TiledMap map;
	private Engine eng;


	public Level(Engine e) {
		eng = e;
		map = new TmxMapLoader().load("data/map.tmx");
	}

	public void render() {

		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		eng.batch.begin();
		for (int x = layer.getWidth() - 1; x >= 0; x--) {
			for (int y = layer.getHeight() - 1; y >= 0; y--) {
				TiledMapTileLayer.Cell cell = layer.getCell(x, y);
				TiledMapTile tile = cell.getTile();
				TextureRegion region = tile.getTextureRegion();
				region.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

				Point p = getIsoCoords(56 * (x), 56 * (y));

				eng.batch.draw(region.getTexture(), (float) p.getX(), (float) p.getY());

			}
		}
		eng.batch.end();

	}

	public void tick() {}

	public static Point getIsoCoords(float x, float y) {
		float X = x - y;
		float Y = (x + y) / 2;
		return new Point(Math.round(X), Math.round(Y));
	}

}
