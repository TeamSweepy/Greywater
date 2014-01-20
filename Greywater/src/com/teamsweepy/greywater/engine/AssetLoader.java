package com.teamsweepy.greywater.engine;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;

public class AssetLoader {
	//wanted to name it assMan, didn't, because I'm a grown up
	public static AssetManager assetManager;
	

	public void init(){
		assetManager = new AssetManager();
	}
	
	public void loadSet(String filePath){
		//TODO parse text file/json object that specifies tilesets/enemies/etc
	}

}
