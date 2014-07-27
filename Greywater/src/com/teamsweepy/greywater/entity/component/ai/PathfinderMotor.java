/**
 * If we want different behaviours then this class provide extend it, almost every method is protected/public.
 * 
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 *
 *
 * IMPORTANT!!! The PF pathfinder won't work, this needs to get fixed ASAP
 */

package com.teamsweepy.greywater.entity.component.ai;

import com.teamsweepy.greywater.entity.component.ai.core.AStar;
import com.teamsweepy.greywater.entity.level.Level;
import com.teamsweepy.greywater.math.Point2I;

import java.util.LinkedList;

public class PathfinderMotor {

	private Method currentMethod;

	// A* specific
	private java.util.List<Point2I> aStarMap;
	private int pathIndex;

	// PF specific
	private double[][] pfMap;
	private Point2I curPos;
	private Point2I[] nodesDirections;
	private Level level;

	public PathfinderMotor(Method method) {
		currentMethod = method;
		pathIndex = 0;

//		if (method == Method.ASTAR) {
//			pathfinder = new AStar();
//		} else if (method == Method.POTENTIAL_FIELD) {
//			pathfinder = new PotentialField();
//		}
	}

	public void reset() {
		if (aStarMap != null)
			aStarMap.clear();
		pathIndex = 0;
		curPos = null;
		pathIndex = 0;
	}

	public void createPath(Point2I from, Point2I end) {
        if(from.equals(end)) { // No need to find the same path twice
            return;
        }
        if(currentMethod == Method.ASTAR) {

        }

		pathIndex = 0;

		if (from.equals(end)) { //if clicked self, ignore
			aStarMap = new LinkedList<Point2I>();
			aStarMap.add(from);
			return;
		}

		if (currentMethod == Method.ASTAR) {
            aStarMap = AStar.create(from, end, level.getMapAsCosts());

//            System.out.println("Created map: "+aStarMap.size());
//			aStarMap = (java.util.List<Point2I>) pathfinder.create();
			if (aStarMap != null)
				for (Point2I p : aStarMap) {
					level.getMapAsCosts()[p.x][p.y] = 1;
				}
		} else if (currentMethod == Method.POTENTIAL_FIELD) {
//			pfMap = (double[][]) pathfinder.create();
		}
	}

	public Point2I getNextStep() {
		if (currentMethod == Method.ASTAR) {
			if (aStarMap != null && pathIndex < aStarMap.size()) {
				pathIndex++;
				return aStarMap.get(pathIndex - 1);
			}
		} else if (currentMethod == Method.POTENTIAL_FIELD) {
//			if (!pathfinder.isGoal(curPos)) {
//				curPos = getBestNode(curPos, nodesDirections, pfMap);
//				return curPos;
//			}
		}

		return null;
	}

	public Point2I getFinalStep() {
		if (currentMethod == Method.ASTAR) {
			if (aStarMap != null && aStarMap.size() > 0) {

				return aStarMap.get(aStarMap.size() - 1);
			}
		}
		return null;
	}

	private Point2I getBestNode(Point2I pos, Point2I[] directions, double[][] map) {
//		Point2I node = null;
//
//		double highestValue = 0;
//
//		for (Point2I dir : directions) {
//			int x = pos.x + dir.x;
//			int y = pos.y + dir.y;
//			Point2I newPos = new Point2I(x, y);
//
//			if (pathfinder.isGoal(newPos)) {
//				return newPos;
//			}
//
//			if (x > 0 && x < map.length) {
//				if (y > 0 && y < map[0].length) {
//					if (map[x][y] > highestValue) {
//						highestValue = map[x][y];
//						node = newPos;
//					}
//				}
//			}
//		}
//
//		return node;
        return null;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public boolean hasPath() {
		if (currentMethod == Method.ASTAR) {
			if (aStarMap != null)
				return pathIndex != aStarMap.size();
			return false;
		}
		return false;
	}

	public enum Method {
		ASTAR, POTENTIAL_FIELD
	}
}
