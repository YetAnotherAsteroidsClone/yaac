package com.yaac.model;

import com.yaac.Settings;

public class GameConstraints {
    private int maxSpeed;
    private int maxAcceleration;
    private int maxBulletSpeed;
    private int maxBulletDamage;

    private GameConstraints(){
        //TODO
    }
    private static GameConstraints instance = null;
    public static GameConstraints getInstance(){
        if (instance == null)
            instance = new GameConstraints();
        return instance;
    }


    //world dimensions
    public static int WORLDWIDTH = Settings.width;
    public static int WORLDHEIGHT = Settings.height;
}
