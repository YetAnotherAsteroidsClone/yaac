package com.yaac.model;

import com.yaac.Settings;

public class GameConstraints {
    private double maxSpeed = 5;
    private double shipAcceleration = 2;
    private double bulletSpeed = 10;
    private double bulletDamage = 10;
    private double bulletRatio = 10;
    private double maxDurationShield=5;
    private int shipRotation = 7;
    private double shipDeceleration = 0.90;

    private int lvlMaxSpeed=1;
    private int lvlBulletSpeed=1;
    private int lvlBulletDamage=1;
    private int lvlBulletRatio=1;
    private boolean shopShield=false;
    private int score=0;
    private int orbs=0;
    private int[] costs = {1000,2000,3000,4000,5000,6000,7000,8000,9000};
    private int shieldCost = 10000;
    private int life=3;
    private int highScore=0;
    private double asteroidMaxSpeed = 8;

    //GETTERS
    public double getMaxSpeed() {return maxSpeed;}
    public double getShipAcceleration() {return shipAcceleration;}
    public double getBulletSpeed() {return bulletSpeed;}
    public double getBulletDamage() {return bulletDamage;}
    public double getBulletRatio() {return bulletRatio;}
    public double getShipDeceleration() {return shipDeceleration;}
    public double getMaxDurationShield() {return maxDurationShield;}
    public int getShipRotation() {return shipRotation;}
    public int getLvlMaxSpeed() {return lvlMaxSpeed;}
    public int getLvlBulletSpeed() {return lvlBulletSpeed;}
    public int getLvlBulletDamage() {return lvlBulletDamage;}
    public int getLvlBulletRatio() {return lvlBulletRatio;}
    public boolean getShopShield() {return shopShield;}
    public int getScore() {return score;}
    public int getOrbs() {return orbs;}
    public int getCost(int index){return costs[index];}
    public int getShieldCost() {return shieldCost;}
    public int getLife() {return life;}
    public int getHighScore() {return highScore;}

    //SETTERS
    public void setMaxSpeed(double maxSpeed) {this.maxSpeed = maxSpeed;}
    public void setShipAcceleration(double shipAcceleration) {this.shipAcceleration = shipAcceleration;}
    public void setBulletSpeed(double bulletSpeed) {this.bulletSpeed = bulletSpeed;}
    public void setBulletDamage(double bulletDamage) {this.bulletDamage = bulletDamage;}
    public void setBulletRatio(double bulletRatio) {this.bulletRatio = bulletRatio;}
    public void setShipDeceleration(double shipDeceleration) {this.shipDeceleration = shipDeceleration;}
    public void setMaxDurationShield(double maxDurationShield) {this.maxDurationShield = maxDurationShield;}
    public void setShipRotation(int shipRotation) {this.shipRotation = shipRotation;}
    public void setLife(int life) {this.life = life;}
    public void setHighScore(int bestScore) {this.highScore = bestScore;}

    public void setLvlMaxSpeed(int lvlMaxSpeed) {if(lvlMaxSpeed<=10) this.lvlMaxSpeed = lvlMaxSpeed;}
    public void setLvlBulletSpeed(int lvlBulletSpeed) {if(lvlBulletSpeed<=10) this.lvlBulletSpeed = lvlBulletSpeed;}
    public void setLvlBulletDamage(int lvlBulletDamage) {if(lvlBulletDamage<=10) this.lvlBulletDamage = lvlBulletDamage;}
    public void setLvlBulletRatio(int lvlBulletRatio) {if(lvlBulletRatio<=10) this.lvlBulletRatio = lvlBulletRatio;}
    public void setShopShield(boolean shopShield) {this.shopShield = shopShield;}

    public void setScore(int score) {this.score = score;}
    public void setOrbs(int orbs) {this.orbs = orbs;}

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

    public double getAsteroidMaxSpeed() {
        return asteroidMaxSpeed;
    }
}
