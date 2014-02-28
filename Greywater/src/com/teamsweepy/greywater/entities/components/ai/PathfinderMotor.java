/**
 * If we want different behaviours then this class provide extend it, almost every method is protected/public.
 *
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

package com.teamsweepy.greywater.entities.components.ai;

import com.teamsweepy.greywater.entities.components.ai.core.AStar;
import com.teamsweepy.greywater.entities.components.ai.core.Pathfinder;
import com.teamsweepy.greywater.entities.components.ai.core.PotentialField;
import com.teamsweepy.greywater.entities.level.Level;

import java.awt.*;

public class PathfinderMotor
{
    private Pathfinder pathfinder;
    private Method currentMethod;

    // A* specific
    private java.util.List<Point> aStarMap;
    private int pathIndex;

    // PF specific
    private double[][] pfMap;

    public PathfinderMotor(Method method) {
        currentMethod = method;
        pathIndex = 0;

        if(method == Method.ASTAR){
            pathfinder = new AStar();
        } else if(method == Method.POTENTIAL_FIELD) {
            pathfinder = new PotentialField();
        }
    }

    public void createPath(Point from, Point end) {
        // TODO: Pathfinder classes static, so we won't have to reset it from here
        pathfinder.reset();
        pathfinder.setStart(from);
        pathfinder.setEnd(end);

        if(currentMethod == Method.ASTAR){
            aStarMap = (java.util.List<Point>)pathfinder.create();
        }else if(currentMethod == Method.POTENTIAL_FIELD){
            pfMap = (double[][])pathfinder.create();
        }
    }

    public Point getNextStep(){
        if(currentMethod == Method.ASTAR) {
            if (aStarMap != null && pathIndex < aStarMap.size()) {
                pathIndex++;
                return aStarMap.get(pathIndex - 1);
            }
        } else if(currentMethod == Method.POTENTIAL_FIELD){
        }

        return null;
    }

    public void updateMap(Level level) {
        Point dimensions = level.getMapDimensions();
        int[][] mapCost = new int[dimensions.x][dimensions.y];
        // Walkable == 0
        for (int x = 0; x < dimensions.x; x++) {
            for (int y = 0; y < dimensions.y; y++) {
                if (level.isTileWalkable(x, y)) {
                    mapCost[x][y] = 0;
                } else {
                    mapCost[x][y] = 1;
                }
            }
        }

        pathfinder.setMap(mapCost);
    }

    public enum Method {
        ASTAR, POTENTIAL_FIELD
    }
}
