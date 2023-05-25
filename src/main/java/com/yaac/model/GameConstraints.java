package com.yaac.model;

import com.yaac.Settings;

public class GameConstraints {
    private double maxSpeed = 5;
    private double shipAcceleration = 2;
    private double bulletSpeed = 10;
    private double bulletDamage = 2;
    private double bulletRatio = 2;
    private double maxDurationShield;
    private int shipRotation = 5;
    private double shipDeceleration = 0.95;

    private int lvlMaxSpeed=5;
    private int lvlBulletSpeed =7;
    private int lvlBulletDamage =4;
    private int lvlBulletRatio =9;

    //GETTERS
    public double getMaxSpeed() {return maxSpeed;}
    public double getShipAcceleration() {return shipAcceleration;}
    public double getBulletSpeed() {return bulletSpeed;}
    public double getBulletDamage() {return bulletDamage;}
    public double getBulletRatio() {return bulletRatio;}
    public double getShipDeceleration() {
        return shipDeceleration;
    }
    public double getMaxDurationShield() {return maxDurationShield;}
    public int getShipRotation() {return shipRotation;}
    public int getLvlMaxSpeed() {return lvlMaxSpeed;}
    public int getLvlBulletSpeed() {return lvlBulletSpeed;}
    public int getLvlBulletDamage() {return lvlBulletDamage;}
    public int getLvlBulletRatio() {return lvlBulletRatio;}

    //SETTERS
    public void setMaxSpeed(double maxSpeed) {this.maxSpeed = maxSpeed;}
    public void setShipAcceleration(double shipAcceleration) {this.shipAcceleration = shipAcceleration;}
    public void setBulletSpeed(double bulletSpeed) {this.bulletSpeed = bulletSpeed;}
    public void setBulletDamage(double bulletDamage) {this.bulletDamage = bulletDamage;}
    public void setBulletRatio(double bulletRatio) {this.bulletRatio = bulletRatio;}
    public void setShipDeceleration(double shipDeceleration) {this.shipDeceleration = shipDeceleration;}
    public void setMaxDurationShield(double maxDurationShield) {this.maxDurationShield = maxDurationShield;}
    public void setShipRotation(int shipRotation) {this.shipRotation = shipRotation;}
    public void setLvlMaxSpeed(int lvlMaxSpeed) {this.lvlMaxSpeed = lvlMaxSpeed;}
    public void setLvlBulletSpeed(int lvlBulletSpeed) {this.lvlBulletSpeed = lvlBulletSpeed;}
    public void setLvlBulletDamage(int lvlBulletDamage) {this.lvlBulletDamage = lvlBulletDamage;}
    public void setLvlBulletRatio(int lvlBulletRatio) {this.lvlBulletRatio = lvlBulletRatio;}

    private GameConstraints(){
        //TODO
    }
    private static GameConstraints instance = null;
    public static GameConstraints getInstance(){
        if (instance == null)
            instance = new GameConstraints();
        return instance;
    }

    public static int WORLDWIDTH = Settings.width;
    public static int WORLDHEIGHT = Settings.height;
}
