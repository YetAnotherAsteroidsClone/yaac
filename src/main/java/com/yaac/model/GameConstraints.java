package com.yaac.model;

import com.yaac.Settings;

public class GameConstraints {
    private double maxSpeed;
    private double maxAcceleration;
    private double maxBulletSpeed;
    private double maxBulletDamage;
    private double maxBulletRatio;
    private double maxDurationShield;

    private int lvlMaxSpeed=5;
    private int lvlMaxBulletSpeed=7;
    private int lvlMaxBulletDamage=4;
    private int lvlMaxBulletRatio=9;

    //GETTERS
    public double getMaxSpeed() {return maxSpeed;}
    public double getMaxAcceleration() {return maxAcceleration;}
    public double getMaxBulletSpeed() {return maxBulletSpeed;}
    public double getMaxBulletDamage() {return maxBulletDamage;}
    public double getMaxBulletRatio() {return maxBulletRatio;}
    public double getMaxDurationShield() {return maxDurationShield;}
    public int getLvlMaxSpeed() {return lvlMaxSpeed;}
    public int getLvlMaxBulletSpeed() {return lvlMaxBulletSpeed;}
    public int getLvlMaxBulletDamage() {return lvlMaxBulletDamage;}
    public int getLvlMaxBulletRatio() {return lvlMaxBulletRatio;}

    //SETTERS
    public void setMaxSpeed(double maxSpeed) {this.maxSpeed = maxSpeed;}
    public void setMaxAcceleration(double maxAcceleration) {this.maxAcceleration = maxAcceleration;}
    public void setMaxBulletSpeed(double maxBulletSpeed) {this.maxBulletSpeed = maxBulletSpeed;}
    public void setMaxBulletDamage(double maxBulletDamage) {this.maxBulletDamage = maxBulletDamage;}
    public void setMaxBulletRatio(double maxBulletRatio) {this.maxBulletRatio = maxBulletRatio;}
    public void setMaxDurationShield(double maxDurationShield) {this.maxDurationShield = maxDurationShield;}
    public void setLvlMaxSpeed(int lvlMaxSpeed) {this.lvlMaxSpeed = lvlMaxSpeed;}
    public void setLvlMaxBulletSpeed(int lvlMaxBulletSpeed) {this.lvlMaxBulletSpeed = lvlMaxBulletSpeed;}
    public void setLvlMaxBulletDamage(int lvlMaxBulletDamage) {this.lvlMaxBulletDamage = lvlMaxBulletDamage;}
    public void setLvlMaxBulletRatio(int lvlMaxBulletRatio) {this.lvlMaxBulletRatio = lvlMaxBulletRatio;}


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
