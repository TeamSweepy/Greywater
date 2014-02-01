
package com.teamsweepy.greywater.engine;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;


public class Camera {

	public static int xOffset;
	public static int yOffset;
	private static OrthographicCamera camera;

	public static void init(int xWidth, int yHeight) {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, xWidth, yHeight);
	}

	public static void move(float x, float y) {
		xOffset += x;
		yOffset += y;
		camera.translate(x, y);
	}

	public static void update() {
		camera.update();
	}

	public static void apply(GL10 gl10) {
		camera.apply(gl10);

	}

	public static Matrix4 getProjectionMatrix() {
		return camera.combined;
	}

}
