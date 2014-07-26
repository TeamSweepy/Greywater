
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

	boolean pop;
	boolean recede;
	float timeSinceAttack;

	public ClockWorm(Level level, Mob enemy) {
		super("clockworm", 5, 5, 50, 50, 4, level, true);
		focusTarget = enemy;
		graphicsComponent.setImage(1f, "attack_south", Sprite.STILL_IMAGE);
		inventory = new AIInventory(this);
	}

	@Override
	protected void getInput() {
		if (focusTarget.getLocation().distance(getLocation()) < 2000) {
			if (timeSinceAttack < 7.5 && !pop && !attacking && !recede) { //if it has been less than 7.5 seconds since attack, telegraph
				System.out.println("pop");
				Point2F surpriseLoc = Globals.calculateRandomLocation(focusTarget.getLocation(), world, 6);
				physicsComponent.setLocation(surpriseLoc.x, surpriseLoc.y);
				pop = true;
				graphicsComponent.setImage(.7f, "Pop", Sprite.FORWARD);
				return;
			} else if (timeSinceAttack >= 7.5) //otherwise, attack
				sendInteract();
		}
	}

	/** Draws the current sprite for this entity. */
	public void render(SpriteBatch g) {
		for (int i = 0; i < afflictingSpells.size(); i++) {
			afflictingSpells.get(i).render(g);
		}
		if (!pop && !attacking && !recede)
			return;
		Point2F p = Globals.toIsoCoord(getX(), getY());
		graphicsComponent.render(g, p.x - graphicsComponent.getImageWidth() / 2, p.y + Globals.tileImageHeight / 10);
	}

	public void tick(float deltaTime) {
		timeSinceAttack += deltaTime;
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
			if (attacking && !pop)
				graphicsComponent.setImage(.4f, "Attack_" + currentDirection, Sprite.FORWARD); // TODO if multiple attacks clicked, pingpong
		}
	}

	@Override
	public boolean sendInteract() {
		if (((Mob) focusTarget).isAlive() && !attacking && !pop && !recede) { //start attack by appearing near target
			Point2F surpriseLoc = Globals.calculateRandomLocation(focusTarget.getLocation(), world, 1);
			physicsComponent.setLocation(surpriseLoc.x, surpriseLoc.y);
			attack((Mob) focusTarget);
			return true;
		}
		return false;
	}

	@Override
	public void receiveInteract(Mob interlocutor) {}

	@Override
	protected void attack(Mob enemy) {
		this.currentDirection = Globals.getDirectionString(enemy, this);
		pop = true;
		attacking = true;
		graphicsComponent.setImage(.4f, "Pop", Sprite.FORWARD);
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
		if (HP <= 0) {
			graphicsComponent.setImage(.42f, "Pop", Sprite.REVERSED);
			if (inventory != null)
				inventory.dumpSlots();
			world.removeFromInteractiveList(this);
		}
	}

	/** Generic implementation for walk and attack sounds based on Animation Events */
	public void handleEvent(AnimEvent e) {
		System.out.println(e.action + " " + e.beginning + " " + e.ending);

		if (e.action.contains("ATTACK") && e.ending) {
			timeSinceAttack = 0f;
			if (!missing)
				executeAttack();
			recede = true;
			attacking = false;
			graphicsComponent.setImage(.6f, "POP", Sprite.REVERSED);
		} else if (e.action.contains("POP") && e.ending && pop) {
			if (!attacking){
				graphicsComponent.setImage(.6f, "POP", Sprite.REVERSED);
				recede = true;
			}
			pop = false;
		} else if (e.action.contains("POP") && e.ending && recede && !pop) {
			pop = false;
			recede = false;
			graphicsComponent.stopAnimating();
		}
	}

	/** Finds if given point is within current image's bounding box, meant for Ziga to override for items */
	protected boolean didPointHitImage(Point2F point) {
		if (!attacking && !pop && !recede)
			return false;
		Point2F p = Globals.toIsoCoord(getX(), getY());
		return graphicsComponent.getImageRectangleAtOrigin(p.x + mainCamera.xOffsetAggregate, p.y + mainCamera.yOffsetAggregate).contains(point.x, point.y);
	}
}
