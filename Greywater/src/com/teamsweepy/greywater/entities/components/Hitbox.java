/**
 * Used to hold data about an Entity's hitbox, including position, speed, and accessor methods. In reality, this class is the Entity. The
 * Entity interacts with the world entirely through this class because it is the 2D representation.
 * 
 * *********** IMPORTANT ************************
 * 
 * The graphics component (sprite) is the isometric image that is rendered to the screen, but isometric math is unnecessarily intense, and
 * wasteful to do when cheaper, more effective methods are available. To that end, the world is ACTUALLY a top down rectangular world, and
 * is merely represented isometrically.
 * 
 * This class is the 2D physics component (hitbox) and the iso aspect is the graphics component. This class controls the speed, movement,
 * location, and collision of the object as if it were top-down, and then that data is taken by the entity class and math'd into isometric
 * silliness.
 * 
 * see- Roger Engelbert http://www.rengelbert.com/tutorial.php?id=76&show_all=true
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Barnes
 */

package com.teamsweepy.greywater.entities.components;

import com.badlogic.gdx.math.Rectangle;

import java.awt.geom.Point2D;

public class Hitbox {

	/* ************* POSITIONAL VARIABLES ************** */
	public Point2D destination;
	private float speed = 0f; //per second
	private float xDelta = 0f;
	private float yDelta = 0f;

	// physics object
	private Rectangle hitBox;

	/**
	 * Constructor. Sets up location and square hit space
	 * @param x- X co-ordinate of hitBox
	 * @param y - Y co-ordinate of hitBox
	 * @param spd - how many units per update the sprite moves
	 * @param hitWidth- width of the hitBox
	 * @param hitHeight- height of hitBox
	 */
	public Hitbox(float x, float y, int hitWidth, int hitHeight, float spd) {
		destination = new Point2D.Float(x, y);
		this.speed = spd;
		hitBox = new Rectangle(x, y, hitWidth, hitHeight);

	}

	/**
	 * Used to move the object if it has a destination. If not, does nothing.
	 */
	public void tick(float deltaTime) {
		//get direction of movement * speed
		xDelta = Integer.signum((int) (destination.getX() - hitBox.getX())) * speed * deltaTime;
		yDelta = Integer.signum((int) (destination.getY() - hitBox.getY())) * speed * deltaTime;

		if (xDelta != 0 || yDelta != 0) setLocation(xDelta + hitBox.getX(), yDelta + hitBox.getY());

		xDelta = 0; //clear out for next cycle
		yDelta = 0;
	}

	/**
	 * Updates the hitspace (teleports)
	 * 
	 * @param x- new x co-ordinate
	 * @param y- new y co-ordinate
	 */
	public void setLocation(float x, float y) {
		hitBox.set(x, y, hitBox.width, hitBox.height);
	}

	/**
	 * Sets character destination to X,Y
	 * @param x - where to go in the x direction
	 * @param y- where to go in the y direction
	 */
	public void moveTo(float x, float y) {
		destination.setLocation(x, y);
	}

	/**
	 * Stops all movement immediately, by setting destination to current location
	 */
	public void stopMovement() {
		destination.setLocation(hitBox.x, hitBox.y);
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public boolean isMoving() {
		//check to see if destination = hitbox location
		if ((destination.getX() - hitBox.x) == 0. && (destination.getX() - hitBox.x) == 0.)
			if ((destination.getY() - hitBox.y == 0.) && (destination.getY() - hitBox.y) == 0.) return false;

		return true;
	}
}
