package com.yaac.model.Utility;

import java.io.Serializable;

public class SaveFile implements Serializable {
    private int score;
    private int gems;
    private int speedLvl;
    private int bulletSpeedLvl;
    private int bulletDmgLvl;
    private int bulletRatioLvl;
    private boolean shield;
    private int engine;
    private int weapon;
    private int checkpoint;

    public SaveFile(int score, int gems, int speedLvl, int bulletSpeedLvl, int bulletDmgLvl, int bulletRatioLvl, boolean shield, int engine, int weapon, int checkpoint) {
        this.score = score;
        this.gems = gems;
        this.speedLvl = speedLvl;
        this.bulletSpeedLvl = bulletSpeedLvl;
        this.bulletDmgLvl = bulletDmgLvl;
        this.bulletRatioLvl = bulletRatioLvl;
        this.shield = shield;
        this.engine = engine;
        this.weapon = weapon;
        this.checkpoint=checkpoint;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGems() {
        return gems;
    }

    public void setGems(int gems) {
        this.gems = gems;
    }

    public int getSpeedLvl() {
        return speedLvl;
    }

    public void setSpeedLvl(int speedLvl) {
        this.speedLvl = speedLvl;
    }

    public int getBulletSpeedLvl() {
        return bulletSpeedLvl;
    }

    public void setBulletSpeedLvl(int bulletSpeedLvl) {
        this.bulletSpeedLvl = bulletSpeedLvl;
    }

    public int getBulletDmgLvl() {
        return bulletDmgLvl;
    }

    public void setBulletDmgLvl(int bulletDmgLvl) {
        this.bulletDmgLvl = bulletDmgLvl;
    }

    public int getBulletRatioLvl() {
        return bulletRatioLvl;
    }

    public void setBulletRatioLvl(int bulletRatioLvl) {
        this.bulletRatioLvl = bulletRatioLvl;
    }

    public boolean isShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }
    public int getCheckpoint() {return checkpoint;}

    public void setCheckpoint(int checkpoint) {this.checkpoint = checkpoint;}
}
