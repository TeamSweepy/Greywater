/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

package com.teamsweepy.greywater.entity.component.ai.core;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;


public class AStar extends Pathfinder<java.util.List<Point>> {

	private PriorityQueue<AStarPath<Point>> paths;
	private Map<Point, Float> mindists; // Map<K, V> needs two objects
	private double lastCost;
	private int expandedCounter;

	private LinkedList<Point> nodes;
	private int pathIndex;

	public AStar() {
		super(); // Reset is called when super() is called
	}

	@Override
	public void reset() {
		paths = new PriorityQueue<AStarPath<Point>>();
		nodes = new LinkedList<Point>();
		mindists = new HashMap<Point, Float>();
		expandedCounter = 0;
		lastCost = 0.0;
	}

	@Override
	public boolean isGoal(Point from) {
		if (end == null) {
			return false;
		}
		return (from.x == end.x) && (from.y == end.y);
	}

	private float g(int x, int y) {
		int posX = Math.abs(x);
		int posY = Math.abs(y);
		if ((posX > 1 && posY == 0) || (posY > 1 && posX == 0)) {
			return 10.0f; // Straight
		} else {
			return 14.0f; // Diagonal
		}
	}

	@Override
	protected float h(Point from, Point to) {
        float dx = from.x - to.x;
        float dy = from.y - to.y;
		return 10.0f * (Math.abs(dx) + Math.abs(dy));
	}

	private java.util.List<Point> generateSuccesor(Point node) {
		java.util.List<Point> ret = new LinkedList<Point>();
		int x = node.x;
		int y = node.y;
		int mx, my;

		if (y < map[0].length - 1 && map[x][y + 1] != 1)
			ret.add(new Point(x, y + 1)); // Up
		if (y > 0 && map[x][y - 1] != 1)
			ret.add(new Point(x, y - 1)); // Down
		if (x > 0 && map[x - 1][y] != 1)
			ret.add(new Point(x - 1, y)); // Left
		if (x < map.length - 1 && map[x + 1][y] != 1)
			ret.add(new Point(x + 1, y)); // Right

		if (x < map.length - 1 && y < map[0].length - 1 && map[x + 1][y + 1] != 1) {
			mx = map[x + 1][y];
			my = map[x][y + 1];
			if ((mx | my) != 1 || (mx != 1 ^ my != 1))
				ret.add(new Point(x + 1, y + 1)); // Up-Right
		}
		if (x < map.length - 1 && y > 0 && map[x + 1][y - 1] != 1) {
			mx = map[x + 1][y];
			my = map[x][y - 1];
			if ((mx | my) != 1 || (mx != 1 ^ my != 1))
				ret.add(new Point(x + 1, y - 1)); // Down-Right
		}
		if (x > 0 && y < map[0].length - 1 && map[x - 1][y + 1] != 1) {
			mx = map[x - 1][y];
			my = map[x][y + 1];
			if ((mx | my) != 1 || (mx != 1 ^ my != 1))
				ret.add(new Point(x - 1, y + 1)); // Up-Left
		}
		if (x > 0 && y > 0 && map[x - 1][y - 1] != 1) {
			mx = map[x - 1][y];
			my = map[x][y - 1];
			if ((mx | my) != 1 || (mx != 1 ^ my != 1))
				ret.add(new Point(x - 1, y - 1)); // Down-Left
		}

		return ret;
	}

	private float f(AStarPath p, Point from, Point to) {
        float g;
		if (p.parent != null) {
			Point parent = (Point) p.parent.point;
			g = g(parent.x - from.x, parent.y - from.y) + p.parent.g;
		} else {
			g = g(from.x, from.y);
		}

		float h = h(from, to);

		p.g = g;
		p.f = g + h;

		return p.f;
	}

	private void expand(AStarPath<Point> path) {
		Point p = path.point;
		Float min = mindists.get(path.point);

		if (min == null || min.floatValue() > path.f) {
			mindists.put(path.point, path.f);
		} else {
			return;
		}

		java.util.List<Point> successors = generateSuccesor(p);

		for (Point t : successors) {
			AStarPath newPath = new AStarPath(path);
			newPath.point = t;
			f(newPath, (Point) newPath.point, end);
			paths.offer(newPath);
		}

		expandedCounter++;
	}

	@Override
	public java.util.List<Point> create() {
		try {
			AStarPath root = new AStarPath<Point>();
			root.point = start;

			//                if(isGoal(start))
			//                	return new LinkedList<Point>();

			f(root, start, end);
			expand(root);

			int qi = 0;
			for (int iterations = 0;iterations < 500; iterations++) {
				qi++;
				AStarPath<Point> p = paths.poll();

				if (p == null) {
					lastCost = 0;
					return null;
				}

				Point last = p.point;
				lastCost = p.g;
				if (isGoal(last)) {
					LinkedList<Point> retPath = new LinkedList<Point>();

					for (AStarPath<Point> i = p; i != null; i = i.parent) {
						retPath.addFirst(i.point);
					}
					System.out.println(qi);
					nodes = retPath;
					return retPath;
				}

				expand(p);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		return null;
	}

	// Used for debugging
	public Double getCost() {
		return lastCost;
	}

	//Used for debugging
	public int getExpandedCounter() {
		return expandedCounter;
	}
}

// The AStarPath will only be used in this class,
// So whe define a private class
class AStarPath<T> implements Comparable {

	public T point;
	public float f; // Objects are faster
	public float g;
	public AStarPath parent;

	public AStarPath() {
		parent = null;
		point = null;
		g = f = 0.0f;
	}

	public AStarPath(AStarPath p) {
		parent = p;
		g = p.g;
		f = p.f;
	}

	@Override
	public int compareTo(Object o) {
		AStarPath<T> p = (AStarPath<T>) o;
		return (int) (f - p.f);
	}
}
