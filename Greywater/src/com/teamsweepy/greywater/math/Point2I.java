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

// TODO: There is no need to extend Object, every class is already an Object.
public class Point2I extends Object {

	public int x;
	public int y;

	public Point2I() {
		x = 0;
		y = 0;
	}

	public Point2I(int x, int y) {
		this.x = x;
		this.y = y;
	}


	public int length() {
		return (int) Math.sqrt(x * x + y * y);
	}

	public int distance(Point2I p2) {
		return (int) Math.sqrt(Math.pow(p2.x - x, 2) + Math.pow(p2.y - y, 2));
	}

	public int distance(int x, int y) {
		return (int) Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
	}

	public String toString() {
		return "Point2I x:" + x + " y:" + y;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setLocation(Point2I p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	@Override
	public boolean equals(Object o){
		return equals((Point2I) o);
	}


    public boolean equals(Point2I p) {
        return equals(p.x, p.y);
    }

    public boolean equals(int x, int y) {
        return (this.x == x) && (this.y == y);
    }

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// Some basic mathematical operations, adding vectors and such

	public Point2I mul(Point2I add) {
		return new Point2I(x * add.getX(), y * add.getY());
	}

	public Point2I mul(int ratio) {
		return new Point2I(this.x * ratio, this.y * ratio);
	}

	public Point2I div(Point2I add) {
		return new Point2I(x / add.getX(), y / add.getY());
	}

	public Point2I div(int ratio) {
		return new Point2I(this.x / ratio, this.y / ratio);
	}

	public Point2I add(Point2I add) {
		return new Point2I(x + add.getX(), y + add.getY());
	}

	public Point2I add(int amout) {
		return new Point2I(this.x + amout, this.y + amout);
	}

	public Point2I sub(Point2I add) {
		return new Point2I(x - add.getX(), y - add.getY());
	}

	public Point2I sub(int amout) {
		return new Point2I(this.x - amout, this.y - amout);
	}

}
