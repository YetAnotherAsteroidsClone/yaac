package com.yaac.view.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/** Classe che rappresenta un oggetto composto da più immagini
 * Utilizzata per la gestione delle animazioni e gli sprites
 */
public class CompositeSprite {

    /** ArrayList contenente le animazioni
     */
    private final ArrayList<ObjectAnimation> animations;
    /** ArrayList contenente le immagini
     */
    private final ArrayList<Image> images;
    /** ArrayList contenente gli sprites
     */
    private final ArrayList<Image> sprites;
    /** Indice dello sprite corrente
     */
    private int currentSprite = 0;

    /** Costruttore della classe
     * @param animations ArrayList contenente le animazioni
     * @param images ArrayList contenente le immagini
     * @param sprites ArrayList contenente gli sprites
     */
    public CompositeSprite(ArrayList<ObjectAnimation> animations, ArrayList<Image> images, ArrayList<Image> sprites) {
        this.animations = animations;
        this.images = images;
        this.sprites = sprites;
    }

    /** Metodo per il disegno dello sprite corrente
     * @return l'immagine dello sprite corrente
     */
    public Image draw(){
        BufferedImage sprite = new BufferedImage(sprites.get(currentSprite).getWidth(null), sprites.get(currentSprite).getHeight(null), BufferedImage.TRANSLUCENT);
        Graphics2D g2d = sprite.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(ObjectAnimation animation : animations){
            g2d.drawImage(animation.getCurrentFrame(), 0,0, null);
            animation.update();
        }
        for(Image image : images)
            g2d.drawImage(image, 0, 0, null);
        g2d.drawImage(sprites.get(currentSprite), 0, 0, null);
        g2d.dispose();
        return sprite;
    }

    /** Metodo per l'aggiunta di un'animazione
     * @param animation animazione da aggiungere
     */
    public void addAnimation(ObjectAnimation animation){
        animations.add(animation);
    }

    /** Imposta l'animazione alla posizione index
     * @param animation animazione da impostare
     * @param index posizione in cui impostare l'animazione
     */
    public void setAnimation(ObjectAnimation animation, int index){
        animations.set(index, animation);
    }

    /** Rimuove un'animazione dlla posizione index
     * @param index posizione in cui rimuovere l'animazione
     */
    public void removeAnimation(int index){
        animations.remove(index);
    }

    /** Metodo per l'aggiunta di un'immagine
     * @param image immagine da aggiungere
     */
    public void addImage(Image image){
        images.add(image);
    }

    /** Rimuove un'immagine dlla posizione index
     * @param index posizione in cui rimuovere l'immagine
     */
    public void removeImage(int index){
        images.remove(index);
    }

    /** Metodo per l'aggiornamento dello sprite corrente
     *  se l'indice sfora il numero di sprites disponibili, viene riportato all'inizio
     */
    public void setCurrentSprite(){
        currentSprite = (currentSprite + 1) % sprites.size();
    }

    /** Metodo wrapper per abilitare l'animazione
     * @param index posizione dell'animazione da abilitare/disabilitare
     */
    public void enableAnimation(int index){
        animations.get(index).enable();
    }

    /** Metodo wrapper per disabilitare l'animazione
     * @param index posizione dell'animazione da abilitare/disabilitare
     */
    public void disableAnimation(int index) {
        animations.get(index).disable();
    }
}
