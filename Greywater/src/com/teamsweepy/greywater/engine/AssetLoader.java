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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AssetLoader {

	//wanted to name it assMan, didn't, because I'm a grown up
	private final static String ASSET_FOLDER = "data/";
	
	private static AssetManager assetManager;
	private static ArrayList<String> processList; //list of things to process and place into createdAssetMap
	private static HashMap<String, Object> createdAssetMap;


	/**
	 * Must be called before any other methods, initializes the asset manager component
	 */
	public static void init() {
		assetManager = new AssetManager();
		processList = new ArrayList<String>();
		createdAssetMap = new HashMap<String, Object>();
		parseINI("Assets.ini"); //load common assets such as the HUD and menu
	}

	/**
	 * Loads all files listed in the given ini file. Note that files are expect to be of the format:
	 * LOAD; Filepath; Type
	 * or
	 * Type; Filepath; Assetname
	 * 
	 * Loaded assets directly from file are of the first format (Textures, sounds, etc)
	 * Composite assets that are composed of multiple load assets (such as Animations that use multiple textureregions) are the seconds format.
	 * 
	 * @param iniName
	 */
	public static void parseINI(String iniName) {
		try {
			FileHandle setFile = Gdx.files.internal(ASSET_FOLDER + iniName);
			BufferedReader br = setFile.reader(64);// new BufferedReader(setReader);

			while (br.ready()) {
				String currentLine = br.readLine();

				if (currentLine.startsWith("//") || currentLine.length() < 6) continue;

				String action = currentLine.substring(0, currentLine.indexOf(";")).trim();
				String fileName = ASSET_FOLDER + currentLine.substring(currentLine.indexOf(";") + 1, currentLine.lastIndexOf(";")).trim();
				String type = currentLine.substring(currentLine.lastIndexOf(";") + 1).trim();

				if (action.equalsIgnoreCase("LOAD")) {
					assetManager.load(fileName, getAssetType(type));
				} else {
					processList.add(currentLine); //process "composite" assets later
				}
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
	 * @return percent complete as float.
	 */
	public static float tick() {
		assetManager.update();
		float prog = assetManager.getProgress();
		if (prog >= 1 && processList.size() > 0) {
			processAll(); //set up composite assets when all "primitive" assets are loaded
		}
		return prog;
	}

	/**
	 * Getter for assets for use in game.
	 * @param type - class of the asset type
	 * @param name - name of the asset to get
	 * @return The asset requested or null
	 */
	public static Object getAsset(Class<?> type, String name) {
		if (type.equals(Animation.class)) {
			return createdAssetMap.get(name);
		} else {
			return assetManager.get(name, type);

		}
	}

	/**
	 * Process "created assets" that are composed of other assets loaded by the AssetManager. These are things like Animations which are
	 * composed of multiple Textures or TextureRegions
	 */
	private static void processAll() {
		for (String str : processList) {
			
			//parse out sections of semicolon delimited list
			String action = str.substring(0, str.indexOf(";")).trim();
			String fileName = str.substring(str.indexOf(";") + 1, str.lastIndexOf(";")).trim();
			String assetName = str.substring(str.lastIndexOf(";") + 1).trim();

			if (action.equalsIgnoreCase("Animation")) { //currently only composite asset is animations
				TextureAtlas atlas = assetManager.get(ASSET_FOLDER + fileName, TextureAtlas.class);
				Animation anim = new Animation(1f, atlas.findRegions(assetName), Animation.LOOP);
				createdAssetMap.put(assetName, anim);
			}
		}
		processList.clear(); //when all are processed, clear the list
	}


	/**
	 * Used to parse the type from INI files from a string into a proper type object.
	 * 
	 * @param type - name of the type as string from INI
	 * @return - type object
	 * @throws Exception - errors on no such type. Spelling counts!
	 */
	private static Class<?> getAssetType(String type) throws Exception {
		if (type.equalsIgnoreCase("Texture")) return Texture.class;
		if (type.equalsIgnoreCase("BitmapFont")) return BitmapFont.class;
		if (type.equalsIgnoreCase("TextureAtlas")) return TextureAtlas.class;
		if (type.equalsIgnoreCase("Music")) return Music.class;
		if (type.equalsIgnoreCase("Sound")) return Sound.class;
		if (type.equalsIgnoreCase("ParticleEffect")) return ParticleEffect.class;

		throw new Exception("Invalid Class Specified in AssetLoader! Invalid type - " + type);
	}

}
