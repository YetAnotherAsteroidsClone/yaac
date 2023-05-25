package com.yaac.view.Utility;

import com.yaac.Settings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  Classe dedicata alla gestione delle animazioni degli oggetti
 * */
public class ObjectAnimation {

    private ArrayList<Image> images = new ArrayList<>();
    private int index = 0;

    /**
     *  Costruttore della classe ObjectAnimation con immagini quadrate
     *  @param source percorso dell'immagine
     */
    public ObjectAnimation(String source) {
        images = ImageUtility.loadAnimationFrames(source);
    }

    /**
     *  Costruttore della classe ObjectAnimation con immagini rettangolari
     *  @param source percorso dell'immagine
     *  @param width larghezza
     *  @param height altezza
     */
    public ObjectAnimation(String source, int width, int height) {
        images = ImageUtility.loadAnimationFrames(source, width, height);
    }

    /**
     * Metodo per scalare le animazioni
     *  @param width larghezza
     *  @param height altezza
     */
    public void scaleImage(int width, int height) {
        for (int i = 0; i < images.size(); i++) {
            images.set(i, images.get(i).getScaledInstance(width, height, Image.SCALE_SMOOTH));
        }
    }

    public Image getImage(int index) {
        return images.get(index);
    }

    /**
     * Metodo per aggiornare l'animazione allo step successivo
     */
    public void update() {
        index = (index+1) % images.size();
    }

    /**
     * Metodo per ottenere l'immagine corrente
     * @return immagine corrente
     */
    public Image getCurrentFrame() {
        return images.get(index);
    }

    public int size() {
        return images.size();
    }
}