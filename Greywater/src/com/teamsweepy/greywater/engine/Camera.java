
package com.teamsweepy.greywater.engine;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

// If we want multiple cameras, we need to wright this camera a little bit different.

public class Camera
{
    private static Camera _default;

    private OrthographicCamera camera;
    public float xOffset, yOffset;
    public int width, height;

    // Synchronized so the object won't be initialized in different threads
    public static synchronized Camera getDefault() {
        if(_default == null) _default = new Camera();

        return _default;
    }

    private Camera()
    {
        camera = new OrthographicCamera();
    }

    public void setViewPort(int width, int height)
    {
        this.width = width;
        this.height = height;

        camera.setToOrtho(false, width, height);
    }

    public void move(float x, float y)
    {
        xOffset += x;
        yOffset += y;

        // The camera translate might be a bit overkill
        camera.translate(x, y);
    }

    public void update()
    {
        camera.update();
    }

    public void apply(GL10 gl)
    {
        camera.apply(gl);
    }

    public Matrix4 getProjectionMatric()
    {
        return camera.combined;
    }

    public Vector2 toIsoCoord(float xCoord, float yCoord)
    {
        float x = xCoord - yCoord;
        float y = (xCoord + yCoord) / 2;

        return new Vector2(x, y);
    }

    public Vector2 toNormalCoord(float xIso, float yIso)
    {
        float x = (2 * xIso + yIso) / 2;
        float y = (2 * yIso - xIso) / 2;

        return new Vector2(x, y);
    }


//	public static int xOffset;
//	public static int yOffset;
//	private static OrthographicCamera camera;
//
//	public static void init(int width, int height) {
//		camera = new OrthographicCamera();
//		camera.setToOrtho(false, width, height);
//	}
//
//	public static void move(float x, float y) {
//		xOffset += x;
//		yOffset += y;
//		camera.translate(x, y);
//	}
//
//	public static void update() {
//		camera.update();
//	}
//
//	public static void apply(GL10 gl10) {
//		camera.apply(gl10);
//
//	}
//
//	public static Matrix4 getProjectionMatrix() {
//		return camera.combined;
//	}

}
