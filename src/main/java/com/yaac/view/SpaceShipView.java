package com.yaac.view;

import com.yaac.view.Utility.CompositeSprite;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.ObjectAnimation;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/** Classe per la gestione della grafica della navicella
 */
public class SpaceShipView {
    private ArrayList<Image> bodies;
    private ArrayList<ObjectAnimation> weapons;
    private ArrayList<Image> engines;
    private Image baseEngine;
    private CompositeSprite spaceship;
    private ObjectAnimation baseEnginePowering;

    private int currentWeapon = 0;

    /** Costruttore della classe SpaceShipView
     * @param width larghezza
     * @param height altezza
     */
    public SpaceShipView(int width, int height) {
        bodies = new ArrayList<>(List.of(
                ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/Body1.png"), width, height),
                ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/Body2.png"), width, height),
                ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/Body3.png"), width, height),
                ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/Body4.png"), width, height)
        ));
        weapons = new ArrayList<>(List.of(
                new ObjectAnimation("/GameSprite/WeaponBaseCannon.png"),
                new ObjectAnimation("/GameSprite/WeaponBigCannon.png"),
                new ObjectAnimation("/GameSprite/WeaponRocket.png"),
                new ObjectAnimation("/GameSprite/WeaponZapper.png")
        ));
        for(ObjectAnimation weapon : weapons) {
            weapon.scaleImage(width, height);
            weapon.disable();
        }
        baseEngine = ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/BaseEngine.png"), width, height);
        baseEnginePowering = new ObjectAnimation("/GameSprite/BaseEngine-Powering.png");
        baseEnginePowering.scaleImage(width, height);

        /*
        * Indici:
        * Array animazioni: 0 = EnginePowering/EngineIdle, 1 = weapons
        * Array immagini: 0 = baseEngine
        * Array sprite: 0 = bodies
         */
        spaceship = new CompositeSprite(
                new ArrayList<>(List.of(baseEnginePowering, weapons.get(currentWeapon))),
                new ArrayList<>(List.of(baseEngine)),
                new ArrayList<>(List.of(bodies.get(0))));
    }

    /** Imposta l'animazione del motore
     * @param powering true se il motore è in uso, false altrimenti
     */
    public void setPowering(boolean powering) {
        if (powering)
            spaceship.enableAnimation(0);
        else
            spaceship.disableAnimation(0);
    }

    /** Imposta l'animazione dell'arma corrente
     * @param shooting true se l'arma è in uso, false altrimenti
     */
    public void setCurrentWeapon(boolean shooting){
        if(!shooting)
            spaceship.disableAnimation(1);
        else
            spaceship.enableAnimation(1);
    }

    /** Ritorna la navicella
     * @return CompositeSprite navicella
     */
    public CompositeSprite getSpaceship() {
        return spaceship;
    }

    /** Imposta l'arma corrente della navicella alla successiva
     *  se l'indice sfora il numero di armi disponibili, viene riportato all'inizio
     */
    public void nextWeapon(){
        currentWeapon = (currentWeapon + 1) % weapons.size();
        spaceship.setAnimation(weapons.get(currentWeapon),1);
    }

    /** Imposta l'arma corrente della navicella alla precedente
     *  se l'indice sfora il numero di armi disponibili, viene riportato all'inizio
     */
    public void previousWeapon() {
        currentWeapon = (currentWeapon - 1 + weapons.size()) % weapons.size();
        spaceship.setAnimation(weapons.get(currentWeapon), 1);
    }
}
