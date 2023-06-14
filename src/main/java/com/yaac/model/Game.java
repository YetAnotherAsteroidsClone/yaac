package com.yaac.model;

import com.yaac.Settings;
import com.yaac.model.GameComponent.*;
import com.yaac.model.Utility.*;
import com.yaac.view.SoundEngine;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;

public class Game {
    static Game instance = null;
    private final ArrayList<OnDeathListener> onDeathListeners;
    private final ArrayList<GameOverListener> gameOverListeners;
    private final ArrayList<OnShieldStatusChangedListener> onShieldStatusChangedListeners;
    private final ArrayList<OnBoostStatusChangedListener> onBoostStatusChangedListeners;
    GameComponentsManager asteroids;
    GameComponentsManager destroyedAsteroids;
    GameComponentsManager bullets;
    GameComponentsManager gems;
    GameComponentsManager destroyedBullets;
    SpaceShip spaceShip;
    static boolean speedBoostActivated;
    static boolean shieldActivated;
    int gemCount;
    int scoreCount;
    int lives;
    boolean pwUpShield;
    boolean pwUpSpeed;
    int tick;
    int tickShield;
    int tickBoost;
    int stageTickTransition;
    int stage;
    boolean stagePause;
    boolean deathPause;
    int deathTick;

    public static double isBoostActivated() {
        return speedBoostActivated ? 1.0 : 0.0;
    }

    public static boolean isShieldActivated() {
        return shieldActivated;
    }

    public boolean getStagePause() {
        return stagePause;
    }
    public boolean getDeathPause() {
        return deathPause;
    }

    /**
     * Costruttore privato per implementare il pattern Singleton
     */
    private Game() {
        onDeathListeners = new ArrayList<>();
        gameOverListeners = new ArrayList<>();
        onShieldStatusChangedListeners = new ArrayList<>();
        onBoostStatusChangedListeners = new ArrayList<>();
        stage = SaveFileManager.getInstance().getCheckPoint();
        stagePause = true;
        tick = 0;
        stageTickTransition =50;
        deathTick = 10;
        this.spaceShip = new SpaceShip(Settings.width/2,Settings.height/2);
        bullets = new GameComponentsManager();
        asteroids = new GameComponentsManager();
        gems = new GameComponentsManager();
        destroyedAsteroids = new GameComponentsManager();
        destroyedBullets = new GameComponentsManager();
        lives = GameConstraints.getInstance().getLife();
        gemCount = SaveFileManager.getInstance().getCurrentGems();
        updatePwUpShield();
        updatePwUpBoost();
        tickShield = 0;
        tickBoost = 0;
        speedBoostActivated = false;
        shieldActivated = false;
    }

    /**
     *  Metodo per ottenere l'istanza della classe Game
     */
    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }

    // Getters
    public int getScore() {
        return scoreCount;
    }
    public int getLives() {
        return lives;
    }
    public GameComponentsManager getBullets() {
        return bullets;
    }
    public GameComponentsManager getAsteroids() {
        return asteroids;
    }
    public GameComponentsManager getDestroyedAsteroids() {
        return destroyedAsteroids;
    }
    public GameComponentsManager getDestroyedBullets() {
        return destroyedBullets;
    }
    public GameComponentsManager getGems() {
        return gems;
    }
    public SpaceShip getSpaceShip(){
        return spaceShip;
    }
    public int getStage(){
        return stage;
    }
    public int getGemCount(){
        return gemCount;
    }

    //Metodi per gestire i comandi impartiti dal giocatore
    public void startRotateRight(){
        if(!deathPause)
            spaceShip.startRotatingRight();
    }
    public void startRotateLeft(){
        if(!deathPause)
            spaceShip.startRotatingLeft();
    }
    public void startAccelerate(){
        if(!deathPause)
            spaceShip.startAccelerating();
    }
    public void startShoot(){
        if(!deathPause)
            spaceShip.startShooting();
        if(deathPause && spaceShip.isShooting())
            stopShot();
    }
    public void stopRotateRight(){
        spaceShip.stopRotatingRight();
    }
    public void stopRotateLeft(){
        spaceShip.stopRotatingLeft();
    }
    public void stopAccelerate(){
        spaceShip.stopAccelerating();
    }
    public void stopShot(){
        spaceShip.stopShooting();
    }
    public void activateShield(){
        if (pwUpShield && !shieldActivated) {
            GameConstraints.getInstance().setShopShield(false);
            shieldActivated = true;
            Settings.LOGGER.log(Level.INFO, "Shield activated");
            tickShield = 0;
            for(OnShieldStatusChangedListener listener : onShieldStatusChangedListeners)
                listener.onShieldStatusChanged();
        }
    }
    public void activateBoost(){
        if (pwUpSpeed && !speedBoostActivated) {
            GameConstraints.getInstance().setShopBoost(false);
            speedBoostActivated = true;
            Settings.LOGGER.log(Level.INFO, "Boost activated");
            tickBoost = 0;
            for(OnBoostStatusChangedListener listener : onBoostStatusChangedListeners)
                listener.onBoostStatusChanged();
        }
    }

    /**
     * Metodo per aggiornare lo stato del gioco
     */
    public void update(){
        // Update delle componenti del gioco
        spaceShip.update();
        bullets.update();
        asteroids.update();
        destroyedAsteroids.update();
        destroyedBullets.update();
        gems.update();

        // Generazione degli asteroidi
        if (!deathPause && !stagePause && tick % GameConstraints.getInstance().getAsteroidsSpawnRate(stage) == 0 && asteroids.size() < GameConstraints.getInstance().getMaxAsteroids(stage)) {
            addRandomAsteroid();
        }

        // Risolutore delle collisioni
        resolveCollisions();

        // Rimozione delle componenti non necessarie
        removeOutsideBullets();
        removeVanishedAsteroids();
        removeVanishedBullets();
        removeVanishedGems();

        //cambio stage
        if (stagePause) {stageChangeTransition();}

        if (deathPause) {deathPauseTransition();}

        // Aggiornamento dei tick
        if (speedBoostActivated) {
            tickBoost++;
            if (tickBoost > GameConstraints.getInstance().getBoostDuration()) {
                speedBoostActivated = false;
                Settings.LOGGER.log(Level.INFO, "Boost deactivated");
                tickBoost = 0;
            }
        }
        if (shieldActivated) {
            tickShield++;
            if (tickShield > GameConstraints.getInstance().getShieldDuration()) {
                shieldActivated = false;
                Settings.LOGGER.log(Level.INFO, "Shield deactivated");
                tickShield = 0;
                for(OnShieldStatusChangedListener listener : onShieldStatusChangedListeners)
                    listener.onShieldStatusChanged();
            }
        }
        tick++;
    }

    private void stageChangeTransition(){
        if(stage % 5 == 0) //Autosalvataggio ogni 5 stage
            SaveFileManager.getInstance().saveData();
        if(stageTickTransition<=0){stagePause = false; stageTickTransition=50;}
        else{stageTickTransition--;}
    }

    private void deathPauseTransition(){
        spaceShip.stopShooting();
        spaceShip.stopRotatingRight();
        spaceShip.stopRotatingLeft();
        spaceShip.stopAccelerating();
        if(deathTick<=0){deathPause = false; deathTick=10; spaceShip.reset();}
        else{deathTick--;}
    }

    private void removeVanishedGems() {
        GameComponentsManager vanishedGems = new GameComponentsManager();
        for (GameObject gem : gems) {
            if (gem.getTick() > GameConstraints.getInstance().getGemVanishingTime()) {
                vanishedGems.add(gem);
            }
        }
        gems.removeArray(vanishedGems);
    }

    /**
     * Metodo per eliminare gli asteroidi distrutti
     */
    private void removeVanishedAsteroids() {
        GameComponentsManager vanishedAsteroids = new GameComponentsManager();
        for (GameObject asteroid : destroyedAsteroids) {
            if (asteroid.getTick() > 14) {
                vanishedAsteroids.add(asteroid);
            }
        }
        destroyedAsteroids.removeArray(vanishedAsteroids);
    }

    /**
     * Metodo per eliminare i proiettili distrutti
     */
    private void removeVanishedBullets() {
        GameComponentsManager vanishedBullets = new GameComponentsManager();
        for (GameObject bullet : destroyedBullets) {
            if (bullet.getTick() > 10) {
                vanishedBullets.add(bullet);
            }
        }
        destroyedBullets.removeArray(vanishedBullets);
    }

    /**
     * Metodo per aggiungere un asteroide casuale
     */
    public void addRandomAsteroid() {
        int dim = (int) (Math.random() * GameConstraints.getInstance().getAsteroidMaxDimensionUpscale(stage)) + GameConstraints.getInstance().getAsteroidMinDimension(stage);
        int life = GameConstraints.getInstance().getAsteroidLife(stage, dim);
        int x, y;
        if(Math.random() > 0.5){
            x = (int) (Math.random() * GameConstraints.WORLDWIDTH) + 1;
            y = Math.random() > 0.5 ? 1 : GameConstraints.WORLDHEIGHT;
        }
        else{
            x = Math.random() > 0.5 ? 1 : GameConstraints.WORLDWIDTH;
            y = (int) (Math.random() * GameConstraints.WORLDHEIGHT) + 1;
        }
        int vx = (int) (Math.random() * GameConstraints.getInstance().getAsteroidMaxSpeedVariable(stage)) + GameConstraints.getInstance().getAsteroidMinSpeed(stage);
        int vy = (int) (Math.random() * GameConstraints.getInstance().getAsteroidMaxSpeedVariable(stage)) + GameConstraints.getInstance().getAsteroidMinSpeed(stage);
        asteroids.add(new Asteroid(x, y, vx, vy, life, dim));
        Settings.LOGGER.log(Level.INFO, "Asteroid added with dimension: " + dim + " and life: " + life + " at position: " + x + ", " + y);
    }

    /**
     * Metodo per aggiungere un proiettile
     * @param b proiettile da aggiungere
     */
    public void addBullet(Bullet b) {
        SoundEngine.getInstance().playBullet();
        bullets.add(b);
        Settings.LOGGER.log(Level.INFO, "Bullet added at position: " + b.getX() + ", " + b.getY());
    }

    /**
     * Metodo per rimuovere i proiettili fuori dallo schermo
     */
    public void removeOutsideBullets(){
        GameComponentsManager outsideBullets = new GameComponentsManager();
        for (GameObject bullet : bullets) {
            if (((Bullet) bullet).isOutside()) {
                outsideBullets.add(bullet);
            }
        }
        bullets.removeArray(outsideBullets);
    }

    /**
     * Metodo per risolvere le collisioni
     * e aggiornare i punteggi
     */
    public void resolveCollisions() {
        GameComponentsManager newDestroyedAsteroids = new GameComponentsManager();
        GameComponentsManager newDestroyedBullets = new GameComponentsManager();
        GameComponentsManager newPickedGems = CollisionUtility.checkCollisionElementArray(spaceShip, gems);
        GameComponentsManager newMissileExplosions = new GameComponentsManager();

        // Gestione della collisione tra astronave e asteroidi
        if (!shieldActivated) {
            if (CollisionUtility.bCheckCollision(spaceShip, asteroids))
                loseLife();
        }
        else {
            GameComponentsManager collidedAsteroids = CollisionUtility.checkCollisionElementArray(spaceShip, asteroids);
            for (GameObject asteroid : collidedAsteroids) {
                scoreCount += ((Asteroid) asteroid).getScore();
            }
            newDestroyedAsteroids.add(collidedAsteroids);
            asteroids.removeArray(collidedAsteroids);
        }

        //Gestione delle collisioni tra proiettili
        for (GameObject bullet : bullets) {
            switch (bullet.getType()) {
                case 0:
                case 1:
                    if (CollisionUtility.bCheckCollision(bullet, asteroids)) {
                        bullet.setRadius(30);
                        newDestroyedBullets.add(bullet);
                        Settings.LOGGER.log(Level.INFO, "Bullet destroyed at position: " + bullet.getX() + ", " + bullet.getY());
                    }
                    break;
                case 2:
                    if (CollisionUtility.bCheckCollision(bullet, asteroids)) {
                        newDestroyedBullets.add(bullet);
                        newMissileExplosions.add(new Bullet(bullet.getX(), bullet.getY(), 0, 0, ((Bullet)bullet).getDamage(), 0, 0, 124));
                    }
                    break;
                case 3:
                    break;
            }
        }

        // Gestione delle collisioni tra asteroidi
        if (Math.random() > 0.9) {
            GameComponentsManager asteroidsCollisions = CollisionUtility.checkCollisionArrayArray(asteroids, asteroids);
            for (GameObject asteroid : asteroidsCollisions) {
                ((Asteroid) asteroid).bounce();
            }
        }

        // Gestione delle collisioni tra proiettili e asteroidi
        for (GameObject bullet : bullets) {
            double damage = ((Bullet) bullet).getDamage();
            for (GameObject asteroid : asteroids) {
                if (CollisionUtility.checkCollision(bullet, asteroid) && ((Asteroid) asteroid).receiveDamage(damage)) {
                    newDestroyedAsteroids.add(asteroid);
                    Settings.LOGGER.log(Level.INFO, "Asteroid destroyed at position: " + asteroid.getX() + ", " + asteroid.getY());
                }
            }
        }
        for(GameObject explosion : newMissileExplosions){
            for (GameObject asteroid : asteroids) {
                if (CollisionUtility.checkCollision(explosion, asteroid) && ((Asteroid) asteroid).receiveDamage(((Bullet) explosion).getDamage())) {
                    newDestroyedAsteroids.add(asteroid);
                    Settings.LOGGER.log(Level.INFO, "Missile explosion destroyed asteroid at position: " + asteroid.getX() + ", " + asteroid.getY());
                }
            }
        }
        asteroids.removeArray(newDestroyedAsteroids);
        bullets.removeArray(newDestroyedBullets);
        newDestroyedBullets.add(newMissileExplosions);

        // Gestione delle gemme raccolte
        for (GameObject gem : newPickedGems) {
            gemCount += GameConstraints.getInstance().getGemValue(gem.getType());
            GameConstraints.getInstance().setGems(GameConstraints.getInstance().getGems() + GameConstraints.getInstance().getGemValue(gem.getType()));
            Settings.LOGGER.log(Level.INFO, "Pickup gem of type " + gem.getType() + " at position: " + gem.getX() + ", " + gem.getY() + " with value: " + GameConstraints.getInstance().getGemValue(gem.getType()));
        }
        gems.removeArray(newPickedGems);

        // Gestione degli asteroidi distrutti, score ed eventuale nuovo stage
        for (GameObject asteroid : newDestroyedAsteroids) {
            scoreCount += ((Asteroid) asteroid).getScore();
            GameConstraints.getInstance().setScore(GameConstraints.getInstance().getScore() + (int)((Asteroid) asteroid).getScore());
            SoundEngine.getInstance().playExplosion();
            int pointsToStage = switch (stage) {
                case 1, 2, 3 -> stage * 100;
                case 4, 5 -> stage * 150;
                case 6, 7 -> stage * 200;
                case 8, 9, 10 -> stage * 250;
                default -> stage * 300;
            };
                if (scoreCount >= pointsToStage) {
                stage += 1;
                Settings.LOGGER.log(Level.INFO, "Stage " + stage + " reached!");
                stagePause = true;
                for (GameObject asteroid2 : asteroids)
                    asteroid2.setTick(0);
                destroyedAsteroids.add(asteroids);
                asteroids.clear();
            }

            // Generazione gemma
            if (Math.random() < GameConstraints.getInstance().getGemChance() && gems.size() <= GameConstraints.getInstance().getMaxGems())
                generatorGem(asteroid.getX(), asteroid.getY());

            // Generazione asteroidi figli
            if (asteroid.getRadius() > GameConstraints.getInstance().getAsteroidMinSplitDim(stage))
                asteroids.add(((Asteroid) asteroid).split());
        }


        // Reset tick per gli oggetti distrutti
        for(GameObject obj : newDestroyedAsteroids)
            obj.setTick(0);
        for(GameObject obj : newDestroyedBullets)
            obj.setTick(0);

        // Aggiunta degli oggetti distrutti a questo tick alle liste di oggetti distrutti
        destroyedAsteroids.add(newDestroyedAsteroids);
        destroyedBullets.add(newDestroyedBullets);
    }

    /**
     * Metodo per perdere una vita
     */
    public void loseLife(){
        lives--;
        for (GameObject asteroid : asteroids)
            asteroid.setTick(0);
        for (GameObject bullet : bullets)
            bullet.setTick(0);
        destroyedAsteroids.add(asteroids);
        SoundEngine.getInstance().playExplosion();
        destroyedBullets.add(bullets);
        bullets.clear();
        asteroids.clear();
        gems.clear();
        speedBoostActivated = false;
        shieldActivated = false;
        if(lives == 0) {
            for (GameOverListener listener : gameOverListeners)
                listener.onGameOver();
            Settings.LOGGER.log(Level.INFO, "Game Over. Score: " + scoreCount + " Gems: " + gemCount + " Stage: " + stage);
        }
        else {
            // Invio evento di morte
            for (OnDeathListener listener : onDeathListeners)
                listener.onDeath();
            deathPause = true;
            Settings.LOGGER.log(Level.INFO, "Death lives left: " + lives);
        }
    }

    /**
     * Metodo per generare una gemma di tipo casuale
     * @param x coordinata x
     * @param y coordinata y
     */
    public void generatorGem(double x, double y){
        int power = (int) (Math.random() * 100);
        if(power < 50)
            gems.add(new Gem(x, y, 1));
        else if(power < 80)
            gems.add(new Gem(x, y, 2));
        else
            gems.add(new Gem(x, y, 3));
    }

    public static void reset(){
        instance = null;
    }
    public void addOnDeathListener(OnDeathListener listener){
        onDeathListeners.add(listener);
    }
    public void addGameOverListener(GameOverListener listener){
        gameOverListeners.add(listener);
    }
    public void addOnShieldStatusChangedListener(OnShieldStatusChangedListener listener){
        onShieldStatusChangedListeners.add(listener);
    }
    public void addOnSpeedBoostStatusChangedListener(OnBoostStatusChangedListener listener){
        onBoostStatusChangedListeners.add(listener);
    }
    public void setBulletType(int bulletType){
        this.spaceShip.setBulletType(bulletType);
    }
    public void setScore(int score) {this.scoreCount = score;}

    public boolean isShielded(){
        return shieldActivated;
    }
    public boolean isBoosted(){return speedBoostActivated;}

    /**
     * Metodo per fermare tutte le azioni della navicella
     */
    public void stopAllActions(){
        spaceShip.stopShooting();
        spaceShip.stopRotatingRight();
        spaceShip.stopRotatingLeft();
        spaceShip.stopAccelerating();
    }

    public void updatePwUpShield(){
        pwUpShield = GameConstraints.getInstance().getShopShield();
    }

    public void updatePwUpBoost(){
        pwUpSpeed = GameConstraints.getInstance().getShopBoost();
    }
}
