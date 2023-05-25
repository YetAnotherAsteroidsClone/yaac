package com.yaac.model.GameComponent;

import com.yaac.model.GameConstraints;

import static java.lang.Math.min;
import static java.lang.Math.max;

/**
 * Classe dedicata alla gestione dell'astronave
 * Estende la classe MovableObject
 * Implementa i metodi per la gestione della accelerazione e della rotazione
 */
public class SpaceShip extends MovableObject{
    private int accelerationCoefficient = 2;
    private int rotationCoefficient = 2;

    boolean isShooting = false;
    boolean isMoving = false;

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
        super(x, y, vx, vy);
    }


    @Override
    public void update() {
        move();
    }

    /**
     * Muove l'astronave in base alla velocità
     * Se l'astronave raggiunge i bordi del mondo, la velocità viene azzerata
     */
    public void move() {
        x = x + vx;
        if(x > GameConstraints.WORLDWIDTH || x < 20 ){
            vx = 0;
            x = min(max(x, 20), GameConstraints.WORLDWIDTH);
        }
        y = y + vy;
        if(y > GameConstraints.WORLDHEIGHT || y < 20) {
            vy = 0;
            y = min(max(y, 20), GameConstraints.WORLDHEIGHT);
        }
    }

    /**
     * Setter del coefficiente di accelerazione <br>
     * Il coefficiente di accelerazione è un valore che indica quanto rapidamente aumenta la velocità
     * durante l'accelerazione per ogni step di gioco
     * @param accelerationCoefficient coefficiente di accelerazione
     */
    public void setAccelerationCoefficient(int accelerationCoefficient) {
        this.accelerationCoefficient = accelerationCoefficient;
    }

    /**
     * Setter del coefficiente di rotazione <br>
     * Il coefficiente di rotazione è un valore che indica quanto rapidamente ruota l'astronave
     * durante la virata per ogni step di gioco
     * @param rotationCoefficient coefficiente di rotazione
     */
    public void setRotationCoefficient(int rotationCoefficient) {
        this.rotationCoefficient = rotationCoefficient;
    }

    /**
     * Accelera l'astronave in base al coefficiente di accelerazione e alla rotazione <br>
     * La velocità viene aumentata e divisa in due componenti, una per asse
     * in base alla direzione dell'astronave
     */
    public void accelerate(){
        if (!isMoving)
            isMoving = true;
        double accX = (accelerationCoefficient * Math.sin(Math.toRadians(rotation)));
        double accY = - (accelerationCoefficient * Math.cos(Math.toRadians(rotation)));
        vx += accX;
        vy += accY;
    }

    /**
     *  Ruota l'astronave a sinistra in base al coefficiente di rotazione
     */
    public void rotateLeft(){
        rotation -= rotationCoefficient;
    }

    /**
     *  Ruota l'astronave a destra in base al coefficiente di rotazione
     */
    public void rotateRight(){
        rotation += rotationCoefficient;
    }
}
