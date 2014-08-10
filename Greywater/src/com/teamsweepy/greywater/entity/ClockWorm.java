
package com.teamsweepy.greywater.entity;

import com.teamsweepy.greywater.effect.spell.Spell;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.component.events.AnimEvent;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.ui.gui.AIInventory;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamsweepy.greywater.utils.SoundManager;

public class ClockWorm extends Mob {

	//WORM STATES
	private final int STATE_AGGRESSIVE = 0;
	private final int STATE_FLEEING = 1;
	private final int STATE_LYING_IN_WAIT = 2;

	boolean pop;
	float actionTimer;
	int state = STATE_LYING_IN_WAIT;

	public ClockWorm(Level level, Mob enemy) {
		super("clockworm", 8, 8, 50, 50, 4, level, true);
		focusTarget = enemy;
		graphicsComponent.setImage(1f, "attack_south", Sprite.STILL_IMAGE);
		inventory = new AIInventory(this);
	}

	@Override
	protected void getInput() {
		if(actionTimer > 15f && state != STATE_FLEEING){
			actionTimer = 0f;
			pop = true; 
			state = STATE_FLEEING;
			graphicsComponent.setImage(.42f, "Pop", Sprite.REVERSED);
		}
		
		if (state == STATE_AGGRESSIVE) {
			if (focusTarget.getLocation().distance(getLocation()) < 200 && actionTimer > .6f) {
				actionTimer = 0f;
			}else if (!pop || actionTimer > 1f){
				graphicsComponent.setImage(1f, "ATTACK_" + currentDirection, Sprite.STILL_IMAGE);
			}
		} else if (state == STATE_LYING_IN_WAIT && focusTarget.getLocation().distance(getLocation()) < 2000 && actionTimer > 8f && !pop) {
			System.out.println("TELEPORT");
			Point2F surpriseLoc = Globals.calculateRandomLocation(focusTarget.getLocation(), world, 6);
			physicsComponent.setLocation(surpriseLoc.x, surpriseLoc.y);
			graphicsComponent.setImage(.8f, "Pop", Sprite.FORWARD);
			pop = true;
		} else if (state == STATE_FLEEING && actionTimer > 2f) {
			state = STATE_LYING_IN_WAIT;
		}
	}

	/** Draws the current sprite for this entity. */
	public void render(SpriteBatch g) {
		for (int i = 0; i < afflictingSpells.size(); i++) {
			afflictingSpells.get(i).render(g);
		}
		if (state == STATE_LYING_IN_WAIT && !pop)//don't render while underground
			return;
		Point2F p = Globals.toIsoCoord(getX(), getY());
		graphicsComponent.render(g, p.x - graphicsComponent.getImageWidth() / 2, p.y + Globals.tileImageHeight / 10);
	}

	public void tick(float deltaTime) {
		this.currentDirection = Globals.getDirectionString((Mob) this.focusTarget, this);
		actionTimer += deltaTime;
		graphicsComponent.tick(deltaTime);

		for (int i = 0; i < afflictingSpells.size(); i++) {
			Spell affliction = afflictingSpells.get(i);
			affliction.tick(deltaTime);
			if (!affliction.isActive()) {
				afflictingSpells.remove(i);
				i--;
			}
		}

		if (HP <= 0) { //if dead
			physicsComponent.stopMovement();
			attacking = false;
		} else { //if alive
			getInput();
		}
	}

	@Override
	public boolean sendInteract() {
		if (((Mob) focusTarget).isAlive() && !attacking && !pop) { //start attack by appearing near target
			attack((Mob) focusTarget);
			return true;
		}
		return false;
	}

	@Override
	public void receiveInteract(Mob interlocutor) {
	}

	@Override
	protected void attack(Mob enemy) {
		attacking = true;
		graphicsComponent.setImage(.4f, "ATTACK_" + currentDirection, Sprite.FORWARD);
		SoundManager.playSound("worm_roar.wav");
		int chanceToHit = Globals.D(20) + 4; //20 sided dice, bitch
		missing = !(chanceToHit > ((Mob) focusTarget).getReflex());
	}

	@Override
	public void executeAttack() {
		((Mob) focusTarget).changeHP(Globals.D(30));
		missing = true;
	}

	/** Reduce or increase this mob's health by the given amount (+ for damage, - for buffs/healing) */
	public void changeHP(float damage) {
		HP -= damage;
		state = STATE_FLEEING;
		graphicsComponent.setImage(.42f, "Pop", Sprite.REVERSED);
		if (HP <= 0) {
			if (inventory != null)
				inventory.dumpSlots();
			world.removeFromInteractiveList(this);
		}
	}

	/** Generic implementation for walk and attack sounds based on Animation Events */
	public void handleEvent(AnimEvent e) {
		if (e.action.contains("ATTACK") && e.ending) {
			if (attacking) {
				actionTimer = 0f;
				if (!missing)
					executeAttack();
				attacking = false;
				graphicsComponent.setImage(.6f, "ATTACK_" + currentDirection, Sprite.REVERSED);
			} else {
				graphicsComponent.setImage(1f, "ATTACK_" + currentDirection, Sprite.STILL_IMAGE);
				graphicsComponent.changeSeriesPosition(3, Sprite.STILL_IMAGE);
			}
		} else if (e.action.contains("POP") && e.ending && pop) {
			System.out.println("ENDING");
			if (state == STATE_LYING_IN_WAIT) {
				state = STATE_AGGRESSIVE;
				graphicsComponent.setImage(1f, "ATTACK_" + currentDirection, Sprite.STILL_IMAGE);
				graphicsComponent.changeSeriesPosition(3, Sprite.STILL_IMAGE);
			} else {
				state = STATE_FLEEING;
			}
			pop = false;
			attacking = false;
		}
	}

	/** Finds if given point is within current image's bounding box, meant for Ziga to override for items */
	protected boolean didPointHitImage(Point2F point) {
		System.out.println("well did it?");
		if (state == STATE_LYING_IN_WAIT)
			return false;
		Point2F p = Globals.toIsoCoord(getX(), getY());
		System.out.println(graphicsComponent.getImageRectangleAtOrigin(p.x + mainCamera.xOffsetAggregate, p.y + mainCamera.yOffsetAggregate).contains(point.x, point.y));
		return graphicsComponent.getImageRectangleAtOrigin(p.x + mainCamera.xOffsetAggregate, p.y + mainCamera.yOffsetAggregate).contains(point.x, point.y);
	}
}
