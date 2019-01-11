package com.game.twinghosts.elementalclimber.Data.InGame;

import android.content.Context;
import android.media.MediaPlayer;

import com.game.twinghosts.elementalclimber.R;

public class SoundPlayer {

    public static MediaPlayer buttonMediaPlayer;
    public static MediaPlayer jumpMediaPlayer;
    public static MediaPlayer deadMediaPlayer;

    public static void playButtonClickSound(Context context){
        try {
            if (buttonMediaPlayer.isPlaying()) {
                buttonMediaPlayer.stop();
                buttonMediaPlayer.release();
                buttonMediaPlayer = MediaPlayer.create(context, R.raw.button_click);
            } buttonMediaPlayer.start();
        } catch(Exception e) { e.printStackTrace(); }
    }

    public static void playJumpSound(Context context){
        try {
            if (jumpMediaPlayer.isPlaying()) {
                jumpMediaPlayer.stop();
                jumpMediaPlayer.release();
                jumpMediaPlayer = MediaPlayer.create(context, R.raw.jump);
            } jumpMediaPlayer.start();
        } catch(Exception e) { e.printStackTrace(); }
    }

    public static void playDeadSound(Context context){
        try {
            if (deadMediaPlayer.isPlaying()) {
                deadMediaPlayer.stop();
                deadMediaPlayer.release();
                deadMediaPlayer = MediaPlayer.create(context, R.raw.dead);
            } deadMediaPlayer.start();
        } catch(Exception e) { e.printStackTrace(); }
    }
}
