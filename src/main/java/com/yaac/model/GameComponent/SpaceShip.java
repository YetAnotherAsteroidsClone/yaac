package com.yaac.model.GameComponent;

/**
 * Classe dedicata alla gestione dell'astronave
 * Estende la classe MovableObject
 * Implementa i metodi per la gestione della accelerazione e della rotazione
 */
public class SpaceShip extends MovableObject{
    private int accelerationCoefficient = 1;
    private int rotationCoefficient = 1;

    boolean isShooting = false;
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
        super(x, y, vx, vy);
    }


    @Override
    public void update() {
        move();
    }

    /**
     * Muove l'astronave in base alla velocità
     */
    public void move() {
        x = x + vx;
        y = y + vy;
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
        int accX = (int) (accelerationCoefficient * Math.cos(rotation));
        int accY = (int) (accelerationCoefficient * Math.sin(rotation));
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
