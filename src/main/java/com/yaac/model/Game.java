package com.yaac.model;

import com.yaac.Settings;
import com.yaac.model.GameComponent.*;
import com.yaac.model.Utility.CollisionUtility;
import com.yaac.model.Utility.GameComponentsManager;
import com.yaac.view.Utility.Sound;

public class Game {
    static Game instance = null;

    GameComponentsManager asteroids;
    GameComponentsManager destroyedAsteroids;
    GameComponentsManager bullets;
    GameComponentsManager gems;
    GameComponentsManager destroyedBullets;
    Sound bulletSound, gameMusic;
    SpaceShip spaceShip;
    int gemCount;
    int scoreCount;
    int lives;
    int tick;
    int stage;

    /**
     * Costruttore privato per implementare il pattern Singleton
     */
    private Game() {
        stage = 0;
        tick = 0;
        this.spaceShip = new SpaceShip(Settings.width/2,Settings.height/2);
        bullets = new GameComponentsManager();
        asteroids = new GameComponentsManager();
        gems = new GameComponentsManager();
        destroyedAsteroids = new GameComponentsManager();
        destroyedBullets = new GameComponentsManager();
        lives = GameConstraints.getInstance().getLife();
        bulletSound = new Sound("SpaceshipFiring.wav");
        gameMusic = new Sound("Music.wav");
        gameMusic.loop();
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

    /**
     * Metodo per aggiornare lo stato del gioco
     */
    public void update(){
        spaceShip.update();
        bullets.update();
        asteroids.update();
        destroyedAsteroids.update();
        destroyedBullets.update();
        gems.update();
        if (tick % GameConstraints.getInstance().getAsteroidsSpawnRate(stage) == 0 && asteroids.size() < GameConstraints.getInstance().getMaxAsteroids()) {
            addRandomAsteroid();
        }
        resolveCollisions();
        removeOutsideBullets();
        removeVanishedAsteroids();
        removeVanishedBullets();
        tick++;
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
        int dim = (int) (Math.random() * 46) + 24;
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
        bulletSound.play();
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
        GameComponentsManager newDestroyedBullets;
        GameComponentsManager newPickedGems = CollisionUtility.checkCollisionElementArray(spaceShip, gems);
        for (GameObject gem : newPickedGems)
            gemCount += GameConstraints.getInstance().getGemValue(gem.getType());
        gems.removeArray(newPickedGems);
        for (GameObject bullet : bullets){
            double damage = ((Bullet) bullet).getDamage();
            for (GameObject asteroid : asteroids){
                if(CollisionUtility.checkCollision(bullet, asteroid) && ((Asteroid) asteroid).receiveDamage(damage)) {
                    newDestroyedAsteroids.add(asteroid);
                }
            }
        }
        newDestroyedBullets = CollisionUtility.checkCollisionArrayArray(asteroids, bullets);
        asteroids.removeArray(newDestroyedAsteroids);
        bullets.removeArray(newDestroyedBullets);
        for (GameObject asteroid : newDestroyedAsteroids){
            scoreCount += ((Asteroid) asteroid).getScore();
            if(Math.random() < GameConstraints.getInstance().getGemChance() && gems.size() <= GameConstraints.getInstance().getMaxGems()){
                int power = (int) (Math.random() * 100);
                if(power < 50)
                    gems.add(new Gem(asteroid.getX(), asteroid.getY(), 1));
                else if(power < 80)
                    gems.add(new Gem(asteroid.getX(), asteroid.getY(), 2));
                else
                    gems.add(new Gem(asteroid.getX(), asteroid.getY(), 3));
            }
            if (asteroid.getRadius() > 30)
                asteroids.add(((Asteroid) asteroid).split());
        }

        if(CollisionUtility.bCheckCollision(spaceShip, asteroids)){
            lives--;
            spaceShip.reset();
            newDestroyedAsteroids.add(asteroids);
            newDestroyedBullets.add(bullets);
            bullets.clear();
            asteroids.clear();
        }
        for(GameObject obj : newDestroyedAsteroids)
            obj.setTick(0);
        for(GameObject obj : newDestroyedBullets){
            obj.setTick(0);
        }
        destroyedAsteroids.add(newDestroyedAsteroids);
        destroyedBullets.add(newDestroyedBullets);
    }

}
