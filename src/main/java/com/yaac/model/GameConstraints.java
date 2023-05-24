package com.yaac.model;

public class GameConstraints {
    private int maxSpeed;
    private int maxAcceleration;
    private int maxBulletSpeed;
    private int maxBulletDamage;

    private GameConstraints(){
        //TODO
    }
    private static GameConstraints instance = new GameConstraints();
    public static GameConstraints getInstance(){return instance;}


    //world dimensions
    public static int WORLDWIDTH; // = xxxxx
    public static int WORLDHEIGHT; // = xxxxx
}
