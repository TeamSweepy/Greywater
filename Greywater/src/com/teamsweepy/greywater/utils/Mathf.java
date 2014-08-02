package com.teamsweepy.greywater.utils;

/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */
public class Mathf {
    public static final float ROOT_TWO = (float)Math.sqrt(2);

    public static float fastAbs(float v) {
        if(v < 0) return -v;
        return v;
    }
}
