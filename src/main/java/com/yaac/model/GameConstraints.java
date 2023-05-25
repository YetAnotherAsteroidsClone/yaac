package com.yaac.model;

import com.yaac.Settings;

public class GameConstraints {
    private double maxSpeed;
    private double maxAcceleration;

    private double maxBulletSpeed;
    private double maxBulletDamage;
    private double maxBulletRatio;
    private double maxDurationShield;

    //GETTERS
    public double getMaxSpeed() {return maxSpeed;}

    public double getMaxAcceleration() {return maxAcceleration;}

    public double getMaxBulletSpeed() {return maxBulletSpeed;}

    public double getMaxBulletDamage() {return maxBulletDamage;}

    public double getMaxBulletRatio() {return maxBulletRatio;}

    public double getMaxDurationShield() {return maxDurationShield;}


    //SETTERS
    public void setMaxSpeed(double maxSpeed) {this.maxSpeed = maxSpeed;}

    public void setMaxAcceleration(double maxAcceleration) {this.maxAcceleration = maxAcceleration;}

    public void setMaxBulletSpeed(double maxBulletSpeed) {this.maxBulletSpeed = maxBulletSpeed;}

    public void setMaxBulletDamage(double maxBulletDamage) {this.maxBulletDamage = maxBulletDamage;}

    public void setMaxBulletRatio(double maxBulletRatio) {this.maxBulletRatio = maxBulletRatio;}

    public void setMaxDurationShield(double maxDurationShield) {this.maxDurationShield = maxDurationShield;}


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
