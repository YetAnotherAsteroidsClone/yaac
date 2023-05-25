package com.yaac.model;

import com.yaac.Settings;

public class GameConstraints {
    private int maxSpeed;
    private int maxAcceleration;

    private int maxBulletSpeed;
    private int maxBulletDamage;
    private int maxBulletRatio;
    private int maxDurationShield;

    public int getMaxSpeed(int x){return maxSpeed;}

    private GameConstraints(){
        //TODO
    }
    private static GameConstraints instance = null;
    public static GameConstraints getInstance(){
        if (instance == null)
            instance = new GameConstraints();
        return instance;
    }
}
