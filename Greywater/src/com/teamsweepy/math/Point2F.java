/**
 * Java's point2D default's to doubles, making return types a huge paint.
 * I don't trust LibGDX's math because they've failed many other places. Also, Vector2F is unnecessary for 99% of our point operations.
 * 
 * Copyright Team Sweepy - Jeremy Barnes 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 * 
 * @author Barnes
 */
package com.teamsweepy.math;

import java.awt.Point;


public class Point2F extends Point {

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

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setLocation(Point2F p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public float distance(Point2F p2){
		return (float) Math.sqrt(p2.x*x + p2.y*y);
	}
	
	public float distance(float x, float y){
		return (float) Math.sqrt(this.x*x + this.y*y);
	}

	public String toString() {
		return "Point2F x:" + x + " y:" + y;
	}

}