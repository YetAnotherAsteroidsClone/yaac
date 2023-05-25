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
public class SpaceShip extends MovableObject{

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
        super(x, y);
    }

    /**
     * Costruttore della spaceship con parametri
     * @param x posizione x
     * @param y posizione y
     * @param vx velocità iniziale su asse x
     * @param vy velocità iniziale su asse y
     */
    public SpaceShip(int x, int y, int vx, int vy){
        super(x, y, vx, vy, 0);
    }


    @Override
    public void update() {
        if(isRotatingLeft)
            rotateLeft();
        if(isRotatingRight)
            rotateRight();
        if(isAccelerating)
            accelerate();
        if(isShooting)
            shoot();
        move();
    }

    /**
     * Metodo non usato
     */
    @Override
    public boolean toBeDeleted() {
        return false;
    }

    /**
     * Muove l'astronave in base alla velocità
     * Se l'astronave raggiunge i bordi del mondo, la velocità viene azzerata
     */
    public void move() {
        x = x + vx;
        if(x > GameConstraints.WORLDWIDTH || x < 0 ){
            vx = 0;
            x = min(max(x, 0), GameConstraints.WORLDWIDTH);
        }
        y = y + vy;
        if(y > GameConstraints.WORLDHEIGHT || y < 0) {
            vy = 0;
            y = min(max(y, 0), GameConstraints.WORLDHEIGHT);
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
        double bulletVX = (GameConstraints.getInstance().getBulletSpeed() * Math.sin(Math.toRadians(rotation)));
        double bulletVY = - (GameConstraints.getInstance().getBulletSpeed() * Math.cos(Math.toRadians(rotation)));
        Game.getInstance().addBullet(new Bullet(x, y, bulletVX, bulletVY, GameConstraints.getInstance().getBulletDamage(), rotation));
    }

    public void startShooting() {
        isShooting = true;
    }

    public void stopShooting() {
        isShooting = false;
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
}
