/**
 * AssetLoader is essentially a wrapper for the LibGDX AssetManager. It adds some convenience methods such as parsing asset-sets.
 * Basically, these methods could be written elsewhere, but they will get used frequently and there's no point
 * in cluttering up other classes with this stuff.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 */
package com.teamsweepy.greywater.engine;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AssetLoader {

	//wanted to name it assMan, didn't, because I'm a grown up
	public static AssetManager assetManager;
	private final static String ASSET_FOLDER = "data/";


	/**
	 * Must be called before any other methods, initializes the asset manager component
	 */
	public static void init() {
		assetManager = new AssetManager();
	}


	/**
	 * Parses file that defines resources needed for a given level, then loads those assets.
	 * 
	 * @param setName - filename (including extension) of the file that defines the resource set.
	 */
	public static void loadSet(String setName) {
		try {
			FileHandle setFile = new FileHandle(ASSET_FOLDER + setName);
			FileReader setReader = new FileReader(setFile.file());
			BufferedReader br = new BufferedReader(setReader);

			while (br.ready()) {
				//TODO create "asset" file for characters, items, tiles, etc to minimize loaded assets.
				//Parse that file
				//load assets
			}

			setReader.close();
			br.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Critical File Missing from " + setName + ". " + ex.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Could not close a reader " + e.getMessage());
			System.exit(1);
		}
	}



}
