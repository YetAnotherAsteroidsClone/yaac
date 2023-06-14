package com.yaac.view;

import com.yaac.model.Game;
import com.yaac.model.SaveFileManager;
import com.yaac.view.Utility.CompositeSprite;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.ObjectAnimation;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/** Classe per la gestione della grafica della navicella
 */
public class SpaceShipView {
    private final ArrayList<Image> bodies;
    private final ArrayList<ObjectAnimation> weapons;
    private final ArrayList<EngineView> engines;
    private final CompositeSprite spaceship;
    private int currentWeapon = SaveFileManager.getInstance().getWeapon();
    private int currentEngine = SaveFileManager.getInstance().getEngine();
    private int currentBody = 0;
    private boolean idleState = true;
    private boolean explosion = false;

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
        for(ObjectAnimation weapon : weapons)
            weapon.scaleImage(width, height);

        ArrayList<ObjectAnimation> locked_weapons = new ArrayList<>(List.of(
                new ObjectAnimation("/GameSprite/WeaponBigCannon_locked.png"),
                new ObjectAnimation("/GameSprite/WeaponRocket_locked.png"),
                new ObjectAnimation("/GameSprite/WeaponZapper_locked.png")
        ));
        for(ObjectAnimation weapon : locked_weapons)
            weapon.scaleImage(width, height);

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

        ArrayList<EngineView> locked_engines = new ArrayList<>(List.of(
                new EngineView(
                        null,
                        new ObjectAnimation("/GameSprite/BigEngine-Powering_locked.png"),
                        ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/BigEngine_locked.png"), width, height)
                ),
                new EngineView(
                        null,
                        new ObjectAnimation("/GameSprite/BurstEngine-Powering_locked.png"),
                        ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/BurstEngine_locked.png"), width, height)
                ),
                new EngineView(
                        null,
                        new ObjectAnimation("/GameSprite/SuperChargedEngine-Powering_locked.png"),
                        ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/SuperChargedEngine_locked.png"), width, height)
                )
        ));
        for(EngineView engine : locked_engines)
            engine.getPoweringState().scaleImage(width, height);
        boolean[] unlockedEngines = SaveFileManager.getInstance().getUnlockedEngines();
        for(int i = 0; i < unlockedEngines.length; i++) {
            if(!unlockedEngines[i]) {
                engines.get(i).setEngine(locked_engines.get(i - 1).getEngine());
                engines.get(i).setPoweringState(locked_engines.get(i - 1).getPoweringState());
            }
        }
        boolean[] unlockedWeapons = SaveFileManager.getInstance().getUnlockedWeapons();
        for(int i = 0; i < unlockedWeapons.length; i++) {
            if(!unlockedWeapons[i])
                weapons.set(i, locked_weapons.get(i-1));
        }

        ObjectAnimation shield = new ObjectAnimation("/GameSprite/PowerUpShield.png");
        shield.scaleImage(width, height);

        ObjectAnimation boost = new ObjectAnimation("/GameSprite/ShopBoost.png");
        boost.scaleImage(width, height);

        ObjectAnimation explosion = new ObjectAnimation("/GameSprite/BulletExplosionAnimation.png");
        explosion.scaleImage(width, height);

        /*
        * Indici:
        * Array animazioni: 0 = EnginePowering, 1 = EngineIdle, 2 = weapons, 3 = explosion
        * Array immagini: 0 = baseEngine
        * Array sprite: 0 = body1, 1 = body2, 2 = body3, 3 = body4
         */
        spaceship = new CompositeSprite(
                new ArrayList<>(List.of(engines.get(currentEngine).getPoweringState(), engines.get(currentEngine).getIdleState(), weapons.get(currentWeapon), explosion)),
                new ArrayList<>(List.of(engines.get(currentEngine).getEngine())),
                bodies,new ArrayList<>(List.of(shield, boost)));
        enableAnimations();
        //Se è stata attivata un'esplosione disattiva l'animazione dopo che è stata eseguita una volta
        spaceship.addDrawListener(()->{if(this.explosion && !Game.getInstance().getDeathPause()) setExplosion(false);});
        setExplosion(false);
    }

    /** Imposta l'animazione del motore
     * @param powering true se il motore è in uso, false altrimenti
     */
    public void setPowering(boolean powering) {
        if (powering) {
            spaceship.enableAnimation(0, true);
            spaceship.disableAnimation(1, false);
            idleState = false;
        }
        else {
            spaceship.disableAnimation(0, false);
            spaceship.enableAnimation(1, true);
            idleState = true;
        }
    }

    /** Imposta il motore della navicella
     * @param currentEngine indice del corpo
     */
    public void setCurrentEngine(int currentEngine) {
        this.currentEngine = currentEngine;
        spaceship.setAnimation(engines.get(this.currentEngine).getPoweringState(), 0);
        spaceship.setAnimation(engines.get(this.currentEngine).getIdleState(), 1);
        spaceship.setImage(engines.get(this.currentEngine).getEngine(), 0);
        setPowering(false);
    }

    /** Imposta l'animazione dell'arma corrente
     * @param shooting true se l'arma è in uso, false altrimenti
     */
    public void setCurrentWeaponAnimation(boolean shooting){
        if(!shooting)
            spaceship.disableAnimation(2, true);
        else
            spaceship.enableAnimation(2, true);
    }

    /** Imposta l'arma corrente
     * @param currentWeapon arma corrente
     */
    public void setCurrentWeapon(int currentWeapon) {
        this.currentWeapon = currentWeapon;
        spaceship.setAnimation(weapons.get(this.currentWeapon), 2);
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
        if(!idleState)
            spaceship.disableAnimation(1,false);
    }

    /** Imposta il motore corrente della navicella al precedente
     * se l'indice sfora il numero di motori disponibili, viene riportato all'inizio
     */
    public void previousEngine() {
        currentEngine = (currentEngine - 1 + engines.size()) % engines.size();
        spaceship.setImage(engines.get(currentEngine).getEngine(), 0);
        spaceship.setAnimation(engines.get(currentEngine).getPoweringState(), 0);
        spaceship.setAnimation(engines.get(currentEngine).getIdleState(), 1);
        if(!idleState)
            spaceship.disableAnimation(1,false);
    }

    public void setBody(int index){
        currentBody = index % bodies.size();
        spaceship.setCurrentSprite(currentBody);
    }

    public void nextBody(){
        currentBody = (currentBody + 1) % bodies.size();
        spaceship.setCurrentSprite(currentBody);
    }

    /** Imposta l'animazione dello scudo della navicella
     * @param shield true se lo scudo dev'essere attivato
     */
    public void setShield(boolean shield){
        if(shield)
            spaceship.enableOverlayAnimation(0, true);
        else
            spaceship.disableOverlayAnimation(0, false);
    }

    public void setBoost(boolean boost){
        if(boost)
            spaceship.enableOverlayAnimation(1, true);
        else
            spaceship.disableOverlayAnimation(1, false);
    }

    /** Ritorna l'indice del motore corrente
     */
    public int getCurrentWeapon() {
        return currentWeapon;
    }

    /** Ritorna l'indice del motore corrente
     * @return
     */
    public int getCurrentEngine() {
        return currentEngine;
    }

    /** Disabilita tutte le animazioni della navicella
     */
    private void disableAnimations(){
        spaceship.disableAnimation(0, false);
        spaceship.disableAnimation(1, false);
        spaceship.disableAnimation(2, false);
        spaceship.setSprite(false);
        spaceship.setImages(false);
    }

    /** Abilita le animazioni della navicella
     */
    public void enableAnimations(){
        setPowering(false);
        setCurrentWeaponAnimation(false);
        setShield(false);
        setBoost(false);
        spaceship.setSprite(true);
        spaceship.setImages(true);
    }

    /** Imposta l'animazione dell'esplosione della navicella
     * @param explosion true se bisogna attivare l'animazione di esplosione
     */
    public void setExplosion(boolean explosion){
        if(explosion) {
            spaceship.enableAnimation(3, true);
            disableAnimations();
            this.explosion = true;
        }
        else {
            spaceship.disableAnimation(3, false);
            enableAnimations();
            this.explosion = false;
        }
    }
}
