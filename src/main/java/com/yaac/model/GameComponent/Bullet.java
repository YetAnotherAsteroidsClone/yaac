package com.yaac.model.GameComponent;

import com.yaac.model.GameConstraints;

/**
 * Classe dedicata alla gestione dei proiettili
 * Estende la classe MovableObject
 * Implementa la gestione del danno
 */
public class Bullet extends MovableObject{
    private int type = 2;

    private double damage = 1;
    private double radius = 5;
    private boolean collided = false;

    /**
     * Costruttore della projectile con parametri
     * @param x posizione x
     * @param y posizione y
     * @param vx velocità iniziale su asse x
     * @param vy velocità iniziale su asse y
     * @param damage danno della projectile
     */
    public Bullet(double x, double y, double vx, double vy, double damage, int rotation){
        super(x, y, vx, vy, rotation);
        this.damage = damage;
    }

    public Bullet(double x, double y, double vx, double vy, double damage, int rotation, int type){
        super(x, y, vx, vy, rotation);
        this.damage = damage;
        this.type = type;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    /**
     * Setter del danno della projectile
     * @param damage danno della projectile
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Getter del danno della projectile
     * @return danno della projectile
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Ritorna il lifetime del proiettile
     * @return tick della projectile
     */
    public long getTick() {
    	return tick;
    }

    public void move() {
        x = x + vx;
        y = y + vy;
        if (x > GameConstraints.WORLDWIDTH || x < 0 || y > GameConstraints.WORLDHEIGHT || y < 0) {
            collided = true;
        }
    }

    public boolean toBeDeleted() {
    	return collided;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void update() {
        tick++;
        move();
    }
}
