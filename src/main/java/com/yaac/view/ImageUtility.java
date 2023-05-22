package com.yaac.view;

import com.yaac.Settings;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe delle utility per il caricamento e la manipolazione delle immagini
 */
public class ImageUtility {
    public static BufferedImage loadImage(String source) throws IOException{
        return ImageIO.read(new File(Settings.resourcePath + source));
    }

    /**
     * Metodo per il caricamento di un animazione
     * @param obj nome della cartella in cui si trova l'immagine
     * @param name nome dell'immagine
     * @param nFrames numero di frame dell'animazione
     * @param width larghezza di un frame
     * @param height altezza di un frame
     * @return un arrayList di immagini (animazione)
     */
    public static ArrayList<BufferedImage> loadAnimationFrames(String obj, String name, int nFrames, int width, int height){
        String source = "GameSprite/" + obj + "/" + name + ".png";
        BufferedImage animationFile = null;
        try {
            animationFile = loadImage(source);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<BufferedImage> frames = new ArrayList<>();
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
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = image.getWidth();
        int h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin);
        int newh = (int)Math.floor(h*cos+w*sin);
        BufferedImage result = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB);
        result = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB);
        result.getGraphics().drawImage(image, (neww-w)/2, (newh-h)/2, null);
        return result;
    }

}
