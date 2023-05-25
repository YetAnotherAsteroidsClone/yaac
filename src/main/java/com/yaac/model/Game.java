package com.yaac.model;

import com.yaac.Settings;
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
    }

    public void addAsteroid() {
        //TODO
    }

    public void addBullet() {
        //TODO
    }

    public void addPowerUp() {
        //TODO
    }

    public void resolveCollisions() {
        //TODO
    }

    public SpaceShip getSpaceShip(){
        return spaceShip;
    }

    public void rotateShipRight(){
        spaceShip.rotateRight();
    }

    public void rotateShipLeft(){
        spaceShip.rotateLeft();
    }

    public void accelerateShip(){
        spaceShip.accelerate();
    }

    public void deleteInstance(){
        instance = null;
    }
}
