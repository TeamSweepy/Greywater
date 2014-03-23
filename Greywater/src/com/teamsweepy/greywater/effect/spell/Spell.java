
package com.teamsweepy.greywater.effect.spell;

import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class Spell {

	protected ParticleEffect spellEffect;
	protected float duration;
	protected float damagePerSecond;
	protected Level world;

	public Spell(String effect, float repeatDurationSeconds, float damagePerSecond, Level world) {
		spellEffect = new ParticleEffect((ParticleEffect)AssetLoader.getAsset(ParticleEffect.class, effect));
		this.damagePerSecond = damagePerSecond;
		this.world = world;
		spellEffect.start();
		duration = repeatDurationSeconds;
	}

	public void render(SpriteBatch g) {
		spellEffect.draw(g);
	}

	public void tick(float deltatime) {
		spellEffect.update(deltatime);
		duration -= deltatime;
		
		if (duration > 0 && spellEffect.isComplete()) {
			spellEffect.start();
		}
	}

	public void stop() {
		spellEffect.setDuration(0);
		duration = 0;
	}
	
	public boolean isActive(){
		return (duration > 0 || !spellEffect.isComplete());
	}

	public void updatePosition(Point2F newLoc) {
		spellEffect.setPosition(newLoc.x, newLoc.y);
		
	}

	public void updatePosition(float x, float y) {
		spellEffect.setPosition(x, y);
	}


}
