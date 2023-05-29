package com.yaac.model.GameComponent;

import com.yaac.model.GameConstraints;
import com.yaac.model.Utility.GameComponentsManager;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Classe dedicata alla gestione degli asteroidi <br>
 * Estende la classe GameObject <br>
 * Implementa la gestione della vita, dello score e del della divisione in due asteroidi
 * @see GameObject
 */
public class Asteroid extends GameObject{
    private double life;
    private double score;

    /**
     * Costruttore dell'asteroide con parametri
     * @param x posizione x
     * @param y posizione y
     * @param vx velocità iniziale su asse x
     * @param vy velocità iniziale su asse y
     * @param life vita dell'asteroide
     * @param radius raggio dell'asteroide
     */
    public Asteroid(double x, double y, double vx, double vy, double life, int radius) {
        super(x, y, vx, vy, 0, radius);
        this.life = life;
        score = radius;
    }

    // Getter e Setter
    public void setLife(int life) {
        this.life = life;
    }
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

    /**
     * Metodo che gestisce il movimento dell'asteroide <br>
     * Se l'asteroide tocca i bordi del mondo, rimbalza. <br>
     * Applica una leggera rotazione all'asteroide
     */
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

    /**
     * Metodo che gestisce il rimbalzo dell'asteroide
     */
    public void bounce(){
        vx = -vx;
        vy = -vy;
    }

    /**
     * Metodo che gestisce l'aggiornamento dell'asteroide <br>
     * Aggiorna il tick e il movimento
     */
    public void update() {
        tick++;
        move();
    }

    /**
     * Metodo che gestisce il sottotipo di asteroide
     * ATTUALMENTE NON UTILIZZATO
     */
    @Override
    public int getType() {
        return 0;
    }


    /**
     * Metodo che gestisce la divisione dell'asteroide in due asteroidi <br>
     * @return un nuovo GameComponentsManager contenente i due asteroidi
     * @see GameComponentsManager
     */
    public GameComponentsManager split(){
        GameComponentsManager gameComponentsManager = new GameComponentsManager();
        gameComponentsManager.add(new Asteroid(x, y, vx , -vy, life/2, (int) radius/2));
        gameComponentsManager.add(new Asteroid(x, y, -vx, vy, life/2, (int) radius/2));
        return gameComponentsManager;
    }

    /**
     * Metodo che restituisce lo score dell'asteroide se distrutto
     * @return score dell'asteroide
     */
    public double getScore() {
        return score;
    }
}
