package com.yaac.model.GameComponent;

/**
 *  Classe astratta per la gestione di tutti i componenti <br>
 *  Implementa posizione, rotazione e raggio (per la collider detection)
 */

public abstract class GameObject{
    protected double x;
    protected double y;
    protected int rotation = 0;
    protected double radius = 0;


    /**
     *  Costruttore della classe GameObject
     *  @param x posizione x
     *  @param y posizione y
     */
    protected GameObject(int x, int y){
        this.x = x;
        this.y = y;
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
}