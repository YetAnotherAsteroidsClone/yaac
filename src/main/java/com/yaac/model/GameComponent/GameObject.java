package com.yaac.model.GameComponent;

/**
 *  Classe astratta per la gestione di tutti i componenti <br>
 *  Implementa posizione, rotazione e raggio (per la collider detection)
 */

public abstract class GameObject{
    protected long tick = 0;
    protected double vx = 0;
    protected double vy = 0;
    protected double x;
    protected double y;
    protected int rotation;
    protected double radius = 0;




    /**
     *  Costruttore della classe GameObject
     *  @param x posizione x
     *  @param y posizione y
     *  @param rotation rotazione
     */
    protected GameObject(double x, double y, int rotation){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    /**
     *  Costruttore della classe GameObject con raggio
     *  @param x posizione x
     *  @param y posizione y
     *  @param rotation rotazione
     *  @param radius raggio
     */
    protected GameObject(double x, double y, int rotation, double radius){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.radius = radius;
    }

    /**
     *  Costruttore della classe GameObject con raggio e velocità
     *  @param x posizione x
     *  @param y posizione y
     *  @param rotation rotazione
     *  @param radius raggio
     *  @param vx velocità su asse x
     *  @param vy velocità su asse y
     */
    protected GameObject(double x, double y, double vx, double vy, int rotation, double radius){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.rotation = rotation;
        this.radius = radius;
    }


    public double getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getRotation() {
        return rotation;
    }
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
    public double getRadius() {
        return radius;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }

    public abstract void update();
    public abstract int getType();
    public long getTick(){
        return tick;
    }

    public void setTick(long i) {
        this.tick = i;
    }
}