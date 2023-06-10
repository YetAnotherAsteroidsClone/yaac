package com.yaac.model;

import com.yaac.Settings;

public class GameConstraints {
    public int gemVanishingTime = 240;
    private double maxSpeed = 5;
    private double shipAcceleration = 2;
    private double bulletSpeed = 6;
    private double bulletDamage = 10;
    private double bulletRatio = 10;
    private double maxDurationShield=5;
    private int shipRotation = 7;
    private double shipDeceleration = 0.90;

    private int lvlMaxSpeed=1;
    private int lvlBulletSpeed=1;
    private int lvlBulletDamage=1;
    private int lvlBulletRatio=1;

    private final double maxSpeedCoefficient = 1.1;
    private final double accelerationCoefficient = 1.1;
    private final double bulletRatioCoefficient = 1.5;
    private final double bulletSpeedCoefficient = 1.5;
    private final double bulletDamageCoefficient = 1.8;

    private final int maxGems = 15;
    private final int maxAsteroids = 20;

    private boolean shopBoost=false;
    private int boostCost = 500;
    private boolean shopShield=false;
    private int shieldCost = 1000;

    private int score;
    private int gems;
    private final int[] costs = {100,200,300,400,500,600,700,800,900};

    private final int[] unlockWeaponsScore = {500,1500,2500};
    private final int[] unlockEnginesScore = {1000,2000,3000};

    public final static int lives = 4;
    private int life;
    private int checkpoint;
    private int highScore;
    private double gemChance = 0.7; //valore da 0 a 1 che indica la probabilità di spawnare una gemma (0.1 = 10%)
    private final int[] gemValue = {5, 20, 100};

    public static int WORLDWIDTH = Settings.width;
    public static int WORLDHEIGHT = Settings.height;

    //GETTERS
    public double getMaxSpeed() {return maxSpeed + lvlMaxSpeed * maxSpeedCoefficient;}
    public double getShipAcceleration() {return shipAcceleration + lvlMaxSpeed * accelerationCoefficient;}
    public double getBulletSpeed() {return bulletSpeed + lvlBulletSpeed * bulletSpeedCoefficient;}
    public double getBulletDamage() {return bulletDamage + lvlBulletDamage * bulletDamageCoefficient ;}
    public double getBulletRatio() {return bulletRatio - lvlBulletRatio * bulletRatioCoefficient;}

    public double getShipDeceleration() {return shipDeceleration;}
    public double getMaxDurationShield() {return maxDurationShield;}
    public int getShipRotation() {return shipRotation;}
    public int getLvlMaxSpeed() {return lvlMaxSpeed;}
    public int getLvlBulletSpeed() {return lvlBulletSpeed;}
    public int getLvlBulletDamage() {return lvlBulletDamage;}
    public int getLvlBulletRatio() {return lvlBulletRatio;}
    public boolean getShopShield() {return shopShield;}
    public boolean getShopBoost(){return shopBoost;}
    public int getScore() {return score;}
    public int getGems() {return gems;}
    public int getCost(int index){return costs[index];}
    public int getShieldCost() {return shieldCost;}
    public int getBoostCost(){return boostCost;}
    public int getLife() {return life;}
    public int getHighScore() {return highScore;}
    public double getGemChance() {
        return gemChance;
    }
    public int getGemValue(int index) {
        return gemValue[index - 1];
    }
    public int getMaxGems() {return maxGems;}
    public int getGemVanishingTime() {
        return gemVanishingTime;
    }
    public int getCheckpoint() {return checkpoint;}
    public int getUnlockWeaponsScore(int index) {return unlockWeaponsScore[index];}
    public int getUnlockEnginesScore(int index) {return unlockEnginesScore[index];}


    //ASTEROIDS CONSTRAINTS
    public int getMaxAsteroids(int stage) {
        return maxAsteroids + stage * 2;
    }
    public double getAsteroidMaxSpeedVariable(int stage) {
        return 1 + stage * 0.5;
    }
    public int getAsteroidMinSpeed(int stage) {
        return stage * 2 + 4;
    }
    public int getAsteroidsSpawnRate(int stage) {return 100 - (stage-1) * 5;}
    public int getAsteroidLife(int stage, int dim) {
        return (int) (stage * 4 + dim * 0.4);
    }
    public int getAsteroidMaxDimensionUpscale(int stage) {
        return (int) (10 + stage * 0.5);
    }
    public int getAsteroidMinDimension(int stage) {
        return 30 + stage * 2;
    }
    public int getAsteroidMinSplitDim(int stage) {
        return 40 - stage * 2;
    }

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

    public void setLvlMaxSpeed(int lvl) {lvlMaxSpeed = Math.min(lvl, 10);}
    public void setLvlBulletSpeed(int lvl) {lvlBulletSpeed = Math.min(lvl, 10);}
    public void setLvlBulletDamage(int lvl) {lvlBulletDamage = Math.min(lvl, 10);}
    public void setLvlBulletRatio(int lvl) {lvlBulletRatio = Math.min(lvl, 10);}

    public void setShopShield(boolean shopShield) {this.shopShield = shopShield;}
    public void setShopBoost(boolean shopBoost) {this.shopBoost = shopBoost;}

    public void setScore(int score) {this.score = score;}
    public void setGems(int gems) {this.gems = gems;}
    public void setCheckpoint(int checkpoint) {this.checkpoint = checkpoint;}

    private GameConstraints(){
        life = SaveFileManager.getInstance().getLives();
        checkpoint = SaveFileManager.getInstance().getCheckPoint();
        highScore = SaveFileManager.getInstance().getHighScore();
        gems = SaveFileManager.getInstance().getGems();
        score= SaveFileManager.getInstance().getScore();
    }
    private static GameConstraints instance = null;
    public static GameConstraints getInstance(){
        if (instance == null)
            instance = new GameConstraints();
        return instance;
    }
}
