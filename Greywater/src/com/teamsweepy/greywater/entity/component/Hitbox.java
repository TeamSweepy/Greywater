/**
 * Used to hold data about an Entity's hitbox, including position, speed, and accessor methods. In reality, this class is the Entity. The
 * Entity interacts with the world entirely through this class because it is the 2D representation.
 * 
 * *********** IMPORTANT ******************
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

package com.teamsweepy.greywater.entity.component;

import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.math.Rectangle;


public class Hitbox {

	/* ************* POSITIONAL VARIABLES ************** */
	public Point2F destination;
	private float speed = 0f; //per second
	private float xDelta = 0f;
	private float yDelta = 0f;

	// physics object
	private Rectangle hitBox;

	public Hitbox() {
		destination = new Point2F();
		this.speed = 0;
		hitBox = new Rectangle();
	}

	/**
	 * Constructor. Sets up location and square hit space
	 * @param x- X co-ordinate of hitBox
	 * @param y - Y co-ordinate of hitBox
	 * @param spd - how many units per update the sprite moves
	 * @param hitWidth- width of the hitBox
	 * @param hitHeight- height of hitBox
	 */
	public Hitbox(float x, float y, int hitWidth, int hitHeight, float spd) {
		destination = new Point2F(x, y);
		this.speed = spd;
		hitBox = new Rectangle(x, y, hitWidth, hitHeight);
	}

	/** Used to move the object if it has a destination. If not, does nothing. */
	public void tick(float deltaTime) {
		//get direction of movement * speed
		xDelta = Math.signum((destination.getX() - hitBox.getX())) * speed * deltaTime;
		yDelta = Math.signum((destination.getY() - hitBox.getY())) * speed * deltaTime;
		if (Math.abs(xDelta) > Math.abs(destination.getX() - hitBox.getX()))
			xDelta = destination.getX() - hitBox.getX();

		if (Math.abs(yDelta) > Math.abs(destination.getY() - hitBox.getY()))
			yDelta = destination.getY() - hitBox.getY();

		if (xDelta != 0 || yDelta != 0)
			setLocation(xDelta + hitBox.getX(), yDelta + hitBox.getY());

		xDelta = 0; //clear out for next cycle
		yDelta = 0;
	}

	/** Checks a point intersection with a Hitbox */
	public boolean intersects(Point2F point) {
		boolean intersectsOnX = hitBox.getX() < point.x && hitBox.getX() + hitBox.getWidth() > point.x;
		boolean intersectsOnY = hitBox.getY() < point.y && hitBox.getY() + hitBox.getHeight() > point.y;
		return intersectsOnX && intersectsOnY;
	}

	/** Updates the hitspace (teleports) to this given location */
	public void setLocation(float x, float y) {
		hitBox.set(x, y, hitBox.width, hitBox.height);
	}

	/** Sets character destination to X,Y */
	public void moveTo(float x, float y) {
		destination.setLocation(x, y);
	}

	/** Stops all movement immediately, by setting destination to current location */
	public void stopMovement() {
		destination.setLocation(hitBox.x, hitBox.y);
	}

	public String toString() {
		return "{" + hitBox.x + " " + hitBox.y + " || " + hitBox.width + " " + hitBox.height + "}";
	}

	/** Returns the rectangle used in objective coordinates for collisions */
	public Rectangle getHitBox() {
		return hitBox;
	}

	/** Indicates if there is still any movement left in the X or Y axis */
	public boolean isMoving() {
		//check to see if destination = hitbox location
		if ((destination.getX() - hitBox.x) == 0. && (destination.getX() - hitBox.x) == 0.)
			if ((destination.getY() - hitBox.y == 0.) && (destination.getY() - hitBox.y) == 0.)
				return false;

		return true;
	}
}
