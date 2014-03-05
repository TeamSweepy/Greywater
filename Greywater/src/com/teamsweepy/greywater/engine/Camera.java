/**
 * Maintains Orthographic camera and offset data. Singleton class, accessible by getDefault method everywhere.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Robin
 */

package com.teamsweepy.greywater.engine;

import com.teamsweepy.greywater.math.Point2F;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Camera {

	private static Camera defaultCamera;

	private OrthographicCamera camera;
	public float xOffset, yOffset; //temporary tracking of camera movement to translate spriteBatch
	public float xOffsetAggregate, yOffsetAggregate; //track total screen movement forever
	public int width, height;
	public float xPos;
	public float yPos;

	// Synchronized so the object won't be initialized in different threads
	/**
	 * Sets up or returns the singleton camera object used everywhere
	 */
	public static synchronized Camera getDefault() {
		if (defaultCamera == null)
			defaultCamera = new Camera();
		return defaultCamera;
	}

	private Camera() {
		camera = new OrthographicCamera();
	}

	public void setViewPort(int width, int height) {
		this.width = width;
		this.height = height;

		camera.setToOrtho(false, width, height);
		camera.translate(new Vector3(-camera.position.x, -camera.position.y, -camera.position.z));
	}

	public void move(float x, float y) {
		xOffset -= x; //subtract so that if the coordinate is positive, the camera moves up/right
		yOffset -= y;

		xOffsetAggregate -= x; //xOffset and yOffset get frequently reset, this is for locking to the screen
		yOffsetAggregate -= y;
	}

	public void moveTo(Point2F move) {
		move = Globals.toScreenCoord(move.x, move.y);
		moveTo(move.x, move.y);
	}

	public void moveTo(float xMove, float yMove) {
		if (xMove == ((width / 2) + 50) && yMove == (2 * height / 3))
			return;

		xOffset = -(xMove - ((width / 2) + 50)); //center the player in the camera view
		yOffset = -(yMove - (2 * height / 3));

		xOffsetAggregate += xOffset;
		yOffsetAggregate += yOffset;
	}

	public void update() {
		camera.update();
	}

	public void apply(GL10 gl) {
		camera.apply(gl);
	}

	public Matrix4 getProjectionMatrix() {
		return camera.combined;
	}

	public Point2F getTranslatedForMatrix() {
		float x = xOffset;
		float y = yOffset;
		xOffset = 0;
		yOffset = 0;
		return new Point2F(x, y);
	}

	/**
	 * Convert user screen coordinates into game viewport coordinates
	 */
	public Point2F unproject(Point2F point) {
		Vector3 v3f = new Vector3(point.x, point.y, 0);
		camera.unproject(v3f);
		return new Point2F(v3f.x, v3f.y);
	}
}
