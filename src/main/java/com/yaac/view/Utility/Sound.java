package com.yaac.view.Utility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Objects;

public class Sound {
    private AudioInputStream audioIn;
    private Clip clip;
    public Sound(String name) { //Riceve come parametro il nome di una risorsa .wav da riprodurre.
        try {
            audioIn = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream("/Sounds/" + name)));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (Exception e) {
            System.out.println("Errore nella riproduzione del suono: " + name);
            clip = null;
        }
    }
    public void loop() { //Riproduzione continua
        if (clip != null)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void play() { //Riproduce il file
        if (clip != null) {
            if (clip.getFramePosition() == clip.getFrameLength())
                clip.setFramePosition(0);
            clip.start();
        }
    }
    public void pause() { //Interrompe la riproduzione
        if (clip != null)
            clip.stop();
    }
    public void restart() { //Riproduce dall’inizio
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }
    public void reduceVolume() { //Abbassa il volume
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float value = gainControl.getValue();
            value -= 1.0f;
            if (value >= gainControl.getMinimum())
                gainControl.setValue(value);
        }
    }
    public void incrementVolume() { //Aumenta il volume
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float value = gainControl.getValue();
            value += 1.0f;
            if (value <= gainControl.getMaximum())
                gainControl.setValue(value);
        }
    }

    public void setVolume(float value) { //Imposta il volume
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (value >= gainControl.getMinimum() && value <= gainControl.getMaximum())
                gainControl.setValue(value);
        }
    }
} //Chiude la definizione della classe Sound

