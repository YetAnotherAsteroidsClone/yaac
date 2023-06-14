package com.yaac.view.Utility;

import com.yaac.Settings;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Classe che gestisce la riproduzione dei suoni.
 */
@SuppressWarnings("unused")
public class Sound {

    private Clip clip;

    /**
     * Costruttore della classe Sound.
     * @param name nome del file audio da riprodurre.
     */
    public Sound(String name) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(Sound.class.getResourceAsStream("/Sounds/" + name))));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            Settings.LOGGER.log(Level.INFO,"Errore nella riproduzione del suono: " + name);
            e.printStackTrace();
            clip = null;
        }
    }

    /**
     * Metodo che riproduce il file audio in loop.
     */
    public void loop() {
        if (clip != null)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Metodo che riproduce il file audio.
     */
    public void play() {
        if (clip != null) {
            if (clip.getFramePosition() == clip.getFrameLength())
                clip.setFramePosition(0);
            clip.start();
        }
    }

    /**
     * Metodo che interrompe la riproduzione del file audio.
     */
    public void pause() {
        if (clip != null)
            clip.stop();
    }

    /**
     * Metodo che riproduce il file audio dall'inizio.
     */
    public void restart() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    /**
     * Metodo che imposta il volume del file audio.
     * @param value valore del volume da impostare.
     */
    public void setVolume(float value) { //Imposta il volume
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (value >= gainControl.getMinimum() && value <= gainControl.getMaximum())
                gainControl.setValue(value);
        }
    }

    /**
     * Metodo che restituisce il volume in percentuale.
     * @param db valore del volume in Decibel.
     * @return valore del volume.
     */
    public static int decibelPercentage(float db){
        // Decibel to percentage
        return (int) (Math.pow(10, db / 20) * 100);
    }
}

