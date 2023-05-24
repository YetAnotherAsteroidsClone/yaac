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
    public static BufferedImage rotateImage(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle));
        double cos = Math.abs(Math.cos(angle));
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        int newWidth = (int) Math.floor(originalWidth * cos + originalHeight * sin);
        int newHeight = (int) Math.floor(originalHeight * cos + originalWidth * sin);
        BufferedImage rotatedBI = new BufferedImage(newWidth, newHeight, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = rotatedBI.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate((newWidth - originalWidth) / 2, (newHeight - originalHeight) / 2);
        g2d.rotate(angle, originalWidth / 2, originalHeight / 2);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotatedBI;
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
