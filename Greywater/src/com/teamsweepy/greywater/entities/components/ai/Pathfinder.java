package com.teamsweepy.greywater.entities.components.ai;

import com.teamsweepy.greywater.math.Point2F;

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
    private Map<Point, Double> mindists;
    private Double lastCost;
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

    private boolean isGoal(Point node) {
        return (node.x == end.x) &&(node.y == end.y);
    }

    // It's going to get the value from an map array,
    // The X and Y both need to be integers
    // Map 0 = walkable area
    private Double g(Point from, Point to) {
        if(from.x == to.x && from.y == to.y) return 0.0;
        if(map[to.y][to.x] == 0) return 1.0;
        return Double.MAX_VALUE;
    }

    private Double h(Point to) {
        return new Double(
                Math.abs(map[0].length - 1 - to.x)
                + Math.abs(map.length - 1 - to.y)
        );
    }

    private List<Point> generateSuccesor(Point node){
        List<Point> ret = new LinkedList<Point>();
        int x = node.x;
        int y = node.y;

        if(y < map.length-1 && map[y+1][x]!=1) ret.add(new Point(x, y+1));
        if(x < map[0].length-1 && map[y][x+1]!=1) ret.add(new Point(x+1, y));

        return ret;
    }

    private Double f(AStarPath p, Point from, Point to) {
        Double g = g(from, to) + ((p.parent != null) ? p.parent.g : 0.0);
        Double h = h(to);

        p.g = g;
        p.f = g + h;

        return p.f;
    }

    private void expand(AStarPath<Point> path) {
        Point p = path.point;
        Double min = mindists.get(path.point);

        if(min == null || min.doubleValue() > path.f.doubleValue()){
            mindists.put(path.point, path.f);
        } else {
            return;
        }

        List<Point> successors = generateSuccesor(p);

        for(Point t : successors){
            AStarPath newPath = new AStarPath(path);
            newPath.point = t;
            f(newPath, path.point, t);
            paths.offer(newPath);
        }

        expandedCounter ++;
    }

    public Double getCost(){
        return lastCost;
    }

    public int getExpandedCounter(){
        return expandedCounter;
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
                    lastCost = Double.MAX_VALUE;
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
}

// The AStarPath will only be used in this class,
// So whe define a private class
class AStarPath<T> implements Comparable {

    public T point;
    public Double f; // Objects are faster
    public Double g;
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