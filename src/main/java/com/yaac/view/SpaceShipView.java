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
    private ArrayList<EngineView> engines;
    private CompositeSprite spaceship;
    private int currentWeapon = 0;
    private int currentEngine = 0;
    private int currentBody = 0;

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
        engines = new ArrayList<>(List.of(
                new EngineView(
                        new ObjectAnimation("/GameSprite/BaseEngine-Idle.png"),
                        new ObjectAnimation("/GameSprite/BaseEngine-Powering.png"),
                        ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/BaseEngine.png"), width, height)
                ),
                new EngineView(
                        new ObjectAnimation("/GameSprite/BigEngine-Idle.png"),
                        new ObjectAnimation("/GameSprite/BigEngine-Powering.png"),
                        ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/BigEngine.png"), width, height)
                ),
                new EngineView(
                        new ObjectAnimation("/GameSprite/BurstEngine-Idle.png"),
                        new ObjectAnimation("/GameSprite/BurstEngine-Powering.png"),
                        ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/BurstEngine.png"), width, height)
                ),
                new EngineView(
                        new ObjectAnimation("/GameSprite/SuperChargedEngine-Idle.png"),
                        new ObjectAnimation("/GameSprite/SuperChargedEngine-Powering.png"),
                        ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/SuperChargedEngine.png"), width, height)
                )
        ));
        for(EngineView engine : engines) {
            engine.getPoweringState().scaleImage(width, height);
            engine.getIdleState().scaleImage(width, height);
        }

        /*
        * Indici:
        * Array animazioni: 0 = EnginePowering, 1 = EngineIdle, 2 = weapons
        * Array immagini: 0 = baseEngine
        * Array sprite: 0 = body1, 1 = body2, 2 = body3, 3 = body4
         */
        spaceship = new CompositeSprite(
                new ArrayList<>(List.of(engines.get(currentEngine).getPoweringState(), engines.get(currentEngine).getIdleState(), weapons.get(currentWeapon))),
                new ArrayList<>(List.of(engines.get(currentEngine).getEngine())),
                bodies);
        setPowering(false);
    }

    /** Imposta l'animazione del motore
     * @param powering true se il motore è in uso, false altrimenti
     */
    public void setPowering(boolean powering) {
        if (powering) {
            spaceship.enableAnimation(0);
            spaceship.disableAnimation(1);
        }
        else {
            spaceship.disableAnimation(0);
            spaceship.enableAnimation(1);
        }
    }

    /** Imposta l'animazione dell'arma corrente
     * @param shooting true se l'arma è in uso, false altrimenti
     */
    public void setCurrentWeapon(boolean shooting){
        if(!shooting)
            spaceship.disableAnimation(2);
        else
            spaceship.enableAnimation(2);
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
        spaceship.setAnimation(weapons.get(currentWeapon),2);
    }

    /** Imposta l'arma corrente della navicella alla precedente
     *  se l'indice sfora il numero di armi disponibili, viene riportato all'inizio
     */
    public void previousWeapon() {
        currentWeapon = (currentWeapon - 1 + weapons.size()) % weapons.size();
        spaceship.setAnimation(weapons.get(currentWeapon), 2);
    }

    /** Imposta il motore corrente della navicella al successivo
     * se l'indice sfora il numero di motori disponibili, viene riportato all'inizio
     */
    public void nextEngine(){
        currentEngine = (currentEngine + 1) % engines.size();
        spaceship.setImage(engines.get(currentEngine).getEngine(), 0);
        spaceship.setAnimation(engines.get(currentEngine).getPoweringState(), 0);
        spaceship.setAnimation(engines.get(currentEngine).getIdleState(), 1);
    }

    /** Imposta il motore corrente della navicella al precedente
     * se l'indice sfora il numero di motori disponibili, viene riportato all'inizio
     */
    public void previousEngine() {
        currentEngine = (currentEngine - 1 + engines.size()) % engines.size();
        spaceship.setImage(engines.get(currentEngine).getEngine(), 0);
        spaceship.setAnimation(engines.get(currentEngine).getPoweringState(), 0);
        spaceship.setAnimation(engines.get(currentEngine).getIdleState(), 1);
    }

    public void setBody(int index){
        currentBody = index % bodies.size();
        spaceship.setCurrentSprite(currentBody);
    }

    public void nextBody(){
        currentBody = (currentBody + 1) % bodies.size();
        spaceship.setCurrentSprite(currentBody);
    }
}
