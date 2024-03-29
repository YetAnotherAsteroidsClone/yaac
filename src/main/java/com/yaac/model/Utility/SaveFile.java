package com.yaac.model.Utility;

import com.yaac.model.Language;

import java.io.Serializable;

public class SaveFile implements Serializable {
    private int score;
    private int gems;
    private int speedLvl;
    private int bulletSpeedLvl;
    private int bulletDmgLvl;
    private int bulletRatioLvl;
    private boolean shield;
    private boolean speed;
    private int engine;
    private int weapon;
    private int checkpoint;
    private int currentScore;
    private int currentGems;
    private int highScore;
    private int lives;
    private float volume;
    private float musicVolume;
    private boolean[] unlockedEngines;
    private  boolean[] unlockedWeapons;
    private Language.languageList language;

    private String resolution;

    public SaveFile(int score, int gems, int speedLvl, int bulletSpeedLvl, int bulletDmgLvl, int bulletRatioLvl, boolean shield, boolean speed, int engine, int weapon, int checkpoint, int currentScore, int currentGems, int highScore, int lives, boolean[] unlockedEngines, boolean[] unlockedWeapons, float volume, float musicVolume, Language.languageList language, String resolution) {
        this.score = score;
        this.gems = gems;
        this.speedLvl = speedLvl;
        this.bulletSpeedLvl = bulletSpeedLvl;
        this.bulletDmgLvl = bulletDmgLvl;
        this.bulletRatioLvl = bulletRatioLvl;
        this.shield = shield;
        this.speed = speed;
        this.engine = engine;
        this.weapon = weapon;
        this.checkpoint = checkpoint;
        this.currentScore = currentScore;
        this.currentGems = currentGems;
        this.highScore = highScore;
        this.lives = lives;
        this.unlockedEngines = unlockedEngines;
        this.unlockedWeapons = unlockedWeapons;
        this.volume = volume;
        this.musicVolume = musicVolume;
        this.language = language;
        this.resolution = resolution;
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

    public boolean shield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public boolean speed() {return speed;}
    public void setSpeed(boolean speed) {this.speed = speed;}

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

    public int getCurrentScore() {return currentScore;}
    public void setCurrentScore(int currentScore) {this.currentScore = currentScore;}
    public int getCurrentGems() {return currentGems;}
    public void setCurrentGems(int currentGems) {this.currentGems = currentGems;}
    public int getHighScore() {return highScore;}
    public void setHighScore(int highScore) {this.highScore = highScore;}
    public int getLives() {return this.lives;}
    public void setLives(int lives) {this.lives = lives;}
    public boolean[] getUnlockedEngines() {return unlockedEngines;}
    public boolean[] getUnlockedWeapons() {return unlockedWeapons;}
    public void setUnlockedEngine(int index) {this.unlockedEngines[index] = true;}
    public void setUnlockedWeapon(int index) {this.unlockedWeapons[index] = true;}
    public void setVolume(float volume) {this.volume = volume;}
    public float getVolume() {return this.volume;}

    public void setUnlockedEngines(boolean[] unlockedEngines) {this.unlockedEngines = unlockedEngines;}

    public void setUnlockedWeapons(boolean[] unlockedWeapons) {this.unlockedWeapons = unlockedWeapons;}

    public Language.languageList getLanguage() {return language;}

    public void setLanguage(Language.languageList language) {this.language = language;}
    public float getMusicVolume() {return musicVolume;}
    public void setMusicVolume(float musicVolume) {this.musicVolume = musicVolume;}

    public String getResolution() {return resolution;}
    public void setResolution(String resolution) {this.resolution = resolution;}
}