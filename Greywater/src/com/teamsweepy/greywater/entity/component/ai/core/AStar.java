/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

package com.teamsweepy.greywater.entity.component.ai.core;

import com.teamsweepy.greywater.math.Point2I;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;


public class AStar {
    private static final int MAX_ITERATIONS = 2000;

	private static float g(int x, int y) {
		int posX = Math.abs(x);
		int posY = Math.abs(y);
		if ((posX > 1 && posY == 0) || (posY > 1 && posX == 0)) {
			return 10.0f; // Straight
		} else {
			return 14.0f; // Diagonal
		}
	}

	private static float h(Point2I from, Point2I to) {
        float dx = from.x - to.x;
        float dy = from.y - to.y;
		return 10.0f * (Math.abs(dx) + Math.abs(dy));
	}

	private static java.util.List<Point2I> generateSuccesor(Point2I node, int[][] map) {
		java.util.List<Point2I> ret = new LinkedList<Point2I>();
		int x = node.x;
		int y = node.y;
		int mx, my;
		if (y <= map[0].length - 1 && map[x][y + 1] != 1)
			ret.add(new Point2I(x, y + 1)); // Up
		if (y >= 0 && map[x][y - 1] != 1)
			ret.add(new Point2I(x, y - 1)); // Down
		if (x >= 0 && map[x - 1][y] != 1)
			ret.add(new Point2I(x - 1, y)); // Left
		if (x <= map.length - 1 && map[x + 1][y] != 1)
			ret.add(new Point2I(x + 1, y)); // Right

		if (x <= map.length - 1 && y <= map[0].length - 1 && map[x + 1][y + 1] != 1) {
			mx = map[x + 1][y];
			my = map[x][y + 1];
			if ((mx | my) != 1 || (mx != 1 ^ my != 1))
				ret.add(new Point2I(x + 1, y + 1)); // Up-Right
		}
		if (x <= map.length - 1 && y >= 0 && map[x + 1][y - 1] != 1) {
			mx = map[x + 1][y];
			my = map[x][y - 1];
			if ((mx | my) != 1 || (mx != 1 ^ my != 1))
				ret.add(new Point2I(x + 1, y - 1)); // Down-Right
		}
		if (x >= 0 && y <= map[0].length - 1 && map[x - 1][y + 1] != 1) {
			mx = map[x - 1][y];
			my = map[x][y + 1];
			if ((mx | my) != 1 || (mx != 1 ^ my != 1))
				ret.add(new Point2I(x - 1, y + 1)); // Up-Left
		}
		if (x > 0 && y > 0 && map[x - 1][y - 1] != 1) {
			mx = map[x - 1][y];
			my = map[x][y - 1];
			if ((mx | my) != 1 || (mx != 1 ^ my != 1))
				ret.add(new Point2I(x - 1, y - 1)); // Down-Left
		}

		return ret;
	}

	private static float f(AStarPath p, Point2I from, Point2I to) {
        float g;
		if (p.parent != null) {
			Point2I parent = (Point2I) p.parent.point;
			g = g(parent.x - from.x, parent.y - from.y) + p.parent.g;
		} else {
			g = g(from.x, from.y);
		}

		float h = h(from, to);

		p.g = g;
		p.f = g + h;

		return p.f;
	}

	private static void expand(Map<Point2I, Float> mindists, PriorityQueue<AStarPath<Point2I>> paths, AStarPath<Point2I> path, Point2I end, int[][] map) {
		Point2I p = path.point;
		Float min = mindists.get(path.point);

		if (min == null || min.floatValue() > path.f) {
			mindists.put(path.point, path.f);
		} else {
			return;
		}

		java.util.List<Point2I> successors = generateSuccesor(p, map);

		for (Point2I t : successors) {
			AStarPath newPath = new AStarPath(path);
			newPath.point = t;
			f(newPath, (Point2I) newPath.point, end);
			paths.offer(newPath);
		}
	}

	public static java.util.List<Point2I> create(Point2I start, Point2I end, int[][] map) {
        PriorityQueue<AStarPath<Point2I>> paths = new PriorityQueue<AStarPath<Point2I>>();
        Map<Point2I, Float> mindists = new HashMap<Point2I, Float>(); // Map<K, V> needs two objects

		try {
			AStarPath root = new AStarPath<Point2I>();
			root.point = start;

			f(root, start, end);
			expand(mindists, paths, root, end, map);

		
			for (int iterations = 0; iterations < MAX_ITERATIONS; iterations++) {

				AStarPath<Point2I> p = paths.poll();

				if (p == null) {
					return null;
				}

				Point2I last = p.point;
				if (last.equals(end)) {
					LinkedList<Point2I> retPath = new LinkedList<Point2I>();

					for (AStarPath<Point2I> i = p; i != null; i = i.parent) {
						retPath.addFirst(i.point);
					}

					return retPath;
				}

				expand(mindists, paths, p, end, map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		return null;
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
