/**
 * Java's point2D default's to doubles, making return types a huge paint. I don't trust LibGDX's math because they've failed many other
 * places. Also, Vector2F is unnecessary for 99% of our point operations.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Barnes
 */

package com.teamsweepy.greywater.math;

import java.awt.Point;

public class Point2F {

	public float x;
	public float y;

	public Point2F() {
		x = 0f;
		y = 0f;
	}

	public Point2F(float x, float y) {
		this.x = x;
		this.y = y;
	}


	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float distance(Point2F p2) {
		return distance(p2.x, p2.y);
	}

	public float distance(float x, float y) {
		float dx = x - this.x;
		float dy = y - this.y;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	public float angle(Point2F p2) {
		return angle(p2.x, p2.y);
	}

	public float angle(float x, float y) {
		float dx = x - this.x;
		float dy = y - this.y;
		return (float) (Math.atan2(dy, dx));
	}

	public String toString() {
		return "Point2F x:" + x + " y:" + y;
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setLocation(Point2F p) {
		this.x = p.x;
		this.y = p.y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public static Point2F convertPoint(Point p) {
		return new Point2F(p.x, p.y);
	}

	public static Point convertPoint2F(Point2F p) {
		return new Point((int) p.x, (int) p.y);
	}


	// Some basic mathematical operatios, adding vectors and such

	public Point2F mul(Point2F add) {
		return new Point2F(x * add.getX(), y * add.getY());
	}

	public Point2F mul(float ratio) {
		return new Point2F(this.x * ratio, this.y * ratio);
	}

	public Point2F div(Point2F add) {
		return new Point2F(x / add.getX(), y / add.getY());
	}

	public Point2F div(float ratio) {
		return new Point2F(this.x / ratio, this.y / ratio);
	}

	public Point2F add(Point2F add) {
		return new Point2F(x + add.getX(), y + add.getY());
	}

	public Point2F add(float amout) {
		return new Point2F(this.x + amout, this.y + amout);
	}

	public Point2F sub(Point2F add) {
		return new Point2F(x - add.getX(), y - add.getY());
	}

	public Point2F sub(float amout) {
		return new Point2F(this.x - amout, this.y - amout);
	}

}
