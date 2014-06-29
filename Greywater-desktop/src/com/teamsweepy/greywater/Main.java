/**
 * This is just an entry point for desktop applications, it sets the window 
 * to the user's current resolution and makes the window fullscreen and then launches the game.
 * 
 * Important note - this is what you need to run in Eclipse to make the project actually go.
 */

package com.teamsweepy.greywater;

import com.teamsweepy.greywater.engine.Engine;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
//import net.biodiscus.debug.Debug;
//import net.biodiscus.debug.singleton.ClassesLoaded;

public class Main {

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Greywater";
		cfg.useGL20 = false;
		cfg.resizable = false;
        cfg.width = 1600/2;
        cfg.height = 900/2;

		//set fullscreen and match current resolution
//		cfg.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
		new LwjglApplication(new Engine(), cfg);
	}
}
