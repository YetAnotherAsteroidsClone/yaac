package com.yaac.view;

import com.yaac.model.SaveFileManager;
import com.yaac.view.Utility.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Classe singleton che gestisce i suoni del gioco e la musica <br>
 * Per segnalare al thread di riprodurre un suono, chiamare il metodo del suono che si vuole riprodurre <br>
 * Internamente verrà impostato l'indice dell'array di suoni disponibili da chiamare nel loop <br>
 * I metodi di loop modificano una variabile interna per segnalare al thread di riprodurre il suono in loop<br>
 * Internamente il thread utilizza una variabile enabled per capire se riprodurre o meno il suono <br>
 * Per segnalare al thread di fermare la riproduzione della musica, chiamare il metodo {@link #stopMusic() stopMusic} <br>
 * @see Sound
 */
public class SoundEngine {
    private ScheduledExecutorService executor;
    private static final SoundEngine instance = new SoundEngine();
    private final ArrayList<Sound> audio = new ArrayList<>(List.of(new Sound("SpaceshipFiring.wav"), new Sound("ExplosionV1.wav"), new Sound("ExplosionV2.wav"), new Sound("ExplosionV3.wav"), new Sound("Music.wav")));
    private int currentSound = -1;
    private boolean enabled = true;
    private boolean loop = false;
    private float volume = SaveFileManager.getInstance().getVolume();
    private float musicVolume = SaveFileManager.getInstance().getMusicVolume();

    private void run() {
        for(int i = 0; i < audio.size() - 1; i++)
            audio.get(i).setVolume(volume);
        audio.get(4).setVolume(musicVolume);
        if(executor != null)
            return;
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            synchronized (this) {
                if(currentSound != -1 && enabled)
                    playSound();
                else if (currentSound != -1)
                    stopSound();
            }
        }, 0, 60, TimeUnit.MILLISECONDS);
    }

    private void playSound(){
        if(loop) {
            audio.get(currentSound).loop();
            loop = false;
        }
        else
            audio.get(currentSound).play();
        currentSound = -1;
    }

    private void stopSound(){
        audio.get(currentSound).pause();
        currentSound = -1;
    }

    public static SoundEngine getInstance() {
        return instance;
    }

    public void playBullet() {
        synchronized (this) {
            currentSound = 0;
            enabled = true;
        }
    }

    public void playMusic(){
        synchronized (this) {
            currentSound = 4;
            enabled = true;
        }
    }

    /**
     * Metodo per riprodurre un suono di esplosione casuale
     */
    public void playExplosion() {
        synchronized (this) {
            currentSound = (int) (Math.random() * 3) + 1;
            enabled = true;
        }
    }

    public void loopMusic(){
        synchronized (this) {
            currentSound = 4;
            enabled = true;
            loop = true;
            // set music volume

        }
    }

    public void stopMusic(){
        synchronized (this) {
            currentSound = 4;
            enabled = false;
        }
    }

    public void increaseVolume(){
        synchronized (this) {
            for (int i = 0; i < audio.size()-1; i++)
                volume = audio.get(i).incrementVolume();
            SaveFileManager.getInstance().setVolume(volume);
        }
    }

    public void decreaseVolume(){
        synchronized (this) {
            for (int i = 0; i < audio.size()-1; i++)
                volume = audio.get(i).reduceVolume();
            SaveFileManager.getInstance().setVolume(volume);
        }
    }

    public void increaseMusicVolume(){
        synchronized (this) {
            musicVolume = audio.get(4).incrementVolume();
            SaveFileManager.getInstance().setMusicVolume(musicVolume);
        }
    }

    public void decreaseMusicVolume(){
        synchronized (this) {
            musicVolume = audio.get(4).reduceVolume();
            SaveFileManager.getInstance().setMusicVolume(musicVolume);
        }
    }

    public void start(){
        run();
    }

    public void stop(){
        executor.shutdown();
        executor = null;
    }
}
