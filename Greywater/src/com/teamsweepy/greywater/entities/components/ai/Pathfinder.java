package com.teamsweepy.greywater.entities.components.ai;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class implements the A* algorithm
 * TODO: Make it so whe can change the step length (every tile is a step),
 * TODO: This way whe can get the best performance from the pathfinder
 *
 * User: Robin de Jong
 */
public class Pathfinder
{
    private PriorityQueue<AStarPath<Point>> paths;
    private Map<Point, Double> mindists; // Map<K, V> needs two objects
    private double lastCost;
    private int expandedCounter;

    private int[][] map;
    private Point end, start;

    private LinkedList<Point> nodes;
    private int pathIndex;

    public Pathfinder() {
        paths = new PriorityQueue<AStarPath<Point>>();
        nodes = new LinkedList<Point>();
        mindists = new HashMap<Point, Double>();
        expandedCounter = 0;
        lastCost = 0.0;
    }

    private boolean isGoal(Point from) {
        return (from.x == end.x) &&(from.y == end.y);
    }

    // There is no need to set a Point in the parameter

    private double g(int x, int y) {
        int posX = Math.abs(x);
        int posY = Math.abs(y);
        if((posX > 1 && posY == 0) || (posY > 1 && posX == 0)) {
            return 10.0; // Straight
        } else {
            return 14.0; // Diagonal
        }
    }

    private double h(Point from, Point to) {
        double dx = from.x - to.x;
        double dy = from.y - to.y;
        return 10.0 * (Math.abs(dx) + Math.abs(dy));
    }

    private List<Point> generateSuccesor(Point node){
        List<Point> ret = new LinkedList<Point>();
        int x = node.x;
        int y = node.y;

        if(y < map.length-1 && map[y+1][x]!=1) ret.add(new Point(x, y+1)); // Up
        if(y > 0 && map[y-1][x]!=1) ret.add(new Point(x, y-1)); // Down
        if(x > 0 && map[y][x-1]!=1) ret.add(new Point(x-1, y)); // Left
        if(x < map[0].length-1 && map[y][x+1]!=1) ret.add(new Point(x+1, y)); // Right

        if(x < map[0].length-1 && y < map.length-1 && map[y+1][x+1]!=1) ret.add(new Point(x+1, y+1)); // Up-Right
        if(x < map[0].length-1 && y > 0 && map[y-1][x+1]!=1) ret.add(new Point(x+1, y-1)); // Down-Right
        if(x > 0 && y < map.length-1 && map[y+1][x-1]!=1) ret.add(new Point(x-1, y+1)); // Up-Left
        if(x > 0 && y > 0 && map[y-1][x-1]!=1) ret.add(new Point(x-1, y-1)); // Down-Left

        return ret;
    }

    private double f(AStarPath p, Point from, Point to) {
        double g;
        if(p.parent != null) {
            Point parent = (Point)p.parent.point;
            g = g(parent.x - from.x, parent.y - from.y) + p.parent.g;
        } else {
            g = g(from.x, from.y);
        }

        double h = h(from, to);

        p.g = g;
        p.f = g+h;

        return p.f;
    }

    private void expand(AStarPath<Point> path) {
        Point p = path.point;
        Double min = mindists.get(path.point);

        if(min == null || min.doubleValue() > path.f){
            mindists.put(path.point, path.f);
        } else {
            return;
        }

        List<Point> successors = generateSuccesor(p);

        for(Point t : successors){
            AStarPath newPath = new AStarPath(path);
            newPath.point = t;
            f(newPath, (Point)newPath.point, end);
            paths.offer(newPath);
        }

        expandedCounter ++;
    }

    public void setMap(int[][] map)
    {
        this.map = map;
    }

    public boolean hasPath(){
        if (nodes == null || pathIndex == nodes.size())
            return false;

        return true;
    }

    public void setNewPath(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getNextLoc() {
        if (nodes == null || pathIndex == nodes.size())
            return null;
        else {
            pathIndex++;
            return nodes.get(pathIndex - 1);
        }
    }

    public List<Point> compute(){
        try
        {
            AStarPath root = new AStarPath<Point>();
            root.point = start;
            f(root, start, end);
            expand(root);

            for(;;){
                AStarPath<Point> p = paths.poll();

                if(p == null){
                    lastCost = 0;
                    return null;
                }

                Point last = p.point;
                lastCost = p.g;

                if(isGoal(last)) {
                    LinkedList<Point> retPath = new LinkedList<Point>();

                    for(AStarPath<Point> i = p; i != null; i = i.parent){
                        retPath.addFirst(i.point);
                    }
                    nodes = retPath;
                    return retPath;
                }

                expand(p);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return null;
    }

    public Point getDestination(){

        return nodes.get(nodes.size()-1);
    }

    public void clearPath(){
        nodes.clear();
        pathIndex = 0;
    }

    // Used for debugging
    public Double getCost(){
        return lastCost;
    }

    //Used for debugging
    public int getExpandedCounter(){
        return expandedCounter;
    }
}

// The AStarPath will only be used in this class,
// So whe define a private class
class AStarPath<T> implements Comparable {

    public T point;
    public double f; // Objects are faster
    public double g;
    public AStarPath parent;

    public AStarPath() {
        parent = null;
        point = null;
        g = f = 0.0;
    }

    public AStarPath(AStarPath p) {
        parent = p;
        g = p.g;
        f = p.f;
    }

    @Override
    public int compareTo(Object o) {
        AStarPath<T> p = (AStarPath<T>)o;
        return (int)(f-p.f);
    }
}