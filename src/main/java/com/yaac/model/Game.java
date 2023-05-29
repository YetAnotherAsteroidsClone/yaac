package com.yaac.model;

import com.yaac.Settings;
import com.yaac.model.GameComponent.*;
import com.yaac.model.Utility.CollisionUtility;
import com.yaac.model.Utility.GameComponentsManager;
import com.yaac.view.Utility.Sound;

public class Game {
    static Game instance = null;
    GameComponentsManager asteroids;
    GameComponentsManager destroyedAsteroids;
    GameComponentsManager bullets;
    GameComponentsManager gems;
    GameComponentsManager destroyedBullets;
    Sound bulletSound, gameMusic;
    SpaceShip spaceShip;
    int gemCount;
    int scoreCount;
    int lives;
    int tick = 0;
    int stage;

    private Game() {
        stage = 0;
        this.spaceShip = new SpaceShip(Settings.width/2,Settings.height/2);
        bullets = new GameComponentsManager();
        asteroids = new GameComponentsManager();
        gems = new GameComponentsManager();
        destroyedAsteroids = new GameComponentsManager();
        destroyedBullets = new GameComponentsManager();
        lives = GameConstraints.getInstance().getLife();
        bulletSound = new Sound("SpaceshipFiring.wav");
        gameMusic = new Sound("Music.wav");
        gameMusic.loop();
    }

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public int getScore() {
        return scoreCount;
    }

    public int getLives() {
        return lives;
    }

    public void update(){
        spaceShip.update();
        bullets.update();
        asteroids.update();
        destroyedAsteroids.update();
        destroyedBullets.update();
        gems.update();
        if (tick % GameConstraints.getInstance().getAsteroidsSpawnRate(stage) == 0 && asteroids.size() < GameConstraints.getInstance().getMaxAsteroids()) {
            addRandomAsteroid();
        }
        resolveCollisions();
        removeOutsideBullets();
        removeVanishedAsteroids();
        removeVanishedBullets();
        tick++;
    }

    private void removeVanishedAsteroids() {
        GameComponentsManager vanishedAsteroids = new GameComponentsManager();
        for (GameObject asteroid : destroyedAsteroids) {
            if (asteroid.getTick() > 14) {
                vanishedAsteroids.add(asteroid);
            }
        }
        destroyedAsteroids.removeArray(vanishedAsteroids);
    }

    private void removeVanishedBullets() {
        GameComponentsManager vanishedBullets = new GameComponentsManager();
        for (GameObject bullet : destroyedBullets) {
            if (bullet.getTick() > 10) {
                vanishedBullets.add(bullet);
            }
        }
        destroyedBullets.removeArray(vanishedBullets);
    }

    public void addRandomAsteroid() {
        int dim = (int) (Math.random() * 46) + 24;
        int life = GameConstraints.getInstance().getAsteroidLife(stage, dim);
        int x, y;
        if(Math.random() > 0.5){
            x = (int) (Math.random() * GameConstraints.WORLDWIDTH) + 1;
            y = Math.random() > 0.5 ? 1 : GameConstraints.WORLDHEIGHT;
        }
        else{
            x = Math.random() > 0.5 ? 1 : GameConstraints.WORLDWIDTH;
            y = (int) (Math.random() * GameConstraints.WORLDHEIGHT) + 1;
        }
        int vx = (int) (Math.random() * GameConstraints.getInstance().getAsteroidMaxSpeedVariable(stage)) + GameConstraints.getInstance().getAsteroidMinSpeed(stage);
        int vy = (int) (Math.random() * GameConstraints.getInstance().getAsteroidMaxSpeedVariable(stage)) + GameConstraints.getInstance().getAsteroidMinSpeed(stage);
        addAsteroid(x, y, vx, vy, dim, life);
    }

    public void addAsteroid(int x, int y, int vx, int vy, int size, int life){
        asteroids.add(new Asteroid(x, y, vx, vy, life,size));
    }

    public void addBullet(Bullet b) {
        bulletSound.play();
        bullets.add(b);
    }

    public void addPowerUp() {
        //TODO
    }

    public void removeOutsideBullets(){
        GameComponentsManager outsideBullets = new GameComponentsManager();
        for (GameObject bullet : bullets) {
            if (((Bullet) bullet).isOutside()) {
                outsideBullets.add(bullet);
            }
        }
        bullets.removeArray(outsideBullets);
    }

    public void resolveCollisions() {
        GameComponentsManager newDestroyedAsteroids = new GameComponentsManager();
        GameComponentsManager newDestroyedBullets;
        GameComponentsManager newPickedGems = CollisionUtility.checkCollisionElementArray(spaceShip, gems);
        for (GameObject gem : newPickedGems)
            gemCount += GameConstraints.getInstance().getGemValue(((Gem) gem).getType());
        gems.removeArray(newPickedGems);
        for (GameObject bullet : bullets){
            double damage = ((Bullet) bullet).getDamage();
            for (GameObject asteroid : asteroids){
                if(CollisionUtility.checkCollision(bullet, asteroid) && ((Asteroid) asteroid).receiveDamage(damage)) {
                    newDestroyedAsteroids.add(asteroid);
                }
            }
        }
        newDestroyedBullets = CollisionUtility.checkCollisionArrayArray(asteroids, bullets);
        asteroids.removeArray(newDestroyedAsteroids);
        bullets.removeArray(newDestroyedBullets);
        for (GameObject asteroid : newDestroyedAsteroids){
            scoreCount += ((Asteroid) asteroid).getScore();
            if(Math.random() < GameConstraints.getInstance().getGemChance() && gems.size() <= GameConstraints.getInstance().getMaxGems()){
                int power = (int) (Math.random() * 100);
                if(power < 50)
                    gems.add(new Gem(((Asteroid) asteroid).getX(), ((Asteroid) asteroid).getY(), 1));
                else if(power < 80)
                    gems.add(new Gem(((Asteroid) asteroid).getX(), ((Asteroid) asteroid).getY(), 2));
                else
                    gems.add(new Gem(((Asteroid) asteroid).getX(), ((Asteroid) asteroid).getY(), 3));
            }
            if (((Asteroid) asteroid).getRadius() > 30)
                asteroids.add(((Asteroid) asteroid).split());
        }

        if(CollisionUtility.bCheckCollision(spaceShip, asteroids)){
            lives--;
            spaceShip.reset();
            newDestroyedAsteroids.add(asteroids);
            newDestroyedBullets.add(bullets);
            bullets.clear();
            asteroids.clear();
        }
        for(GameObject obj : newDestroyedAsteroids)
            obj.setTick(0);
        for(GameObject obj : newDestroyedBullets){
            obj.setTick(0);
        }
        destroyedAsteroids.add(newDestroyedAsteroids);
        destroyedBullets.add(newDestroyedBullets);
    }

    public GameComponentsManager getBullets() {
        return bullets;
    }

    public GameComponentsManager getAsteroids() {
        return asteroids;
    }

    public GameComponentsManager getDestroyedAsteroids() {
        return destroyedAsteroids;
    }

    public GameComponentsManager getDestroyedBullets() {
        return destroyedBullets;
    }

    public GameComponentsManager getGems() {
        return gems;
    }

    public SpaceShip getSpaceShip(){
        return spaceShip;
    }

    public void startRotateRight(){
        spaceShip.startRotatingRight();
    }

    public void startRotateLeft(){
        spaceShip.startRotatingLeft();
    }

    public void startAccelerate(){
        spaceShip.startAccelerating();
    }

    public void startShoot(){
        spaceShip.startShooting();
    }

    public void stopRotateRight(){
        spaceShip.stopRotatingRight();
    }

    public void stopRotateLeft(){
        spaceShip.stopRotatingLeft();
    }

    public void stopAccelerate(){
        spaceShip.stopAccelerating();
    }

    public void stopShot(){
        spaceShip.stopShooting();
    }

    public void deleteInstance(){
        instance = null;
    }
}
