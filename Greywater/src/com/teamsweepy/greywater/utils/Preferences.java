package com.teamsweepy.greywater.utils;

import java.util.Properties;
import java.io.*;

/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 *
 *
 * How to use this class:
 *
 * 1) Create a preference file, if it does not exists it will be created
 *
 * Preferences preferences = Preferences.getDefault();
 * preferences.create("Greywater");
 *
 * 2) Set or get values
 *
 * preferences.getFloat("music_volume", 1F);
 * preferences.setFloat("music_volume", 1F);
 *
 * When getting a value, set the default value. If the object can't be found it will return default value
 *
 *
 * *) If you ever want to delete the config file. Go to your home directory, go in the .prefs folder and delete the
 *    Greywater file.
 */


public class Preferences {
    private static Preferences preferences = new Preferences();
    private Properties properties;
    private OutputStream outStream;
    private InputStream inputStream;
    private String filePath;

    private Preferences() {
        properties = new Properties();
    }

    public void create(String key) {
        String path = IO.getUserDir() + IO.seperator+".prefs"+IO.seperator;
        filePath = path+IO.seperator+key;

        File file = new File(filePath);
        File parent = file.getParentFile();

        if(!parent.exists()) {
            // Construct the path
            if(!parent.exists() && !parent.mkdirs()) {
                throw new IllegalStateException("couldn't create dir: "+file);
            }
        }

        try {
            if(!file.exists()) {
                file.createNewFile();
            }

            inputStream = new FileInputStream(filePath);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putBoolean(String key, boolean value) {
        putString(key, ""+value);
    }

    public void putFloat(String key, float value) {
        putString(key, "" + value);
    }

    public void putInt(String key, int value) {
        putString(key, ""+value);
    }

    public void putString(String key, String value) {
        properties.put(key, value);
    }

    public boolean getBoolean(String key, boolean value) {
        String string = getString(key, ""+value);

        if(string.toLowerCase().equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public Float getFloat(String key, float value) {
        Object temp = properties.get(key);

        if(temp != null) {
            return Float.valueOf((String)temp);
        }

        return value;
    }

    public int getInt(String key, int value) {
        Object temp = properties.get(key);
        if(temp != null) {
            return Integer.valueOf((String)temp);
        }

        return value;
    }

    public String getString(String key, String value) {
        Object temp = properties.get(key);

        if(temp != null) {
            return (String)temp;
        }

        return value;
    }

    public void remove(String key) {
        properties.remove(key);
    }

    public void clear() {
        properties.clear();
    }

    public void save() {
        try {
            outStream = new FileOutputStream(filePath);
            properties.store(outStream  , null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Preferences getDefault() {
        return preferences;
    }
}

