package com.yaac.model;

import com.yaac.Settings;
import com.yaac.model.GameComponent.*;
import com.yaac.model.Utility.CollisionUtility;
import com.yaac.model.Utility.GameComponentsManager;
import com.yaac.model.Utility.GameOverListener;
import com.yaac.model.Utility.OnDeathListener;
import com.yaac.view.SoundEngine;

import java.util.ArrayList;

public class Game {
    static Game instance = null;
    private final ArrayList<OnDeathListener> onDeathListeners;
    private final ArrayList<GameOverListener> gameOverListeners;
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

    public static double isBoostActivated() {
        return speedBoostActivated ? 1.0 : 0.0;
    }

    public static boolean isShieldActivated() {
        return shieldActivated;
    }

    public boolean getStagePause() {
        return stagePause;
    }

    /**
     * Costruttore privato per implementare il pattern Singleton
     */
    private Game() {
        onDeathListeners = new ArrayList<>();
        gameOverListeners = new ArrayList<>();
        stage = SaveFileManager.getInstance().getCheckPoint();
        stagePause = true;
        tick = 0;
        stageTickTransition =50;
        this.spaceShip = new SpaceShip(Settings.width/2,Settings.height/2);
        bullets = new GameComponentsManager();
        asteroids = new GameComponentsManager();
        gems = new GameComponentsManager();
        destroyedAsteroids = new GameComponentsManager();
        destroyedBullets = new GameComponentsManager();
        lives = GameConstraints.getInstance().getLife();

        pwUpShield = GameConstraints.getInstance().getShopShield();
        pwUpSpeed = GameConstraints.getInstance().getShopBoost();
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
        spaceShip.startRotatingRight();
    }
    public void startRotateLeft(){
        spaceShip.startRotatingLeft();
    }
    public void startAccelerate(){
        spaceShip.startAccelerating();
    }
    public void startShoot(){
        spaceShip.startShooting();
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
            tickShield = 0;
        }
    }
    public void activateBoost(){
        if (pwUpSpeed && !speedBoostActivated) {
            GameConstraints.getInstance().setShopBoost(false);
            speedBoostActivated = true;
            tickBoost = 0;
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
        if (!stagePause && tick % GameConstraints.getInstance().getAsteroidsSpawnRate(stage) == 0 && asteroids.size() < GameConstraints.getInstance().getMaxAsteroids(stage)) {
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

        // Aggiornamento dei tick
        if (speedBoostActivated) {
            tickBoost++;
            if (tickBoost > GameConstraints.getInstance().getBoostDuration()) {
                speedBoostActivated = false;
                tickBoost = 0;
            }
        }
        if (shieldActivated) {
            tickShield++;
            if (tickShield > GameConstraints.getInstance().getShieldDuration()) {
                shieldActivated = false;
                tickShield = 0;
            }
        }
        tick++;
    }

    private void stageChangeTransition(){
        if(stageTickTransition<=0){stagePause = false; stageTickTransition=50;}
        else{stageTickTransition--;}
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
    }

    /**
     * Metodo per aggiungere un proiettile
     * @param b proiettile da aggiungere
     */
    public void addBullet(Bullet b) {
        SoundEngine.getInstance().playBullet();
        bullets.add(b);
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
        GameComponentsManager newDestroyedBullets = CollisionUtility.checkCollisionArrayArray(asteroids, bullets);
        GameComponentsManager newPickedGems = CollisionUtility.checkCollisionElementArray(spaceShip, gems);
        if (Math.random() > 0.9) {
            GameComponentsManager asteroidsCollisions = CollisionUtility.checkCollisionArrayArray(asteroids, asteroids);
            for (GameObject asteroid : asteroidsCollisions) {
                ((Asteroid) asteroid).bounce();
            }
        }

        // Gestione delle gemme raccolte
        for (GameObject gem : newPickedGems)
            gemCount += GameConstraints.getInstance().getGemValue(gem.getType());
        gems.removeArray(newPickedGems);

        // Gestione delle collisioni tra proiettili e asteroidi
        for (GameObject bullet : bullets) {
            double damage = ((Bullet) bullet).getDamage();
            for (GameObject asteroid : asteroids) {
                if (CollisionUtility.checkCollision(bullet, asteroid) && ((Asteroid) asteroid).receiveDamage(damage)) {
                    newDestroyedAsteroids.add(asteroid);
                }
            }
        }
        asteroids.removeArray(newDestroyedAsteroids);
        bullets.removeArray(newDestroyedBullets);

        // Gestione degli asteroidi distrutti, score ed eventuale nuovo stage
        for (GameObject asteroid : newDestroyedAsteroids) {
            scoreCount += ((Asteroid) asteroid).getScore();
            SoundEngine.getInstance().playExplosion();
            if (scoreCount >= stage * 100) {
                stage += 1;
                if (stage % 5 == 0) {
                    GameConstraints.getInstance().setCheckpoint(stage);
                }
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

        // Gestione della collisione tra astronave e asteroidi
        if (!shieldActivated) {
            if (CollisionUtility.bCheckCollision(spaceShip, asteroids))
                loseLife();
        }
        else {
            GameComponentsManager bouncingAsteroids = CollisionUtility.checkCollisionElementArray(spaceShip, asteroids);
            for (GameObject asteroid : bouncingAsteroids) {
                if (Math.random() > 0.5)
                    ((Asteroid) asteroid).bounce();
            }
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
        spaceShip.reset();
        for (GameObject asteroid : asteroids)
            asteroid.setTick(0);
        for (GameObject bullet : bullets)
            bullet.setTick(0);
        destroyedAsteroids.add(asteroids);
        destroyedBullets.add(bullets);
        bullets.clear();
        asteroids.clear();
        gems.clear();
        speedBoostActivated = false;
        shieldActivated = false;
        if(lives == 0)
            for(GameOverListener listener : gameOverListeners)
                listener.onGameOver();
        else
            // Invio evento di morte
            for(OnDeathListener listener : onDeathListeners)
                listener.onDeath();
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
    public void setBulletType(int bulletType){
        this.spaceShip.setBulletType(bulletType);
    }
    public void setScore(int score) {this.scoreCount = score;}
    public void resetLives() {this.lives = GameConstraints.lives;}
}
