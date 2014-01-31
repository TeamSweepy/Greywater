/**
 * Holds animation data for a given character.
 * 
 * *********Important note on the name system in this class.********************
 * 
 * Some string variables are used, primarily "name" and "ident"
 * 
 * This system was built to work a very specific way, using a dictionary to store images. The system used is as follows: each sprite for a
 * character is given a formulaic name. For the Character Tavish, his animation walking north is Tavish_Walk_North. This allows the
 * direction to be passed each time he changes bearing (Walk_South for instance) without passing his name each time.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

package com.teamsweepy.greywater.entities.components;

import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.engine.Engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;



public class Sprite {

	//animation styles
	public static final int FORWARD = 0;
	public static final int REVERSED = 1;
	public static final int LOOP = 2;
	public static final int LOOP_REVERSED = 3;
	public static final int LOOP_PINGPONG = 4;
	public static final int STILL_IMAGE = 5;
	private static final int HALTED_PLAYING = 6; //for an ended animation
	private static boolean Ping = true;//used to indicated direction of a ping ponging loop animation

	//image identifiers
	private String name;
	private String currentImageName;
	private int playMode;

	// time keepers
	private int cycleLengthMillis;
	private int totalAnimTimeMillis;
	private int sequenceDurationMillis;
	private int animPeriodMillis;

	// series length and position trackers (not time, image-count)
	private int seriesPosition;
	private int seriesLength;


	//image references
	private AtlasRegion[] animation;
	private TextureRegion sprite;


	/**
	 * Constructor for sprites.
	 * 
	 * @param name - The name of the character/entity (Such as Tavish)
	 * @param imgName - default image to start with. (Such as _Stand_South)
	 */
	public Sprite(String name, String imgName) {
		animPeriodMillis = (Engine.ANIMATION_PERIOD_NANOSEC / 1000000);
		this.name = name;
		currentImageName = imgName;
		setImage(.5f, imgName, STILL_IMAGE); //arbitrary default

	}

	/**
	 * Draws the sprite
	 * 
	 * @param g - graphics object for rendering
	 * @param x - x co-ordinate to render at
	 * @param y - y co-ordinate to render at
	 */
	public void render(SpriteBatch g, int x, int y) {
		if (playMode == STILL_IMAGE || playMode == HALTED_PLAYING)
			g.draw(sprite, x, y);
		else
			g.draw(animation[seriesPosition], x, y);
	}

	/**
	 * Updates the image if it is animated, assumed to be called once every anim-period.
	 */
	public void tick(float elapsedTime) {
		if (playMode == STILL_IMAGE || playMode == HALTED_PLAYING) return; //no need to tick static images

		totalAnimTimeMillis += elapsedTime;

		if (playMode == FORWARD || playMode == LOOP) {
			seriesPosition = (totalAnimTimeMillis / cycleLengthMillis);

			if (seriesPosition > seriesLength - 1) {//if we've gone past the last image in series
				if (playMode == LOOP) {
					seriesPosition = 0; //restart
					totalAnimTimeMillis = 0;
				} else {
					seriesPosition = seriesLength - 1; //stay at last position
					stopAnimating();
				}
			}
		}//end forward/loop

		if (playMode == REVERSED || playMode == LOOP_REVERSED) {
			seriesPosition = (seriesLength - (totalAnimTimeMillis / cycleLengthMillis));

			if (seriesPosition < 0) { //if we've gone past the last image in series
				if (playMode == LOOP_REVERSED)
					seriesPosition = seriesLength - 1;
				else {
					seriesPosition = 0;
					stopAnimating();
				}
			}
		}//end reverseloop/reverse

		if (playMode == LOOP_PINGPONG) {
			if (Ping) //ping indicates forward playing
				seriesPosition = (totalAnimTimeMillis / cycleLengthMillis);
			else
				// !Ping (pong) indicates reverse playing
				seriesPosition = seriesLength - 1 - (totalAnimTimeMillis / cycleLengthMillis);

			//if past either end of the loop
			if (seriesPosition > seriesLength - 1) {
				Ping = !Ping; //reverse
				seriesPosition = seriesLength - 1;
				totalAnimTimeMillis = 0;

			} else if (seriesPosition < 0) {
				Ping = !Ping; //reverse
				seriesPosition = 0;
				totalAnimTimeMillis = 0;
			}
		}
	}

	/**
	 * Halts animation immediately and freezes it to whatever frame its on currently
	 */
	public void stopAnimating() {
		playMode = HALTED_PLAYING;
		sprite = animation[seriesPosition];
		totalAnimTimeMillis = 0;
	}

	/**
	 * @return the current Name + Ident to let you know what animation is active currently
	 */
	public String getCurrentImageName() {
		return currentImageName;
	}

	/**
	 * Sets sprites image/animation
	 * 
	 * @param duration_seconds - length of time to loop in seconds
	 * @param ident - Images are loaded as name+ident (Tavish + _Walk_North)
	 * @param playMode - how the images should play. Static enum ints of this class.
	 */
	public void setImage(float durationSeconds, String ident, int playMode) {
		this.playMode = playMode;
		if (currentImageName.equals(name + ident)) return;

		Ping = true;
		currentImageName = name + ident;
		totalAnimTimeMillis = 0;
		sequenceDurationMillis = (int) (durationSeconds * 1000); //1000 millisec in 1 sec

		//load image from the assetloader/textureatlas, get approriate texture region
		TextureAtlas ta = ((TextureAtlas) AssetLoader.getAsset(TextureAtlas.class, name + ".atlas"));
		Array<AtlasRegion> ar = ta.findRegions(name.toUpperCase() + ident);
		animation = ar.toArray(AtlasRegion.class);
		seriesLength = animation.length;
		cycleLengthMillis = sequenceDurationMillis / seriesLength;

		if (playMode == STILL_IMAGE) {
			sprite = animation[0];
			return; //no further processing needed on static images
		}

		if (playMode != LOOP_REVERSED && playMode != REVERSED)
			seriesPosition = 0; //everything starts at the beginning
		else
			seriesPosition = (seriesLength - 1); //except reversed/revloop which starts at the end
	}
}
