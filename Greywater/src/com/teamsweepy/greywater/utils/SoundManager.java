package com.teamsweepy.greywater.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.teamsweepy.greywater.engine.AssetLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 *
 * The sounds should be in the assets folder!
 *
 * TODO: The volume should be none-linear!
 * TODO: Make it a singleton?
 */

public class SoundManager {
    // TODO: Have a config file, where the music volumes are saved
    private static float master_volume = 1F;
    private static float sfx_volume = 1F;
    private static float music_volume = 1F;

    private static Map<String, Integer> sound_playing = new HashMap<String, Integer>();
    private static Map<Integer, Sound> sound_map = new HashMap<Integer, Sound>();
    private static Integer soundsCount;

    private static Music current_music;


    public static void playSound(String file) {
        Sound sound = AssetLoader.getAsset(Sound.class, file);
        soundsCount ++;

        // Integer.intValue(), can't be a copy
        sound_map.put(soundsCount.intValue(), sound);
        sound_playing.put(file, soundsCount.intValue());

        sound.play(getSFXVolume(false));
    }

    public static void stopSound(String file) {
        stopSound(sound_playing.get(file));
    }

    public static void stopSound(Integer id) {
        sound_map.get(id).stop();
    }

    public static void playMusic(String file) {
        playMusic(file, true);
    }

    /**
     * Only 1 music file can be played at a time
     */
    public static void playMusic(String file, boolean loop) {
        // There already is music playing!
        if(current_music != null) {
            stopMusic();
        }

        current_music = AssetLoader.getAsset(Music.class, file);
        current_music.setLooping(loop);
        current_music.setVolume(getMusicVolume());
        current_music.play();
    }

    public static void stopMusic() {
        current_music.stop();
    }

    // TODO: When changing the volume, the music and sounds playing should be updated!
    // <editor-fold desc="Volume getter / setter">
    public static void setMasterVolume(float volume) {
        master_volume = volume;
    }

    public static void setSFXVolume(float volume) {
        sfx_volume = volume;
    }

    public static void setMusicVolume(float volume) {
        music_volume = volume;
    }

    public static float getMasterVolume() {
        return master_volume;
    }

    public static float getSFXVolume() {
        return getSFXVolume(false);
    }
    /**
     * If 'raw' is set to false the output will have a the master volume applied
     * @param raw
     * @return
     */
    public static float getSFXVolume(boolean raw) {
        if(raw) {
            return sfx_volume;
        } else {
            float volume = (sfx_volume * master_volume);
            return volume;
        }
    }

    public static float getMusicVolume() {
        return getMusicVolume(false);
    }
    /**
     * If 'raw' is set to false the output will have a the master volume applied
     * @param raw
     * @return
     */
    public static float getMusicVolume(boolean raw) {
        if(raw) {
            return music_volume;
        } else {
            float volume = (music_volume * master_volume);
            return volume;
        }
    }


    // </editor-fold>
}
