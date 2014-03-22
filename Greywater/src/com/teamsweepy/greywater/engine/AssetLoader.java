/**
 * AssetLoader is essentially a wrapper for the LibGDX AssetManager. It adds some convenience methods such as parsing asset-sets. Basically,
 * these methods could be written elsewhere, but they will get used frequently and there's no point in cluttering up other classes with this
 * stuff.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */

package com.teamsweepy.greywater.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;

public class AssetLoader {

	private final static String ASSET_FOLDER = "data/";

	// wanted to name it assMan, didn't, because I'm a grown up
	private static AssetManager assetManager;

	/**
	 * Must be called before any other methods, initializes the asset manager component
	 */
	public static void init() {
		assetManager = new AssetManager();
		parseINI("Assets.ini"); // load common assets such as the HUD and menu
	}

	/** Loads all files listed in the given ini file. Note that files are expect to be of the format: LOAD; Filepath; Type */
	public static void parseINI(String iniName) {
		try {
			FileHandle setFile = Gdx.files.internal(ASSET_FOLDER + iniName);
			BufferedReader br = setFile.reader(64); //that's numberwang
			//64 is totally arbitrary here, but it works and its a nice number

			while (br.ready()) {
				String currentLine = br.readLine();

				if (currentLine.startsWith("//") || currentLine.length() < 6)
					continue;

				String action = currentLine.substring(0, currentLine.indexOf(";")).trim();
				String fileName = ASSET_FOLDER + currentLine.substring(currentLine.indexOf(";") + 1, currentLine.lastIndexOf(";")).trim();
				String type = currentLine.substring(currentLine.lastIndexOf(";") + 1).trim();

				if (action.equalsIgnoreCase("LOAD")) {
					assetManager.load(fileName, getAssetType(type));
				} else
					System.out.println("Invalid file in " + iniName + " - " + currentLine);
			}

			br.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Critical File Missing from Assets.ini. " + ex.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Could not close a reader " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}


	/**
	 * Update loaders, create assets if loaders have completed.
	 * @return percent complete as float from 0 - 1.
	 */
	public static float tick() {
		assetManager.update();
		return assetManager.getProgress();
	}

	/**
	 * Getter for assets for use in game.
	 * @param type - class of the asset type
	 * @param name - name of the asset to get
	 * @return The asset requested or null
	 */
	public static <T> T getAsset(Class<?> type, String name) {
//		if (type == ParticleEffect.class) {
//			T asset = ((T) assetManager.get(ASSET_FOLDER + name, type));
//			try {
//				return (T) asset;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		T asset = (T) assetManager.get(ASSET_FOLDER + name, type);
		return asset;
	}

	/**
	 * Used to parse the type from INI files from a string into a proper type object.
	 * @param type - name of the type as string from INI
	 * @return - type object
	 * @throws Exception - errors on no such type. Spelling counts!
	 */
	private static Class<?> getAssetType(String type) throws Exception {
		//no switch used here to support Java 6
		if (type.equalsIgnoreCase("Texture"))
			return Texture.class;
		if (type.equalsIgnoreCase("BitmapFont"))
			return BitmapFont.class;
		if (type.equalsIgnoreCase("TextureAtlas"))
			return TextureAtlas.class;
		if (type.equalsIgnoreCase("Music"))
			return Music.class;
		if (type.equalsIgnoreCase("Sound"))
			return Sound.class;
		if (type.equalsIgnoreCase("ParticleEffect"))
			return ParticleEffect.class;
		if (type.equalsIgnoreCase("TiledMap"))
			return TiledMap.class;

		throw new Exception("Invalid Class Specified in AssetLoader! Invalid type - " + type);
	}

	/** Remove all assets that the manager has */
	public static void disposeAll() {
		assetManager.dispose();
	}

	/**
	 * Remove a specific asset
	 * @param name - the filename or assetname of the asset
	 * @param type - the class of the asset
	 */
	public static void dispose(String name, Class<?> type) {
		try {
			((Disposable) assetManager.get(name, type)).dispose();
		} catch (Exception e) {
			System.out.println("No such asset exists to dispose " + type.getName() + " " + name + " ... " + e.getMessage());
		}
	}

}
