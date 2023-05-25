package com.yaac.model;

import com.yaac.Settings;
import com.yaac.model.GameComponent.Bullet;
import com.yaac.model.GameComponent.SpaceShip;
import com.yaac.model.Utility.GameComponentsManager;

public class Game {
    static Game instance = null;
    GameComponentsManager asteroids;
    GameComponentsManager bullets;
    GameComponentsManager powerUps;
    SpaceShip spaceShip;

    private Game() {
        this.spaceShip = new SpaceShip(Settings.width/2,Settings.height/2);
        bullets = new GameComponentsManager();
    }

    private long tick = 0;
    private int score = 0;
    private int lives = 3;

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
        tick++;
        spaceShip.update();
        bullets.update();
        bullets.clearDeadObjects();
    }

    public void addAsteroid() {
        //TODO
    }

    public void addBullet(Bullet b) {
        bullets.add(b);
    }

    public void addPowerUp() {
        //TODO
    }

    public void resolveCollisions() {
        //TODO
    }

    public GameComponentsManager getBullets() {
        return bullets;
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
