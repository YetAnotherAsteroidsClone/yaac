package com.yaac.view.Utility;

import com.yaac.Main;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
     * Metodo per il caricamento di un animazione quadrata
     * @param source nome della cartella in cui si trova l'immagine
     * @return un arrayList d'immagini (animazione)
     */
    public static ArrayList<Image> loadAnimationFrames(String source){
        BufferedImage animationFile = loadImage(source);
        int nFrames = animationFile.getWidth() / animationFile.getHeight();
        ArrayList<Image> frames = new ArrayList<>();
        for (int i = 0; i < nFrames; i++)
            frames.add(animationFile.getSubimage(i * animationFile.getHeight(), 0, animationFile.getHeight(), animationFile.getHeight()));
        return frames;
    }

    /**
     * Metodo per il caricamento di un animazione rettangolare
     * @param source nome della cartella in cui si trova l'immagine
     * @param width larghezza dell'immagine
     * @param height altezza dell'immagine
     * @return un arrayList d'immagini (animazione)
     */
    public static ArrayList<Image> loadAnimationFrames(String source, int width, int height){
        BufferedImage animationFile = loadImage(source);
        int nFrames = animationFile.getWidth() / width;
        ArrayList<Image> frames = new ArrayList<>();
        for (int i = 0; i < nFrames; i++)
            frames.add(animationFile.getSubimage(i * width, 0, width, height));
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
        //Conversione dell'angolo in gradi ad angolo in radianti (angle*PI/180)
        g2d.rotate(Math.toRadians(angle), originalWidth / 2, originalHeight / 2);
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

    public static BufferedImage ImageToBuffered(Image im){
        BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }
}
