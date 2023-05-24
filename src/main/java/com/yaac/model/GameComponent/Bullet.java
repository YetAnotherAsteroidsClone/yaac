package com.yaac.model.GameComponent;

/**
 * Classe dedicata alla gestione dei proiettili
 * Estende la classe MovableObject
 * Implementa la gestione del danno
 */
public class Bullet extends MovableObject{
    private int damage = 1;

    /**
     * Costruttore della projectile con parametri
     * @param x posizione x
     * @param y posizione y
     * @param vx velocità iniziale su asse x
     * @param vy velocità iniziale su asse y
     * @param damage danno della projectile
     */
    public Bullet(int x, int y, int vx, int vy, int damage){
        super(x, y, vx, vy);
        this.damage = damage;
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
    public int getDamage() {
        return damage;
    }

    public void move() {
        x = x + vx;
        y = y + vy;
    }

    @Override
    public void update() {
        move();
    }
}
