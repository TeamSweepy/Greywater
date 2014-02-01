/**
 * Used for objects which need physical representation in game (Players, Enemies, Tiles, Walls)
 * 
 * Hold the graphics and collision aspects of the object. ALL objects in game must inherit this class. Acts as a wrapper for two central
 * components- graphics and physics. Instead of the main game loop having to track two objects per character, it only has to track one, and
 * by calling it's tick/update and render methods it tracks the two disparate aspects (graphics and physics) and makes sure shit gets done.
 * This is much easier.
 * 
 * 
 * 
 * *********** IMPORTANT ************************ The graphics component (sprite) is an isometric image, it is a skewed square, rotated 45
 * degrees. An iso-tile has width:height of 2:1, giving it the illusion of depth. Unfortunately, that makes programming super hard, and I am
 * lazy. Far more powerful and understandable is to represent the tiles as square in memory, as if the game were a top-down 2D style game.
 * This means that all objects are squares or rectangles, which makes the math easy and render sorting easier. The 2D is represented through
 * the physics component (hitbox) and the iso aspect is the graphics component. The middleman, which holds these two, is the Entity (or
 * subclass thereof, such as this Tile) which makes them play nice, see below.
 * 
 * Only when an item is being rendered is it treated as isometric - the square co-ordinates are run through the math method - getIsoCoords()
 * and converted from boring 2D to exciting, sexy isometric co-ordinates. Because all images undergo the same transformation, the isometric
 * image is a fair and accurate representation of the 2D flat "true" world in memory.
 * 
 * This class is that middleman that takes the 2D square world (flatspace) and uses fancible math things to make its x and y data into
 * isometric fancy x and y data, which is then used to control rendering by the graphics component.
 * 
 * Inspiration for this system is properly due to Roger Englebert ("From Tile to Isometric")
 * http://www.rengelbert.com/tutorial.php?id=76&show_all=true
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

package com.teamsweepy.greywater.entities.components;

import com.teamsweepy.greywater.engine.Camera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.awt.geom.Point2D;


public abstract class Entity {

	/* *** All entities have a physics component for collisions and position, and a graphics component to render on screen *** */
	protected Tangible physicsComponent; // hitbox
	protected Sprite graphicsComponent; // sprite
    protected Camera mainCamera;

    public Entity()
    {
        mainCamera = Camera.getDefault();
    }



	/**
	 * Ticks components (graphics and physics)
	 */
	public void tick(float deltaTime) {
		physicsComponent.tick(deltaTime); // update components
		graphicsComponent.tick(deltaTime);
	}

	/**
	 * Draws the current sprite for this entity.
	 * @param g - Graphics object
	 */
	public void render(SpriteBatch g) {
		graphicsComponent.render(g, getX() - mainCamera.xOffset, getY() - mainCamera.yOffset);//TODO replace with isometric transforms
	}

	/**
	 * @return the physicsComponent for filthy outsiders
	 */
	public Tangible getPhysics() {
		return physicsComponent;
	}

	/**
	 * @return the Shape used for collision detection
	 */
	public Rectangle getPhysicsShape() {
		return physicsComponent.getHitBox();
	}

	public float getX() {
		return physicsComponent.getHitBox().x;
	}

	public float getY() {
		return physicsComponent.getHitBox().y;
	}

	public Point2D getLocation() {
		return new Point2D.Float(getX(), getY());
	}

	/**
	 * @return The approximate depth in Z space of the entity. Used for render sorting.
	 */
	public double getDepth() {
		float x = physicsComponent.getHitBox().width * .5f + getX();
		float y = physicsComponent.getHitBox().height * .5f + getY();
		return (x + y) * .866; //.866 is cos of 30 degrees, which is the isometric transform
	}
}
