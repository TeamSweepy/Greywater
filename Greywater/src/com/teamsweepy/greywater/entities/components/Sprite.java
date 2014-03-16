/**
 * Holds animation data for a given character.
 * 
 * ********* Important note on the name system in this class.************
 * 
 * Some string variables are used, primarily "name" and "ident"
 * 
 * This system was built to work a very specific way, using a dictionary to store images. The system used is as follows: each sprite for a
 * character is given a formulaic name. For the Character Tavish, his animation walking north is Tavish_Walk_North. This allows the
 * direction to be passed each time he changes bearing (Walk_South for instance) without passing his name each time.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Barnes
 */

package com.teamsweepy.greywater.entities.components;

import com.teamsweepy.greywater.engine.AssetLoader;
import com.teamsweepy.greywater.entities.components.events.AnimEvent;
import com.teamsweepy.greywater.entities.components.events.AnimEventListener;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class Sprite {

	/* ************************ ANIMATION STYLES ********************* */
	public static final int FORWARD = 0;
	public static final int REVERSED = 1;
	public static final int LOOP = 2;
	public static final int LOOP_REVERSED = 3;
	public static final int LOOP_PINGPONG = 4;
	public static final int STILL_IMAGE = 5;
	private static final int HALTED_PLAYING = 6; // for an ended animation
	public static final int NINEPATCH = 7;

	// not used externally, used for internal record keeping
	private static boolean Ping = true;// used to indicated direction of a ping ponging loop animation

	// image identifiers
	private String name; // used to indicate Atlas Name or single image name (filename)
	private String currentImageName; // used to indicate the name from within an atlas
	private int playMode;

	// time keepers
	private int frameDurationMillis;
	private int totalAnimTimeMillis;
	private int sequenceDurationMillis;

	// series length and position trackers (not time, but image-count)
	private int seriesPosition;
	private int seriesLength;

	// image references
	private AtlasRegion[] animation;
	private TextureRegion sprite;
	private NinePatch ninePatch;

	private List<AnimEventListener> listeners;

	/**
	 * Constructor for sprite that rely on TextureAtlases
	 * @param name - The name of the character/entity (Such as Tavish) This should match the name of their .atlas file!
	 * @param ident - image to start with (Such as Stand_South). It will be added to the name to find the image - Tavish + Stand_South
	 */
	public Sprite(String name, String ident) {
		this.name = name;
		currentImageName = ident;
		setImage(.5f, ident, STILL_IMAGE, TextureAtlas.class); // arbitrary default
		listeners = new ArrayList<AnimEventListener>();
	}

	/**
	 * Constructor for sprites that use single, unatlased textures.
	 * @param imageName - the name of the image to use, such as "HUD-1600" (no file extension needed, it had better be PNG)
	 */
	public Sprite(String imageName) {
		this.name = imageName;
		currentImageName = "";
		setImage(0, name, STILL_IMAGE, Texture.class); // arbitrary default
		listeners = new ArrayList<AnimEventListener>();
	}

	/**
	 * Constructor for ninepatch sprites that use an indeterminate filetype
	 * @param fileName - name of the image file (.PNG) or Atlas file (.ATLAS) - Do not include extension
	 * @param ident - name of subimage if from an Atlas file. Pass null otherwise.
	 * @param fileType - Texture or TextureAtlas .class
	 */
	public Sprite(String fileName, String ident, Class<?> fileType) {
		currentImageName = fileName;
		this.name = fileName;
		if (ident != null)
			setImage(0, ident, NINEPATCH, fileType);
		else
			setImage(0, fileName, NINEPATCH, fileType);
		listeners = new ArrayList<AnimEventListener>();
	}

	/** Constructor for sprites that use a single, unatlased textureregion that was loaded elsewhere. */
	public Sprite(TextureRegion texture) {
		sprite = texture;
		playMode = STILL_IMAGE;
		listeners = new ArrayList<AnimEventListener>();
	}

	/** Returns the current Name + Ident to let you know what animation is active currently */
	public String getCurrentImageName() {
		return currentImageName;
	}

	/** Returns the current sprite's width */
	public float getImageWidth() {
		if (playMode == STILL_IMAGE || playMode == HALTED_PLAYING)
			return sprite.getRegionWidth();
		else
			return animation[seriesPosition].getRegionWidth();
	}

	/** Returns the current sprite's height */
	public float getImageHeight() {
		if (playMode == STILL_IMAGE || playMode == HALTED_PLAYING)
			return sprite.getRegionHeight();
		else
			return animation[seriesPosition].getRegionHeight();
	}

	/** Returns the current sprite's bounding box with its lower left corner at x,y */
	public Rectangle getImageRectangleAtOrigin(float x, float y) {
		return new Rectangle(x, y, getImageWidth(), getImageWidth());
	}

	/**
	 * Calls the other method with width and height params Renders at default width and height.
	 * @param x - x co-ordinate to render at
	 * @param y - y co-ordinate to render at
	 */
	public void render(SpriteBatch g, float x, float y) {
		render(g, x, y, getImageWidth(), getImageHeight());
	}

	/**
	 * Draws the sprite with a specific size
	 * @param x - x co-ordinate to render at
	 * @param y - y co-ordinate to render at
	 * @param w - w is the width of the drawing place
	 * @param h - h is the height of the drawing place
	 */
	public void render(SpriteBatch g, float x, float y, float w, float h) {
		if (playMode == NINEPATCH) {
			ninePatch.draw(g, x, y, w, h);
		} else if (playMode == STILL_IMAGE || playMode == HALTED_PLAYING) {
			g.draw(sprite, x, y, w, h);
		} else {
			g.draw(animation[seriesPosition], x, y, w, h);
		}
	}

	/**
	 * Draw an image rotated around the center of the image.
	 * @param x - x co-ordinate to render at
	 * @param y - y co-ordinate to render at
	 * @param rotationDegCCW - degrees to rotate the image. To go clockwise, use a negative value.
	 */
	public void renderRotated(SpriteBatch g, float x, float y, float rotationDegCCW) {
		if (playMode == STILL_IMAGE || playMode == HALTED_PLAYING)
			g.draw(sprite, x, y, sprite.getRegionWidth() / 2, sprite.getRegionHeight() / 2, sprite.getRegionWidth(), sprite.getRegionHeight(), 1, 1, rotationDegCCW);
		else
			g.draw(animation[seriesPosition], x, y, animation[seriesPosition].getRegionWidth() / 2, animation[seriesPosition].getRegionHeight() / 2,
				animation[seriesPosition].getRegionWidth(), animation[seriesPosition].getRegionHeight(), 1, 1, rotationDegCCW);
	}

	/** Updates the image if it is animated, should to be called once every anim-period. */
	public void tick(float elapsedTimeSeconds) {
		if (playMode == STILL_IMAGE || playMode == HALTED_PLAYING)
			return; // no need to tick static images

		totalAnimTimeMillis += elapsedTimeSeconds * 1000;// seconds -> millis
		int originalSeriesPosition = seriesPosition;

		if (playMode == FORWARD || playMode == LOOP) {
			seriesPosition = (totalAnimTimeMillis / frameDurationMillis);

			if (seriesPosition > seriesLength - 1) {// if we've gone past the last image in series
				if (playMode == LOOP) {
					seriesPosition = 0; // restart
					totalAnimTimeMillis = 0;
					fireEvent(currentImageName, true, false);
					fireEvent(currentImageName, false, true);
				} else {
					seriesPosition = seriesLength - 1; // stay at last position
					stopAnimating();
					fireEvent(currentImageName, true, false);
				}
			}
		}// end forward/loop

		if (playMode == REVERSED || playMode == LOOP_REVERSED) {
			seriesPosition = ((seriesLength - 1) - (totalAnimTimeMillis / frameDurationMillis));

			if (seriesPosition < 0) { // if we've gone past the last image in series
				if (playMode == LOOP_REVERSED) {
					seriesPosition = seriesLength - 1; //restart
					totalAnimTimeMillis = 0;
					fireEvent(currentImageName, true, false);
					fireEvent(currentImageName, false, true);
				} else { //stop in last position
					seriesPosition = 0;
					stopAnimating();
					fireEvent(currentImageName, true, false);
				}
			}
		}// end reverseloop/reverse

		if (playMode == LOOP_PINGPONG) {
			if (Ping) // ping indicates forward playing
				seriesPosition = (totalAnimTimeMillis / frameDurationMillis);
			else
				// !Ping (pong) indicates reverse playing
				seriesPosition = seriesLength - 1 - (totalAnimTimeMillis / frameDurationMillis);

			// if past either end of the loop
			if (seriesPosition > seriesLength - 1) {
				Ping = !Ping; // reverse
				seriesPosition = seriesLength - 1;
				totalAnimTimeMillis = 0;
				fireEvent(currentImageName, true, false);
				fireEvent(currentImageName, false, true);
			} else if (seriesPosition < 0) {
				Ping = !Ping; // reverse
				seriesPosition = 0;
				totalAnimTimeMillis = 0;
				fireEvent(currentImageName, true, false);
				fireEvent(currentImageName, false, true);
			}
		}//end pingpong

		if (originalSeriesPosition != seriesPosition && (seriesPosition == 3 || seriesPosition == 0)) { //halfway through
			fireEvent(currentImageName, false, false);
		}
	}

	/** Halts animation immediately and freezes it to whatever frame its on currently */
	public void stopAnimating() {
		playMode = HALTED_PLAYING;
		sprite = animation[seriesPosition];
		totalAnimTimeMillis = 0;
		fireEvent(currentImageName, true, false);
	}

	/**
	 * Sets sprites image/animation
	 * @param duration_seconds - length of time to loop/play in seconds
	 * @param ident - Images are loaded as name+ident (Tavish + Walk_North)
	 * @param playMode - how the images should play. Static ints of this class.
	 */
	public void setImage(float durationSeconds, String ident, int playMode) {
		setImage(durationSeconds, ident, playMode, TextureAtlas.class); // arbitrary default
	}

	/** Sets a sprite to a single still image. Image name should be the filename without extension. Case sensitive. */
	public void setImage(String imageName) {
		this.name = imageName;
		currentImageName = "";
		setImage(0, name, STILL_IMAGE, Texture.class); // arbitrary default
	}

	/** Actually handles the logic of setting up the sprite image. */
	private void setImage(float durationSeconds, String ident, int playMode, Class<?> classType) {
		this.playMode = playMode;
		if (currentImageName.equalsIgnoreCase(name + "_" + ident) && durationSeconds * 1000 == sequenceDurationMillis && playMode == this.playMode)
			return;

		Ping = true;
		currentImageName = name + "_" + ident;
		currentImageName = currentImageName.toUpperCase();
		totalAnimTimeMillis = 0;
		sequenceDurationMillis = (int) (durationSeconds * 1000); // 1000 millisec in 1 sec
		fireEvent(currentImageName, false, true);

		if (classType == TextureAtlas.class) {
			// load image from the assetloader/textureatlas, get approriate texture region
			TextureAtlas ta = ((TextureAtlas) AssetLoader.getAsset(TextureAtlas.class, name + ".atlas"));
			Array<AtlasRegion> ar = ta.findRegions(currentImageName);
			animation = ar.toArray(AtlasRegion.class);
			seriesLength = animation.length;
			frameDurationMillis = sequenceDurationMillis / seriesLength;
			animation[0].getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			if (playMode == STILL_IMAGE || playMode == NINEPATCH) {
				sprite = animation[0];
				if (playMode == NINEPATCH) {
					int divisorLine = sprite.getRegionHeight() / 3;//ninepatches must be square!
					ninePatch = new NinePatch(sprite, divisorLine, divisorLine, divisorLine, divisorLine);
				}
				return; // no further processing needed on static images
			}
		} else if (classType == Texture.class) {
			sprite = new TextureRegion((Texture) AssetLoader.getAsset(Texture.class, name + ".png"));
			sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			if (playMode == NINEPATCH) {
				int divisorLine = sprite.getRegionHeight() / 3;//ninepatches must be square!
				ninePatch = new NinePatch(sprite, divisorLine, divisorLine, divisorLine, divisorLine);
			}//end if
		}//end elseif

		if (playMode != LOOP_REVERSED && playMode != REVERSED)
			seriesPosition = 0; // everything starts at the beginning
		else
			seriesPosition = (seriesLength - 1); // except reversed/revloop which starts at the end
	}

	/**
	 * Increment or decrement the animation by adjustment frames. Very large numbers will go to the end, Very negative goes to the
	 * beginning. Also change the playMode if you want. -1 will leave the current playmode in effect.
	 */
	public void changeSeriesPosition(int adjustment, int playMode) {
		if (animation != null && animation.length > 0) {
			if (adjustment + seriesPosition > animation.length) {
				seriesPosition = animation.length - 1;
			} else if (adjustment + seriesPosition < 0) {
				seriesPosition = 0;
			} else {
				seriesPosition += adjustment;
			}
			sprite = animation[seriesPosition];
		}
		if (playMode != -1) {
			this.playMode = playMode;
		}
	}

	/** Add a class that implements AnimEventListener Interface who wants to listen to this sprite */
	public void addAnimListener(AnimEventListener listener) {
		listeners.add(listener);
	}

	/** Remove a class that implements AnimEventListener Interface who no longer wants to listen to this sprite */
	public void removeAnimListener(AnimEventListener listener) {
		listeners.remove(listener);
	}

	/** Fire an event for all listeners to hear */
	public void fireEvent(String message, boolean ending, boolean starting) {
		if (listeners == null)
			return;

		AnimEvent event = new AnimEvent(this, message, ending, starting);
		for (AnimEventListener listener : listeners) {
			listener.handleEvent(event);
		}
	}
}
