package com.yaac.view.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import com.yaac.model.Utility.DrawListener;

/** Classe che rappresenta un oggetto composto da più immagini
 * Utilizzata per la gestione delle animazioni e gli sprites
 */
public class CompositeSprite {

    private final ArrayList<DrawListener> drawListeners;

    /** ArrayList contenente le animazioni
     */
    private final ArrayList<ObjectAnimation> animations;
    /** ArrayList contenente le animazioni disegnate sopra lo sprite
     */
    private final ArrayList<ObjectAnimation> overlayAnimations;
    /** ArrayList contenente le immagini
     */
    private final ArrayList<Image> images;
    /** Immagini abilitate
     */
    private boolean imagesEnabled;
    /** ArrayList contenente gli sprites
     */
    private final ArrayList<Image> sprites;
    /** Sprite abilitato
     */
    private boolean spriteEnabled;
    /** Indice dello sprite corrente
     */
    private int currentSprite = 0;

    /** Costruttore della classe
     * @param animations ArrayList contenente le animazioni
     * @param images ArrayList contenente le immagini
     * @param sprites ArrayList contenente gli sprites
     */
    public CompositeSprite(ArrayList<ObjectAnimation> animations, ArrayList<Image> images, ArrayList<Image> sprites, ArrayList<ObjectAnimation> overlayAnimations) {
        this.animations = animations;
        this.images = images;
        this.sprites = sprites;
        this.overlayAnimations = overlayAnimations;
        this.drawListeners = new ArrayList<>();
        this.spriteEnabled = true;
        this.imagesEnabled = true;
    }

    /** Metodo per il disegno dello sprite corrente
     * @return l'immagine dello sprite corrente
     */
    public Image draw(){
        BufferedImage sprite = new BufferedImage(sprites.get(currentSprite).getWidth(null), sprites.get(currentSprite).getHeight(null), BufferedImage.TRANSLUCENT);
        Graphics2D g2d = sprite.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(ObjectAnimation animation : animations){
            animDraw(g2d, animation);
        }
        for(Image image : images)
            if(imagesEnabled)
                g2d.drawImage(image, 0, 0, null);
        if(spriteEnabled)
            g2d.drawImage(sprites.get(currentSprite), 0, 0, null);
        for(ObjectAnimation animation : overlayAnimations)
            animDraw(g2d, animation);
        g2d.dispose();
        for(DrawListener drawListener : drawListeners)
            drawListener.drawAction();
        return sprite;
    }

    private void animDraw(Graphics2D g2d, ObjectAnimation animation){
        if(animation.isEnabled() && animation.isDrawable()) {
            g2d.drawImage(animation.getCurrentFrame(), 0, 0, null);
            animation.update();
        }else if(animation.isDrawable()){
            g2d.drawImage(animation.getDefaultImage(), 0, 0, null);
        }
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

    /** Imposta l'immagine alla posizione index
     * @param image
     * @param index
     */
    public void setImage(Image image, int index){
        images.set(index, image);
    }

    /** Metodo per l'aggiornamento dello sprite corrente allo sprite successivo
     *  se l'indice sfora il numero di sprites disponibili, viene riportato all'inizio
     */
    public void nextSprite(){
        currentSprite = (currentSprite + 1) % sprites.size();
    }

    /** Metodo per l'aggiornamento dello sprite corrente
     * @param index
     */
    public void setCurrentSprite(int index){
        currentSprite = index;
    }

    /** Metodo wrapper per abilitare l'animazione
     * @param index posizione dell'animazione da abilitare/disabilitare
     * @param drawable indica se l'animazione deve essere disegnata o meno
     */
    public void enableAnimation(int index, boolean drawable){
        animations.get(index).enable();
        animations.get(index).setDrawable(drawable);
    }

    /** Metodo wrapper per disabilitare l'animazione
     * @param index posizione dell'animazione da abilitare/disabilitare
     * @param drawable indica se l'animazione deve essere disegnata o meno
     */
    public void disableAnimation(int index, boolean drawable) {
        animations.get(index).disable();
        animations.get(index).setDrawable(drawable);
    }

    /** Metodo wrapper per abilitare l'animazione dell'overlay
     * @param index posizione dell'animazione da abilitare/disabilitare
     * @param drawable indica se l'animazione deve essere disegnata o meno
     */
    public void enableOverlayAnimation(int index, boolean drawable){
        overlayAnimations.get(index).enable();
        overlayAnimations.get(index).setDrawable(drawable);
    }

    /** Metodo wrapper per disabilitare l'animazione dell'overlay
     * @param index posizione dell'animazione da abilitare/disabilitare
     * @param drawable indica se l'animazione deve essere disegnata o meno
     */
    public void disableOverlayAnimation(int index, boolean drawable) {
        overlayAnimations.get(index).disable();
        overlayAnimations.get(index).setDrawable(drawable);
    }

    public void addDrawListener(DrawListener drawListener){
        drawListeners.add(drawListener);
    }

    /** Imposta lo stato dello sprite (se da disegnare o meno)
     * @param spriteEnabled true se va disegnato, false altrimenti
     */
    public void setSprite(boolean spriteEnabled) {this.spriteEnabled = spriteEnabled;}

    /** Imposta lo stato delle immagini (se da disegnare o meno)
     * @param imagesEnabled true se va disegnato, false altrimenti
     */
    public void setImages(boolean imagesEnabled) {this.imagesEnabled = imagesEnabled;}
}
