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
		pathIndex = 0;

		if (currentMethod == Method.ASTAR) {
            aStarMap = AStar.create(from, end, level.getMapAsCosts());
		}
	}

    public boolean finished() {
        if (currentMethod == Method.ASTAR) {
            if(aStarMap != null) {
                return (pathIndex >= aStarMap.size() - 1);
            } else {
                return true;
            }
        }

        // TODO: implement this for the PF
        return false;
    }

	public Point2I getNextStep() {
		if (currentMethod == Method.ASTAR) {
			if (aStarMap != null && pathIndex < aStarMap.size()) {
				pathIndex++;
				return aStarMap.get(pathIndex - 1);
			}
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