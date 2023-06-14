package com.yaac.model;

import com.yaac.Settings;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class GameConstraints {
    public final int gemVanishingTime = 240;
    private double maxSpeed = 5;
    private double shipAcceleration = 2;
    private double bulletSpeed = 6;
    private double bulletDamage = 10;
    private double bulletRatio = 10;
    private double shieldDuration = 600;
    private final double boostDuration = 600;
    private int shipRotation = 7;
    private double shipDeceleration = 0.90;

    private int lvlMaxSpeed;
    private int lvlBulletSpeed;
    private int lvlBulletDamage;
    private int lvlBulletRatio;

    private final double maxSpeedCoefficient = 1.1;
    private final double accelerationCoefficient = 1.1;
    private final double bulletRatioCoefficient = 1.5;
    private final double bulletSpeedCoefficient = 1.5;
    private final double bulletDamageCoefficient = 1.8;
    private final double speedBoostCoefficient = 4.0;

    private final double[] weaponSpeedCoefficient = {1, 0.4, 1.5, 2};
    private final double[] weaponDamageCoefficient = {1, 4, 1.2, 0.3};
    private final double[] weaponRatioCoefficient = {1, 0.6, 1, 1.1};

    private final int maxGems = 15;
    private final int maxAsteroids = 20;

    private boolean shopBoost;
    private boolean shopShield;
    private final int boostCost = 500;
    private final int shieldCost = 1000;

    private int score;
    private int gems;
    private final int[] costs = {100,200,300,400,500,600,700,800,900};

    private final int[] unlockWeaponsScore = {500,1500,2500};
    private final int[] unlockEnginesScore = {1000,2000,3000};

    public final static int lives = 4;
    private int life;
    private int checkpoint;
    private int highScore;
    private final double gemChance = 0.7; //valore da 0 a 1 che indica la probabilità di spawnare una gemma (0.1 = 10%)
    private final int[] gemValue = {5, 20, 100};

    public static int WORLDWIDTH = Settings.width;
    public static int WORLDHEIGHT = Settings.height;

    public void updateWorldSize() {
        WORLDWIDTH = Settings.width;
        WORLDHEIGHT = Settings.height;
    }

    //GETTERS
    public double getMaxSpeed() {return maxSpeed + lvlMaxSpeed * maxSpeedCoefficient + (speedBoostCoefficient * Game.isBoostActivated());}
    public double getShipAcceleration() {return shipAcceleration + lvlMaxSpeed * accelerationCoefficient;}
    public double getBulletSpeed(int bulletType) {return (bulletSpeed + lvlBulletSpeed * bulletSpeedCoefficient) * weaponSpeedCoefficient[bulletType];}
    public double getBulletDamage(int bulletType) {return (bulletDamage + lvlBulletDamage * bulletDamageCoefficient) * weaponDamageCoefficient[bulletType];}
    public double getBulletRatio(int bulletType) {return bulletRatio - (lvlBulletRatio * bulletRatioCoefficient) * weaponRatioCoefficient[bulletType];}

    public double getShipDeceleration() {return shipDeceleration;}
    public double getShieldDuration() {return shieldDuration;}
    public double getBoostDuration() {return boostDuration;}
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
        return maxAsteroids + stage;
    }
    public double getAsteroidMaxSpeedVariable(int stage) {
        return 1 + stage * 0.25;
    }
    public int getAsteroidMinSpeed(int stage) {
        return stage + 4;
    }
    public int getAsteroidsSpawnRate(int stage) {return 100 - (stage-1) * 2;}
    public int getAsteroidLife(int stage, int dim) {
        return (int) (stage * 3 + dim * 0.4);
    }
    public int getAsteroidMaxDimensionUpscale(int stage) {
        return (int) (10 + stage * 0.25);
    }
    public int getAsteroidMinDimension(int stage) {
        return 35 + stage * 2;
    }
    public int getAsteroidMinSplitDim(int stage) {
        return 50 - stage * 2;
    }

    //SETTERS
    public void setMaxSpeed(double maxSpeed) {this.maxSpeed = maxSpeed;}
    public void setShipAcceleration(double shipAcceleration) {this.shipAcceleration = shipAcceleration;}
    public void setBulletSpeed(double bulletSpeed) {this.bulletSpeed = bulletSpeed;}
    public void setBulletDamage(double bulletDamage) {this.bulletDamage = bulletDamage;}
    public void setBulletRatio(double bulletRatio) {this.bulletRatio = bulletRatio;}
    public void setShipDeceleration(double shipDeceleration) {this.shipDeceleration = shipDeceleration;}
    public void setShieldDuration(double shieldDuration) {this.shieldDuration = shieldDuration;}
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
        shopBoost = SaveFileManager.getInstance().speed();
        shopShield = SaveFileManager.getInstance().shield();
        lvlBulletDamage = SaveFileManager.getInstance().getBulletDmgLvl();
        lvlBulletRatio = SaveFileManager.getInstance().getBulletRatioLvl();
        lvlBulletSpeed = SaveFileManager.getInstance().getBulletSpeedLvl();
        lvlMaxSpeed = SaveFileManager.getInstance().getSpeedLvl();
    }
    private static GameConstraints instance = null;
    public static GameConstraints getInstance(){
        if (instance == null)
            instance = new GameConstraints();
        return instance;
    }
}
