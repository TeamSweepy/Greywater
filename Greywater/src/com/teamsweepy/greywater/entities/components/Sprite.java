/**
 * Holds animation data for a given character.
 * 
 * *********Important note on the name system in this class.******************** 
 * Some string variables are used, primarily "name" and "ident"
 * 
 * This system was built to work a very specific way, using a hashmap to store images. The system used is as follows: each sprite for a
 * character is given a formulaic name. For the Character Tavish, his animation walking north is Tavish_Walk_North. This allows the
 * direction to be passed each time he changes bearing (Walk_South for instance) without passing his name each time.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

package com.teamsweepy.greywater.entities.components;

import com.teamsweepy.greywater.engine.Engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class Sprite {

	private boolean isLooping;
	private boolean isTicking;

	private String name;
	private String currImgName;

	// time keepers
	private double cycleLength_Millis;
	private long totalAnimTime_Millis;
	private double sequenceDuration_Millis;
	private int animPeriod_Millis;

	// series length and position trackers (not time, image-count)
	private int seriesPosition;// TODO private
	private int seriesLength;


	/**
	 * Constructor for sprites.
	 * 
	 * @param name - The name of the character/entity (Such as Tavish)
	 * @param imgName - default image to start with.
	 */
	public Sprite(String name, String imgName) {
		animPeriod_Millis = (int) (Engine.ANIMATION_PERIOD_NANOSEC / 1000000);
	}

	/**
	 * Draws the sprite
	 * 
	 * @param g - graphics object for rendering
	 * @param x - x co-ordinate to render at
	 * @param y - y co-ordinate to render at
	 */
	public void render(SpriteBatch g, int x, int y) {}

	/**
	 * Updates the image if it is animated, assumed to be called once every anim-period.
	 */
	public void tick() {}


	public String getCurrentImageName() {
		return currImgName;
	}

	/**
	 * Loops an animation for a set period of time
	 * 
	 * @param duration_seconds - length of time to loop in seconds
	 * @param ident - Images are loaded as name+ident (Tavish + _Walk_North)
	 */
	public void loopImg(double duration_seconds, String ident) {}

	/**
	 * Plays an animation once.
	 * 
	 * @param duration_seconds - length of time to play the animation in seconds
	 * @param ident - Images are loaded as name+ident (Tavish + _Walk_North)
	 */
	public void animate(double duration_seconds, String ident) {}

	/**
	 * Force-sets an image, circumventing the name + ident system.
	 * 
	 * @param name - the image you want to set.
	 */
	public void forceImage(String name) {}


	public boolean isAnimating() {
		return isTicking;
	}



}
