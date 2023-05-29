package com.yaac.model.GameComponent;

import com.yaac.model.GameConstraints;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Classe dedicata alla gestione degli asteroidi
 * Estende la classe MovableObject
 * Implementa la gestione della vita
 */
public class Asteroid extends GameObject{
    private double life;

    /**
     * Costruttore dell'asteroide con parametri
     * @param x posizione x
     * @param y posizione y
     * @param vx velocità iniziale su asse x
     * @param vy velocità iniziale su asse y
     * @param life vita dell'asteroide
     */
    public Asteroid(int x, int y, double vx, double vy, double life, int radius) {
        super(x, y, vx, vy, 0, radius);
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
    public double getLife() {
        return life;
    }

    /**
     * Applica il danno all'asteroide
     * @param damage danno da applicare
     * @return true se l'asteroide è stato distrutto, false altrimenti
     */
    public boolean receiveDamage(double damage){
        this.life -= damage;
        return this.life <= 0;
    }

    public void move() {
        x = x + vx;
        if(x > GameConstraints.WORLDWIDTH || x < 1){
            vx = -vx;
            x = min(max(x, 1), GameConstraints.WORLDWIDTH);
        }
        y = y + vy;
        if(y > GameConstraints.WORLDHEIGHT || y < 1) {
            vy = -vy;
            y = min(max(y, 1), GameConstraints.WORLDHEIGHT);
        }
        rotation += 2;
    }

    public void bounce(){
        vx = -vx;
        vy = -vy;
    }

    public void update() {
        tick++;
        move();
    }


    @Override
    public int getType() {
        return 0;
    }
}
