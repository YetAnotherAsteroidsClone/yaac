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

    private final ArrayList<Image> images = new ArrayList<>();
    private int index = 0;

    /**
     *  Costruttore della classe ObjectAnimation
     *  @param style stile dell'oggetto
     *                 (es. "Default", "Speciale")
     *  @param object oggetto
     *                 (es. "spaceship", "asteroid", projectile")
     *  @param action azione dell'oggetto
     *                 (es. "idle", "move", "shoot")
     *  @param numberOfElements numero di elementi dell'animazione
     *                 (es. 3, 4, 5)
     */
    public ObjectAnimation(String style, String object, String action, int numberOfElements) {
        try {
            String path = Settings.resourcePath + style + "/" + object + "/";
            for (int i = 1; i <= numberOfElements; i++) {
                Image img = ImageIO.read(getClass().getResourceAsStream(path + action + i + ".png"));
                images.add(img);
            }
        } catch(IOException exception) {
            System.out.println("Error loading image: " + exception.getMessage());
        }
    }

    /**
     * Metodo per scalare le animazioni
     *  @param width larghezza
     *  @param height altezza
     */
    public void scaleImage(int width, int height) {
        for (Image img : images) {
            img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
    }


    /**
     * Metodo per aggiornare l'animazione allo step successivo
     *  @return immagine
     */
    public Image update() {
        index = (index+1) % images.size();
        return images.get(index);
    }
}