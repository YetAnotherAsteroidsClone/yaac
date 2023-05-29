package com.yaac.model.GameComponent;

import com.yaac.model.GameConstraints;

/**
 * Classe dedicata alla gestione dei proiettili
 * Estende la classe MovableObject
 * Implementa la gestione del danno
 */
public class Bullet extends GameObject{
    private int type = 0;
    private double damage;
    private boolean outside = false;

    /**
     * Costruttore della projectile con parametri
     * @param x posizione x
     * @param y posizione y
     * @param vx velocità iniziale su asse x
     * @param vy velocità iniziale su asse y
     * @param damage danno della projectile
     */
    public Bullet(double x, double y, double vx, double vy, double damage, int rotation){
        super(x, y, vx, vy, rotation, 1);
        this.damage = damage;
    }

    public Bullet(double x, double y, double vx, double vy, double damage, int rotation, int type){
        super(x, y, vx, vy, rotation, 1);
        this.damage = damage;
        this.type = type;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void stop() {
        vx = 0;
        vy = 0;
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

    public void move() {
        x = x + vx;
        y = y + vy;
        if (x > GameConstraints.WORLDWIDTH || x < 0 || y > GameConstraints.WORLDHEIGHT || y < 0) {
            outside = true;
        }
    }

    public boolean isOutside() {
        return outside;
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
