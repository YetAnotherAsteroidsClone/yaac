package com.yaac.model;

import com.yaac.Settings;
import com.yaac.model.GameComponent.Asteroid;
import com.yaac.model.GameComponent.Bullet;
import com.yaac.model.GameComponent.GameObject;
import com.yaac.model.GameComponent.SpaceShip;
import com.yaac.model.Utility.CollisionUtility;
import com.yaac.model.Utility.GameComponentsManager;
import com.yaac.view.Utility.Sound;

public class Game {
    static Game instance = null;
    GameComponentsManager asteroids;
    GameComponentsManager destroyedAsteroids;
    GameComponentsManager bullets;
    GameComponentsManager powerUps;
    Sound bulletSound;
    SpaceShip spaceShip;
    int score;
    int lives;
    int tick = 0;

    private Game() {
        this.spaceShip = new SpaceShip(Settings.width/2,Settings.height/2);
        bullets = new GameComponentsManager();
        asteroids = new GameComponentsManager();
        destroyedAsteroids = new GameComponentsManager();
        lives = GameConstraints.getInstance().getLife();
        bulletSound = new Sound("SpaceshipFiring.wav");
    }

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public void update(){
        spaceShip.update();
        bullets.update();
        asteroids.update();
        destroyedAsteroids.update();
        if (tick % 50 == 0) {
            addRandomAsteroid();
        }
        resolveCollisions();
        removeOutsideBullets();
        removeVanishedAsteroids();
        tick++;
    }

    private void removeVanishedAsteroids() {
        GameComponentsManager vanishedAsteroids = new GameComponentsManager();
        for (GameObject asteroid : destroyedAsteroids) {
            if (((Asteroid) asteroid).getTick() > 14) {
                vanishedAsteroids.add(asteroid);
            }
        }
        destroyedAsteroids.removeArray(vanishedAsteroids);
    }

    public void addRandomAsteroid() {
        int dim = (int) (Math.random() * 46) + 24;
        int x, y;
        if(Math.random() > 0.5){
            x = (int) (Math.random() * GameConstraints.WORLDWIDTH) + 1;
            y = Math.random() > 0.5 ? 1 : GameConstraints.WORLDHEIGHT;
        }
        else{
            x = Math.random() > 0.5 ? 1 : GameConstraints.WORLDWIDTH;
            y = (int) (Math.random() * GameConstraints.WORLDHEIGHT) + 1;
        }
        int vx = (int) (Math.random() * GameConstraints.getInstance().getAsteroidMaxSpeed()) + 2;
        int vy = (int) (Math.random() * GameConstraints.getInstance().getAsteroidMaxSpeed()) + 2;
        addAsteroid(x, y, vx, vy, dim);
    }

    public void addAsteroid(int x, int y, int vx, int vy, int size){
        asteroids.add(new Asteroid(x, y, vx, vy, 20,size));
    }

    public void addBullet(Bullet b) {
        bulletSound.play();
        bullets.add(b);
    }

    public void addPowerUp() {
        //TODO
    }

    public void removeOutsideBullets(){
        GameComponentsManager outsideBullets = new GameComponentsManager();
        for (GameObject bullet : bullets) {
            if (((Bullet) bullet).isOutside()) {
                outsideBullets.add(bullet);
            }
        }
        bullets.removeArray(outsideBullets);
    }

    public void resolveCollisions() {
        GameComponentsManager collidedAsteroids =  CollisionUtility.checkCollisionArray(bullets, asteroids);
        GameComponentsManager collidedBullets = CollisionUtility.checkCollisionArray(asteroids, bullets);
        /* Bounce asteroids
        GameComponentsManager contrastAsteroids = CollisionUtility.checkCollisionArray(asteroids, asteroids);
        for (GameObject obj : contrastAsteroids) {
            ((Asteroid) obj).bounce();
        }*/
        if(CollisionUtility.bCheckCollision(spaceShip, asteroids)){
            lives--;
            spaceShip.reset();
            destroyedAsteroids.add(asteroids);
            asteroids.clear();
        }
        asteroids.removeArray(collidedAsteroids);
        bullets.removeArray(collidedBullets);
        for(GameObject obj : collidedAsteroids)
            ((Asteroid)obj).setTick(0);
        destroyedAsteroids.add(collidedAsteroids);
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

    public SpaceShip getSpaceShip(){
        return spaceShip;
    }

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

    public void deleteInstance(){
        instance = null;
    }
}
