package com.yaac.model;

import com.yaac.model.GameComponent.SpaceShip;
import com.yaac.model.Utility.GameComponentsManager;

public class Game {
    static Game instance = null;
    GameComponentsManager asteroids;
    GameComponentsManager bullets;
    GameComponentsManager powerUps;
    SpaceShip spaceShip;

    private Game() {}

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
}
