package com.yaac.model.GameComponent;

import com.yaac.model.GameConstraints;

/**
 * Classe dedicata alla gestione dei proiettili
 * Estende la classe GameObject
 * Implementa la gestione del danno
 */
public class Bullet extends GameObject{
    private int type = 0;
    private double damage;
    private boolean outside = false;

    /**
     * Costruttore del bullet
     * @param x posizione x
     * @param y posizione y
     * @param vx velocità iniziale su asse x
     * @param vy velocità iniziale su asse y
     * @param damage danno del bullet
     */
    public Bullet(double x, double y, double vx, double vy, double damage, int rotation){
        super(x, y, vx, vy, rotation, 1);
        this.damage = damage;
    }

    /**
     * Costruttore del bullet
     * @param x posizione x
     * @param y posizione y
     * @param vx velocità iniziale su asse x
     * @param vy velocità iniziale su asse y
     * @param damage danno della bullet
     * @param type tipo della bullet (0,1,2,3)
     */
    public Bullet(double x, double y, double vx, double vy, double damage, int rotation, int type){
        super(x, y, vx, vy, rotation, 1);
        this.damage = damage;
        this.type = type;
    }

    public Bullet(double x, double y, double vx, double vy, double damage, int rotation, int type, int radius){
        super(x, y, vx, vy, rotation, radius);
        this.damage = damage;
        this.type = type;
    }

    // Getter e setter
    public void setRadius(double radius) {
        this.radius = radius;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void getRadius(double radius) {
        this.radius = radius;
    }
    public double getDamage() {
        return damage;
    }

    /**
     * Ferma il movimento del bullet
     */
    public void stop() {
        vx = 0;
        vy = 0;
    }

    /**
     * Muove il bullet
     */
    public void move() {
        x = x + vx;
        y = y + vy;
        if (x > GameConstraints.WORLDWIDTH || x < 0 || y > GameConstraints.WORLDHEIGHT || y < 0) {
            outside = true;
        }
    }

    /**
     * Controlla se il bullet è fuori dal mondo
     * @return true se è fuori dal mondo, false altrimenti
     */
    public boolean isOutside() {
        return outside;
    }

    /**
     * Restituisce il tipo del bullet
     * @return tipo del bullet
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * Aggiorna i tick del bullet e il suo movimento
     */
    @Override
    public void update() {
        tick++;
        move();
    }
}
