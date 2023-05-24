package com.yaac.view.Utility;

import com.yaac.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe delle utility per il caricamento e la manipolazione delle immagini
 */
public class ImageUtility {
    public static BufferedImage loadImage(String source){
        try {//Main.class.getResource() punta alla cartella resources (è necessario specificare la radice "/")
            return ImageIO.read(Objects.requireNonNull(Main.class.getResource(source)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo per il caricamento di un animazione
     * @param obj nome della cartella in cui si trova l'immagine
     * @param name nome dell'immagine
     * @param nFrames numero di frame dell'animazione
     * @param width larghezza di un frame
     * @param height altezza di un frame
     * @return un arrayList d'immagini (animazione)
     */
    public static ArrayList<Image> loadAnimationFrames(String obj, String name, int nFrames, int width, int height){
        String source = "GameSprite/" + obj + "/" + name + ".png";
        BufferedImage animationFile = loadImage(source);
        ArrayList<Image> frames = new ArrayList<>();
        for (int i = 0; i < nFrames; i++) {

            frames.add(animationFile.getSubimage(i * width, 0, width, height));
        }
        return frames;
    }

    /**
     * Metodo per ruotare un'immagine
     * @param image immagine da ruotare
     * @param angle angolo di rotazione
     * @return l'immagine ruotata
     */
    public static BufferedImage rotateImage(BufferedImage image, double angle){
        double sin = Math.abs(Math.sin(Math.toRadians(angle)));
        double cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = image.getWidth();
        int h = image.getHeight();
        int newW = (int)Math.floor(w*cos+h*sin);
        int newH = (int)Math.floor(h*cos+w*sin);
        BufferedImage result;
        result = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        result.getGraphics().drawImage(image, (newW-w)/2, (newH-h)/2, null);
        return result;
    }

    /**
     * Metodo per scalare un'immagine
     * @param image immagine da scalare
     * @param width larghezza
     * @param height altezza
     * @return l'immagine scalata
     */
    public static BufferedImage scaleImage(BufferedImage image, int width, int height) {
        Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
