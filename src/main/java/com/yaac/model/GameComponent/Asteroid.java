package com.yaac.model.GameComponent;

/**
 * Classe dedicata alla gestione degli asteroidi
 * Estende la classe MovableObject
 * Implementa la gestione della vita
 */
public class Asteroid extends MovableObject{
    private int life;

    /**
     * Costruttore dell'asteroide con parametri
     * @param x posizione x
     * @param y posizione y
     * @param vx velocità iniziale su asse x
     * @param vy velocità iniziale su asse y
     * @param life vita dell'asteroide
     */
    public Asteroid(int x, int y, int vx, int vy, int life) {
        super(x, y, vx, vy);
        this.life = life;
    }

    /**
     * Setter della vita dell'asteroide
     * @param life vita dell'asteroide
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Getter della vita dell'asteroide
     * @return vita dell'asteroide
     */
    public int getLife() {
        return life;
    }

    /**
     * Applica il danno all'asteroide
     * @param damage danno da applicare
     * @return true se l'asteroide è stato distrutto, false altrimenti
     */
    public boolean getDamage(int damage){
        this.life -= damage;
        return this.life <= 0;
    }

    public void move() {
        x = x + vx;
        y = y + vy;
    }

    public void update() {
        move();
    }
}
