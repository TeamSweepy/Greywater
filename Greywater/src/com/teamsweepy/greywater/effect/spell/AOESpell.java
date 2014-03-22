
package com.teamsweepy.greywater.effect.spell;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.entity.level.Tile;
import com.teamsweepy.greywater.math.Point2F;

import java.util.List;


public class AOESpell extends Spell {

	List<Tile> tiles;

	public AOESpell(String effect, List<Tile> tiles, Tile centralTile, float durationSeconds, float damagePerSecond, Level world) {
		super(effect, durationSeconds, damagePerSecond, world);
		Point2F p = Globals.toIsoCoord(centralTile.getX(), centralTile.getY());
		updatePosition(p.x, p.y + 10);
	}

	public void tick(float deltaTime) {
		super.tick(deltaTime);
		if (duration > 0) {
			//TODO get all mobs on affected tiles
		}
	}

}
