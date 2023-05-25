package com.yaac.model.GameComponent;
/**
 *  Classe astratta per la gestione di tutti i componenti che si muovono nel gioco <br>
 *  Implementa la classe GameObject e aggiunge i metodi per il movimento
 */
public abstract class MovableObject extends GameObject{
    protected double vx = 0;
    protected double vy = 0;
    boolean isMoving = false;

    /**
     *  Costruttore della classe MovableObject
     *  @param x posizione x
     *  @param y posizione y
     */
    public MovableObject(int x, int y){
        super(x, y);
    }

    /**
     *  Costruttore della classe MovableObject con velocità iniziale <br>
     *  @param x posizione x
     *  @param y posizione y
     *  @param vx velocità su asse x
     *  @param vy velocità su asse y
     */
    public MovableObject(int x, int y, int vx, int vy){
        super(x, y);
        this.vx = vx;
        this.vy = vy;
    }

    /**
     *  Metodo per muovere l'oggetto di uno step
     */

    public abstract void update();
}
