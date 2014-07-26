package com.teamsweepy.greywater.utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

public class IO {

    public static String seperator = File.separator;

    public static String getUserDir() {
        return System.getProperty("user.home");
    }

    public static Byte[] readBuffer(URL url) {
        byte[] buffer = null;
        BufferedInputStream input = null;


        try {
            input = new BufferedInputStream(url.openConnection().getInputStream());
            int available = input.available();
            buffer = new byte[available];
            input.read(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException ex) {

            }
        }

        // Map<K, T> only allows objects, so convert the byte array to Byte
        Byte[] buf = new Byte[buffer.length];
        int i = 0;
        for(byte b : buffer) {
            buf[i++] = b;
        }


        return buf;
    }

    public static String readFile(URL url) {
        StringBuilder content = new StringBuilder();

        try {
            URLConnection con = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            try {
                String line;

                while((line = reader.readLine()) != null) {
                    content.append(line);
                    content.append("\n");
                }


            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return content.toString();
    }
}
