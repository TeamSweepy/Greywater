
package com.teamsweepy.greywater.entity.level;

import java.util.ArrayList;

import com.teamsweepy.greywater.entity.Player;

/**
 * Class that handles Levels and makes sure all levels are updated if there are players in them
 * */

public class LevelHandler {

	public static ArrayList<Level> allLevels = new ArrayList<Level>();

	/**
	 * Create a new LevelHandler and init all levels
	 * */
	public LevelHandler() {
		initAllLevels();
	}

	/**
	 * Init all the levels and add them into the allLevels arraylist
	 * */
	private void initAllLevels() {
		Level town = new Town("data/map/Greywater.tmx");
		allLevels.add(town);
		Level dungeon = new Dungeon("data/map/Dungeon.tmx");
		allLevels.add(dungeon);
	}

	/**
	 * Tick all the levels
	 * @param delta, time passed betwwen this and the previous tick
	 * */
	public void tick(float delta) {
		for (Level level : allLevels) {
			level.tick(delta);
		}
	}
	
	public static int getFreePlayerID() {
		int x = 0;
		boolean free = true;
		do {
			x++;
			free = true;
			for (Level level : allLevels) {
				for (Player p : level.players) {
					if (p.ID == x) {
						free = false;
						break;
					}
				}
			}


		} while (!free);
		System.out.println("[SERVER] Gave a client ID : " + x);
		return x;
	}


	/**
	 * Return a Level with the ID that was passed
	 * @param ID, id of the level, id-s are specified in Level class
	 * */
	public static Level getLevel(int ID) {
		return allLevels.get(ID);
	}

	/**
	 * Returns all levels
	 * */
	public static ArrayList<Level> getAllLevels() {
		return allLevels;
	}

}
