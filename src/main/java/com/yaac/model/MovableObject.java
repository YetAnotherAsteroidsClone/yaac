package com.yaac.model;
/**
 *  Classe astratta per la gestione di tutti i componenti che si muovono nel gioco <br>
 *  Implementa la classe GameObject e aggiunge i metodi per il movimento
 */
public class MovableObject extends GameObject{
    private int vx = 0;
    private int vy = 0;
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
    public void move(){
        setX(getX() + getVx());
        setY(getY() + getVy());
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

}
