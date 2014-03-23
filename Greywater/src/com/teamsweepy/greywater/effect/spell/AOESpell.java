
package com.teamsweepy.greywater.effect.spell;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.Mob;
import com.teamsweepy.greywater.entity.component.events.KillEvent;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.entity.level.Tile;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;


public class AOESpell extends Spell {

	List<Tile> tiles;
	List<Rectangle> hitBoxes;
	List<Mob> mobs;
	Mob creator;

	public AOESpell(String effect, List<Tile> tiles, Tile centralTile, float durationSeconds, float damagePerSecond, Level world, Mob creator) {
		super(effect, durationSeconds, damagePerSecond, world);
		Point2F p = Globals.toIsoCoord(centralTile.getX(), centralTile.getY());
		updatePosition(p.x, p.y + 10);
		hitBoxes = new ArrayList<Rectangle>();
		for (Tile t : tiles) {
			hitBoxes.add(t.getHitbox());
		}
		this.creator = creator;
		mobs = world.getCollidedMobs(hitBoxes);
	}

	public void tick(float deltaTime) {
		super.tick(deltaTime);

		if (duration > 0 || super.isActive()) {
			for (Mob m : mobs) {
				if (m.isAlive()){
					m.changeHP(damagePerSecond * deltaTime);
					if(!m.isAlive()){
						creator.fireEvent(new KillEvent(creator, m));
						creator.getKillList().add(m);
					}
				}
			}
		}
	}

}
