
package com.teamsweepy.greywater.entity;

import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.entity.component.Entity;
import com.teamsweepy.greywater.entity.component.Sprite;
import com.teamsweepy.greywater.entity.component.events.EscortEvent;
import com.teamsweepy.greywater.entity.item.Item;
import com.teamsweepy.greywater.entity.item.weapons.Bomb;
import com.teamsweepy.greywater.entity.item.weapons.Fist;
import com.teamsweepy.greywater.entity.item.weapons.TazerWrench;
import com.teamsweepy.greywater.entity.item.weapons.Weapon;
import com.teamsweepy.greywater.entity.item.weapons.Wrench;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.entity.level.Tile;
import com.teamsweepy.greywater.math.Point2F;
import com.teamsweepy.greywater.math.Point2I;
import com.teamsweepy.greywater.ui.gui.subgui.ProgressBarCircular;
import com.teamsweepy.greywater.utils.SoundManager;

import java.util.ArrayList;

public class Player extends Mob {

	private static Point2F mouseLocation;
	private static boolean mouseClicked;
	private static Player localPlayer;

	private ProgressBarCircular healthBar;
	private ProgressBarCircular manaBar;
	public Sprite selection;

    // Used so we can reset the hover state
    private Item last_hovered_item;

	private Fist fist; // default weapon

	public static Player getLocalPlayer() {
		return localPlayer;
	}

	/**
	 * Creates a new player standing in the center of the tile specified.
	 * 
	 * @param x - Tile X Position, not objective position
	 * @param y - Tile Y Position, not objective position
	 */
	public static Player initLocalPlayer(float x, float y, Level level) {
		if (localPlayer == null)
			localPlayer = new Player(x, y, level);
		return localPlayer;
	}

	/**
	 * Creates a new player standing in the center of the tile specified.
	 * 
	 * @param x - Tile X Position, not objective position
	 * @param y - Tile Y Position, not objective position
	 */
	private Player(float x, float y, Level level) {
		super("Tavish", x, y, 35, 35, 1.75f, level, true);
		currentDirection = "South";
		this.walkCycleDuration = .5f;
		killList = new ArrayList<Entity>();
		graphicsComponent.setImage(3f, "Walk_South", Sprite.LOOP);
		fist = new Fist();
		armorRating = 14;

	}

	@Override
	public void tick(float deltaTime) {
		super.tick(deltaTime);
		Weapon equippedWeapon = inventory.getWeapon();
		if (equippedWeapon != null) {
			if(equippedWeapon.getClass() == Wrench.class || equippedWeapon.getClass() == TazerWrench.class)
				weapon = "Wrench_";
			if(equippedWeapon instanceof Bomb)
				weapon = "Bomb_";
		} else{
			weapon = "";
		}

		if (healthBar != null && manaBar != null) {
			healthBar.setValue(HP);
			manaBar.setValue(inventory.getCharge());
		}


        // Are we hovering on an object?
//        Entity interacted = world.getClickedEntity(mouseLocation, this);
//        if(interacted instanceof Item) {
//            if(interacted == last_hovered_item) {
//                last_hovered_item.hover(true);
//            } else {
//                if(last_hovered_item != null) {
//                    last_hovered_item.hover(false);
//                }
//
//                last_hovered_item = (Item) interacted;
//                last_hovered_item.hover(true);
//            }
//        }
	}

	@Override
	protected void getInput() {
		// PATHFINDING CODE
		if (mouseClicked) {
			mouseClicked = false;
			if (attacking || sendInteract()) // no need to walk if you're fighting/talking
				return;
            Point2I startTile;
			if (!physicsComponent.isMoving())
				startTile = Globals.toTileIndices(getLocation().x, getLocation().y);
			else
				startTile = Globals.toTileIndices(physicsComponent.destination);

			Point2F objectiveClick = Globals.toNormalCoord(mouseLocation.x, mouseLocation.y);
			Point2I clickedTile = Globals.toTileIndices(objectiveClick.x, objectiveClick.y);

//			System.out.println("Player starts at " + startTile);
//			System.out.println("Clicked to move to " + clickedTile);

			pather.createPath(startTile, clickedTile);
            Point2I newPoint = pather.getNextStep();
			if (newPoint != null) {
				Point2F newLoc = Globals.toNormalCoordFromTileIndices(newPoint.x, newPoint.y);
				physicsComponent.moveTo(newLoc.x, newLoc.y);
			}
		} else if (!physicsComponent.isMoving()) {  // if no recent click, continue along pre-established path
            Point2I newPoint = pather.getNextStep();

			if (newPoint != null) {
				Point2F newLoc = Globals.toNormalCoordFromTileIndices(newPoint.x, newPoint.y);
				physicsComponent.moveTo(newLoc.x, newLoc.y);
				if (!followerList.isEmpty()) {
					for (Mob mob : followerList) {
						System.out.println(mob);
						fireEvent(new EscortEvent(this, mob));
					}
				}
			}
		}
	} // END PATHFINDING CODE


	@Override
	public boolean sendInteract() {

		Entity interacted = world.getClickedEntity(mouseLocation, this);
		focusTarget = null;
		if (interacted == null)
			return false;

		if (interacted instanceof Mob) { // deal with mobs
			Mob interactedMob = (Mob) interacted;
			if (interactedMob.isAlive() && !interactedMob.friendly) { // attack the living enemy
				focusTarget = interactedMob;
				attack(interactedMob);
			} else if (interactedMob.friendly) { // interact with friends
				if (interactedMob.getLocation().distance(getLocation()) > getWidth() * 3.5)
					return false;

//				physicsComponent.stopMovement();
//				pather.reset();
//				this.currentDirection = Globals.getDirectionString(interactedMob, this); // face target
//				interactedMob.receiveInteract(this);
			} else { // clicked a dead guy
				return false;
			}
		}// end mob interaction

		if (interacted instanceof Item) { // pickup loot
			if (interacted.getLocation().distance(getLocation()) < getWidth() * 2) {
				if (inventory.hasSpace()) {
					inventory.addItem((Item) interacted);
					getLevel().removeFloorItem((Item) interacted);
					((Item) interacted).pickup();
				} else {
					getLevel().removeFloorItem((Item) interacted);
					((Item) interacted).throwOnGround(Globals.calculateRandomLocation(this.getLocation(), this.getLevel(), -.1f), this);
				}
			} else {
				pather.createPath(Globals.toTileIndices(this.getLocation()), Globals.toTileIndices(interacted.getLocation()));
				Point2I newPoint = pather.getNextStep();
				newPoint = pather.getNextStep();

				if (newPoint != null) {
					Point2F newLoc = Globals.toNormalCoordFromTileIndices(newPoint.x, newPoint.y);
					physicsComponent.moveTo(newLoc.x, newLoc.y);
				}
			}
		}

		if (interacted.getClass() == Tile.class) {
			// someday door logic will go here
		}
		return true;
	}

	@Override
	protected void attack(Mob enemy) {
		if (enemy == null || attacking) {
			return;
		}

		Weapon equippedWeapon = inventory.getWeapon();
		if (equippedWeapon == null) {
			equippedWeapon = fist;
		}

		focusTarget = enemy;
		boolean visible = this.canSeeTarget();
		if (!visible) {
			focusTarget = null;
			return;
		}

		if (enemy.getLocation().distance(getLocation()) > equippedWeapon.getRange() && visible) { // if cant reach
			pather.createPath(Globals.toTileIndices(this.getLocation()), Globals.toTileIndices(enemy.getLocation()));
            Point2I newPoint = pather.getNextStep();
			if (newPoint != null) {
				Point2F newLoc = Globals.toNormalCoordFromTileIndices(newPoint.x, newPoint.y);
				physicsComponent.moveTo(newLoc.x, newLoc.y);
			}
			return;
		}
//		((Sound)AssetLoader.getAsset(Sound.class, )).play();

        String sound_file = "TAVISH_ATTACK_" + (Globals.rand.nextInt(3) + 1)+ ".wav";
        SoundManager.playSound(sound_file);

		missing = !equippedWeapon.swing(this, enemy);
		physicsComponent.stopMovement();
		pather.reset();

		attacking = true;
		this.currentDirection = Globals.getDirectionString(enemy, this);
	}

	/** Sets local player input variables. Used as a callback. */
	public static void handleInput(Point2F screenLocation, boolean clicked, int keyCode) {
		mouseClicked = clicked;
		mouseLocation = screenLocation;
		if (mouseLocation != null || keyCode != -69) {
			return; // TODO deal with key input when needed
		}
	}

	public void setBars(ProgressBarCircular hp, ProgressBarCircular mana) {
		this.healthBar = hp;
		this.manaBar = mana;
	}

	@Override
	public void executeAttack() {
		Weapon equippedWeapon = inventory.getWeapon();
		if (equippedWeapon == null) {
			equippedWeapon = fist;
		}
		equippedWeapon.attack((Mob) this, (Mob) focusTarget);
		missing = true;
	}
	

	@Override
	public void receiveInteract(Mob interlocutor) {}

}
