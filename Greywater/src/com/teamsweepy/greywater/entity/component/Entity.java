/**
 * Used for objects which need physical representation in game (Players, Enemies, Tiles, Walls)
 * 
 * Hold the graphics and collision aspects of the object. ALL objects in game must inherit this class. Acts as a wrapper for two central
 * components- graphics and physics. Instead of the main game loop having to track two objects per character, it only has to track one, and
 * by calling it's tick/update and render methods it tracks the two disparate aspects (graphics and physics) and makes sure shit gets done.
 * This is much easier.
 * 
 * *********** IMPORTANT *************
 * 
 * The graphics component (sprite) is an isometric image, it is a skewed square, rotated 45 degrees. An iso-tile has width:height of 2:1,
 * giving it the illusion of depth. Unfortunately, that makes programming super hard, and I am lazy. Far more powerful and understandable is
 * to represent the tiles as square in memory, as if the game were a top-down 2D style game. This means that all objects are squares or
 * rectangles, which makes the math easy and render sorting easier. The 2D is represented through the physics component (hitbox) and the iso
 * aspect is the graphics component. The middleman, which holds these two, is the Entity (or subclass of same) which makes them play nice,
 * see below.
 * 
 * Only when an item is being rendered is it treated as isometric and converted from boring 2D to exciting, sexy isometric co-ordinates.
 * Because all images undergo the same transformation, the isometric image is a fair and accurate representation of the 2D flat "true" world
 * in memory.
 * 
 * This class is that middleman that takes the 2D square world (flatspace) and uses fancible math things to make its x and y data into
 * isometric fancy x and y data, which is then used to control rendering by the graphics component.
 * 
 * Inspiration for this system is properly due to Roger Englebert ("From Tile to Isometric")
 * http://www.rengelbert.com/tutorial.php?id=76&show_all=true
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Barnes
 */

package com.teamsweepy.greywater.entity.component;

import com.teamsweepy.greywater.effect.spell.Spell;
import com.teamsweepy.greywater.engine.Camera;
import com.teamsweepy.greywater.engine.Globals;
import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.teamsweepy.greywater.math.Point2I;

import java.util.ArrayList;
import java.util.List;


public abstract class Entity {

	/* *** All entities have a physics component for collisions and position, and a graphics component to render on screen *** */
	protected Hitbox physicsComponent; // hitbox
	protected Sprite graphicsComponent; // sprite
	protected Camera mainCamera;
	
	protected List<Spell> afflictingSpells;

	public Entity() {
		mainCamera = Camera.getDefault();
		afflictingSpells = new ArrayList<Spell>();
	}

	/** Ticks components logic (graphics and physics) */
	public void tick(float deltaTime) {
		physicsComponent.tick(deltaTime); // update components
		graphicsComponent.tick(deltaTime);
		for(int i = 0; i < afflictingSpells.size(); i++){
			Spell affliction = afflictingSpells.get(i);
			affliction.tick(deltaTime);
			if(!affliction.isActive()){
				afflictingSpells.remove(i);
				i--;
			}
		}
	}

	/** Draws the current sprite for this entity. */
	public void render(SpriteBatch g) {
		for(int i = 0; i < afflictingSpells.size(); i++){
			afflictingSpells.get(i).render(g);
			
		}
		Point2F p = Globals.toIsoCoord(getX(), getY());
		graphicsComponent.render(g, p.x, p.y);
	}
	
	public void addSpell(Spell s){
		afflictingSpells.add(s);
	}

	/** @return the physicsComponent for filthy outsiders */
	public Hitbox getPhysics() {
		return physicsComponent;
	}

	/** @return the Rectangle used for collision detection */
	public Rectangle getHitbox() {
		return physicsComponent.getHitBox();
	}

	/** Returns x location in objective coordinates */
	public float getX() {
		return physicsComponent.getHitBox().x;
	}

	/** Returns y location in objective coordinates */
	public float getY() {
		return physicsComponent.getHitBox().y;
	}

	/** Returns hitbox width */
	public float getWidth() {
		return physicsComponent.getHitBox().width;
	}

	/** Returns hitbox height */
	public float getHeight() {
		return physicsComponent.getHitBox().height;
	}

	/** Returns location in objective coordinates */
	public Point2F getLocation() {
		return new Point2F(getX(), getY());
	}

	public Point2I getTileLocation() {
		return Globals.toTileIndices(getLocation());
	}

	/** Used for hitbox interaction, meant to indicate if two entities collide in objective coordinate space */
	public boolean checkPhysicalIntersection(Rectangle intersector) {
		return intersector.overlaps(physicsComponent.getHitBox());
	}

	/** Used for click-interaction, meant to indicate if an entity's sprite was clicked */
	public boolean checkClickedInteraction(Point2F point) {
		return didPointHitImage(point);
	}

	/** Finds if given point is within current image's bounding box, meant for Ziga to override for items */
	protected boolean didPointHitImage(Point2F point) {
		Point2F p = Globals.toIsoCoord(getX(), getY());
		return graphicsComponent.getImageRectangleAtOrigin(p.x + mainCamera.xOffsetAggregate, p.y + mainCamera.yOffsetAggregate).contains(point.x, point.y);
	}



	/** @return The approximate depth in Z space of the entity. Used for render sorting. */
	public float getDepth() {
		float x = physicsComponent.getHitBox().width * .5f + getX();
		float y = physicsComponent.getHitBox().height * .5f + getY();
		return (x - y) * .866f; //.866 is cos of 30 degrees, which is the isometric transform
	}
}
