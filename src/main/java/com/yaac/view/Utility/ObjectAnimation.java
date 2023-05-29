package com.yaac.view.Utility;

import java.awt.Image;
import java.util.ArrayList;

/**
 *  Classe dedicata alla gestione delle animazioni degli oggetti
 * */
public class ObjectAnimation {

    private boolean enabled = true;
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
        if(enabled)
            index = (index+1) % images.size();
        else
            index = 0;
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

    public Image getDefaultImage() {
        return images.get(0);
    }

    /** Metodo per disabilitare l'animazione
     */
    public void disable() {
        enabled = false;
    }

    /** Metodo per abilitare l'animazione
     */
    public void enable() {
        enabled = true;
    }
}