/**
 * Maintains Orthographic camera and offset data. Singleton class, accessible by getDefault method everywhere.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Robin
 */

package com.teamsweepy.greywater.engine;

import com.teamsweepy.math.Point2F;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

// If we want multiple cameras, we need to write this camera a little bit different.

public class Camera {

	private static Camera defaultCamera;

	private OrthographicCamera camera;
	public float xOffset, yOffset;
	public int width, height;

	// Synchronized so the object won't be initialized in different threads
	public static synchronized Camera getDefault() {
		if (defaultCamera == null) defaultCamera = new Camera();

		return defaultCamera;
	}

	private Camera() {
		camera = new OrthographicCamera();
	}

	public void setViewPort(int width, int height) {
		this.width = width;
		this.height = height;

		camera.setToOrtho(false, width, height);
	}

	public void move(float x, float y) {
		xOffset -= x; //subtract so that if the coordinate is positive, the camera moves up/right
		yOffset -= y;
		//		camera.translate(x, y);
		//		camera.update();
		//		camera.apply(Gdx.gl10);

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

	public Point2F toIsoCoord(float xCoord, float yCoord) {
		float x = xCoord - yCoord;
		float y = (xCoord + yCoord) / 2;

		return new Point2F(x, y);
	}

	public Point2F toNormalCoord(float xIso, float yIso) {
		float x = (2 * xIso + yIso) / 2;
		float y = (2 * yIso - xIso) / 2;

		return new Point2F(x, y);
	}
}
