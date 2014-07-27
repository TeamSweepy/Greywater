package com.teamsweepy.greywater.utils;

/**
 * © Biodiscus.net, Robin de Jong
 */
public class Mathf {
    public static final float ROOT_TWO = (float)Math.sqrt(2);

    public static float fastAbs(float v) {
        if(v < 0) return -v;
        return v;
    }
}
