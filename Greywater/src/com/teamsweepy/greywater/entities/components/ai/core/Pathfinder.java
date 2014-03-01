/**
 * 'T' stands for the object create returns
 *
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

package com.teamsweepy.greywater.entities.components.ai.core;

import java.awt.*;

public abstract class Pathfinder<T> {
    protected int[][] map;
    protected Point end, start;

    public Pathfinder() {
        reset();
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int[][] getMap() {
        return map;
    }

    // Used to create the pathfinder
    public abstract T create();

    // Used in the initialization
    public abstract void reset();

    public abstract boolean isGoal(Point from);

    protected abstract double h(Point from, Point to);
}
