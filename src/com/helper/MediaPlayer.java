package com.helper;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.converter.Converter;
import javazoom.jl.player.Player;

public class MediaPlayer {

    private String filePath;
    private Player player;
    FileInputStream fis;
    BufferedInputStream bis;
    public long pauseLocation;
    public long songTotalLength;
    public String fileLocation;
    // constructor that takes the name of an MP3 file

    public MediaPlayer() {
    }

    public void Stop() {
        if (player != null) {
            player.close();
            pauseLocation = 0;
            songTotalLength = 0;
        }
    }

    public void Pause() {
        if (player != null) {
            try {
                pauseLocation = fis.available();
                player.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // play the MP3 file to the sound card
    public void play(String filename) {
        try {
            fis = new FileInputStream(filename);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
            songTotalLength = fis.available();
            fileLocation = filename;
        } catch (Exception e) {
            System.out.println("Problem playing file " + filePath);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread() {

            public void run() {
                try {
                    player.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void resume() {
        try {
            fis = new FileInputStream(fileLocation);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
            fis.skip(songTotalLength - pauseLocation);
        } catch (Exception e) {
            System.out.println("Problem playing file " + filePath);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread() {

            public void run() {
                try {
                    player.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // test client
    public static void main(String[] args) {
      //  String filename = "D:/work/project/MusicMoodDetection/MusicMoodDetection-Main/dataset/classic/02 Aayat (Bajirao Mastani)  - Arijit Singh.wav";
          String filename = "E:/Music/roy-2015[Songspk.CC]/1- Sooraj Dooba Hain - Roy - [Songspk.CC].mp3";
          
        MediaPlayer mp3 = new MediaPlayer();
        mp3.play(filename);

        // do whatever computation you like, while music plays
        int N = 4000;
        double sum = 0.0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += Math.sin(i + j);
            }
        }
        System.out.println(sum);

        // when the computation is done, stop playing it
        mp3.Stop();

        // play from the beginning
        mp3 = new MediaPlayer();
        mp3.play(filename);


    }
}
