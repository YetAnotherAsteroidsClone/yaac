package com.yaac.model.GameComponent;

import com.yaac.model.Game;
import com.yaac.model.GameConstraints;

import static java.lang.Math.min;
import static java.lang.Math.max;

/**
 * Classe dedicata alla gestione dell'astronave
 * Estende la classe MovableObject
 * Implementa i metodi per la gestione della accelerazione e della rotazione
 */
public class SpaceShip extends GameObject{
    int shotTick = 0;

    boolean isMoving = false;
    boolean isShooting = false;
    boolean isRotatingLeft = false;
    boolean isRotatingRight = false;
    boolean isAccelerating = false;

    /**
     * Costruttore di default con posizione iniziale
     * @param x posizione x
     * @param y posizione y
     */
    public SpaceShip(int x, int y){
        super(x, y, 0);
        radius = 24;
    }

    /**
     * Costruttore della spaceship con parametri
     * @param x posizione x
     * @param y posizione y
     * @param vx velocità iniziale su asse x
     * @param vy velocità iniziale su asse y
     */
    public SpaceShip(int x, int y, int vx, int vy){
        super(x, y, vx, vy, 0, 24);
    }


    @Override
    public void update() {
        if(isRotatingLeft)
            rotateLeft();
        if(isRotatingRight)
            rotateRight();
        if(isAccelerating)
            accelerate();
        if(isMoving)
            decelerate();
        if(isShooting)
            shoot();
        move();
        tick++;
    }


    @Override
    public int getType() {
        return 0;
    }

    @Override
    public long getTick() {
        return tick;
    }

    /**
     * Muove l'astronave in base alla velocità
     * Se l'astronave raggiunge i bordi del mondo, la velocità viene azzerata
     */
    public void move() {
        x = x + vx;
        if(x > GameConstraints.WORLDWIDTH || x < 1 ){
            vx = 0;
            x = min(max(x, 1), GameConstraints.WORLDWIDTH);
        }
        y = y + vy;
        if(y > GameConstraints.WORLDHEIGHT || y < 1) {
            vy = 0;
            y = min(max(y, 1), GameConstraints.WORLDHEIGHT);
        }
    }


    /**
     * Accelera l'astronave in base al coefficiente di accelerazione e alla rotazione <br>
     * La velocità viene aumentata e divisa in due componenti, una per asse
     * in base alla direzione dell'astronave
     */
    public void accelerate(){
        double accX = (GameConstraints.getInstance().getShipAcceleration() * Math.sin(Math.toRadians(rotation)));
        double accY = - (GameConstraints.getInstance().getShipAcceleration() * Math.cos(Math.toRadians(rotation)));
        vx += accX;
        vy += accY;
        vx = min(max(vx, -GameConstraints.getInstance().getMaxSpeed()), GameConstraints.getInstance().getMaxSpeed());
        vy = min(max(vy, -GameConstraints.getInstance().getMaxSpeed()), GameConstraints.getInstance().getMaxSpeed());
        isMoving = true;
    }

    public void decelerate(){
        vx = vx * GameConstraints.getInstance().getShipDeceleration();
        vy = vy * GameConstraints.getInstance().getShipDeceleration();
        if(vx < 0.1 && vx > -0.1)
            vx = 0;
        if(vy < 0.1 && vy > -0.1)
            vy = 0;
        if(vx == 0 && vy == 0)
            isMoving = false;
    }

    /**
     *  Ruota l'astronave a sinistra in base al coefficiente di rotazione
     */
    public void rotateLeft(){
        rotation -= GameConstraints.getInstance().getShipRotation();
    }

    /**
     *  Ruota l'astronave a destra in base al coefficiente di rotazione
     */
    public void rotateRight(){
        rotation += GameConstraints.getInstance().getShipRotation();
    }

    public void shoot() {
        if(shotTick % Math.round(GameConstraints.getInstance().getBulletRatio()) == 0) {
            double bulletVX = (GameConstraints.getInstance().getBulletSpeed() * Math.sin(Math.toRadians(rotation)));
            double bulletVY = -(GameConstraints.getInstance().getBulletSpeed() * Math.cos(Math.toRadians(rotation)));
            Game.getInstance().addBullet(new Bullet(x, y, bulletVX, bulletVY, GameConstraints.getInstance().getBulletDamage(), rotation));
        }
        shotTick++;
    }

    public void startShooting() {
        isShooting = true;
    }

    public void stopShooting() {
        isShooting = false;
        shotTick = 0;
    }

    public void startAccelerating() {
        isAccelerating = true;
    }

    public void stopAccelerating() {
        isAccelerating = false;
    }

    public void startMoving() {
        isMoving = true;
    }

    public void stopMoving() {
        isMoving = false;
    }

    public void startRotatingLeft() {
        isRotatingLeft = true;
    }

    public void stopRotatingLeft() {
        isRotatingLeft = false;
    }

    public void startRotatingRight() {
        isRotatingRight = true;
    }

    public void stopRotatingRight() {
        isRotatingRight = false;
    }

    public void reset() {
        x = (double) GameConstraints.WORLDWIDTH / 2;
        y = (double) GameConstraints.WORLDHEIGHT / 2;
        vx = 0;
        vy = 0;
        rotation = 0;
        isMoving = false;
        isShooting = false;
        isRotatingLeft = false;
        isRotatingRight = false;
        isAccelerating = false;
    }
}
