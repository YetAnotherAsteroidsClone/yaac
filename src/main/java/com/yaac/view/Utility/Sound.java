package com.yaac.view.Utility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Objects;

/**
 * Classe che gestisce la riproduzione dei suoni.
 */
public class Sound {

    private AudioInputStream audioIn;
    private Clip clip;

    /**
     * Costruttore della classe Sound.
     * @param name nome del file audio da riprodurre.
     */
    public Sound(String name) {
        try {
            audioIn = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream("/Sounds/" + name)));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (Exception e) {
            System.out.println("Errore nella riproduzione del suono: " + name);
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
     * Metodo che riduce il volume del file audio di un'unità.
     * @return valore del volume dopo la riduzione.
     */
    public float reduceVolume() {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float value = gainControl.getValue();
            // Pseudo regolare cambio volume
            if(value <= -13)
                value=-80;
            else if(value <= -10)
                value = -13;
            else
                value -= 1.0f;
            if (value >= gainControl.getMinimum())
                gainControl.setValue(value);
            return value;
        }
        return 0;
    }

    /**
     * Metodo che aumenta il volume del file audio di un'unità.
     * @return valore del volume dopo l'aumento.
     */
    public float incrementVolume() {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float value = gainControl.getValue();
            // Pseudo regolare cambio volume
            if(value == -80)
                value = -13;
            else if(value == -13)
                value = -10;
            else if(value <= 0 && value >= -1)
                value = 0;
            else
                value += 2.0f;
            if (value <= 0) // 0db = 100%
                gainControl.setValue(value);
            return value;
        }
        return 0;
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

