package com.yaac.model.GameComponent;

import com.yaac.model.Game;
import com.yaac.model.GameConstraints;

import static java.lang.Math.min;
import static java.lang.Math.max;

/**
 * Classe dedicata alla gestione dell'astronave
 * Estende la classe MovableObject
 * Implementa i metodi per la gestione della accelerazione e della rotazione
 */
public class SpaceShip extends GameObject{
    int shotTick = 0;
    int bulletType = 0;
    boolean isMoving = false;
    boolean isShooting = false;
    boolean isRotatingLeft = false;
    boolean isRotatingRight = false;
    boolean isAccelerating = false;

    /**
     * Costruttore di default con posizione iniziale
     * @param x posizione x
     * @param y posizione y
     */
    public SpaceShip(int x, int y){
        super(x, y, 0);
        radius = 32;
    }

    /**
     * Costruttore della spaceship con parametri
     * @param x posizione x
     * @param y posizione y
     * @param vx velocità iniziale su asse x
     * @param vy velocità iniziale su asse y
     */
    public SpaceShip(int x, int y, int vx, int vy, int bulletType){
        super(x, y, vx, vy, 0, 24);
        this.bulletType = bulletType;
    }

    /**
     * Metodo che aggiorna il comportamento dell'astronave
     * Gestisce la rotazione, l'accelerazione, la decelerazione e lo sparo
     */
    @Override
    public void update() {
        if(isRotatingLeft)
            rotateLeft();
        if(isRotatingRight)
            rotateRight();
        if(isAccelerating)
            accelerate();
        if(isMoving)
            decelerate();
        if(isShooting)
            shoot();
        move();
        tick++;
    }


    /**
     * Metodo che restituisce il tipo di astronave
     * METODO ATTUALMENTE NON UTILIZZATO
     * @return tipo di oggetto
     */
    @Override
    public int getType() {
        return 0;
    }

    /**
     * Muove l'astronave in base alla velocità
     * Se l'astronave raggiunge i bordi del mondo, la velocità viene azzerata
     */
    public void move() {
        x = x + vx;
        if(x > GameConstraints.WORLDWIDTH || x < 1 ){
            vx = 0;
            x = min(max(x, 1), GameConstraints.WORLDWIDTH);
        }
        y = y + vy;
        if(y > GameConstraints.WORLDHEIGHT || y < 1) {
            vy = 0;
            y = min(max(y, 1), GameConstraints.WORLDHEIGHT);
        }
    }


    /**
     * Accelera l'astronave in base al coefficiente di accelerazione e alla rotazione <br>
     * La velocità viene aumentata e divisa in due componenti, una per asse
     * in base alla direzione dell'astronave
     */
    public void accelerate(){
        double accX = (GameConstraints.getInstance().getShipAcceleration() * Math.sin(Math.toRadians(rotation)));
        double accY = - (GameConstraints.getInstance().getShipAcceleration() * Math.cos(Math.toRadians(rotation)));
        vx += accX;
        vy += accY;
        vx = min(max(vx, -GameConstraints.getInstance().getMaxSpeed()), GameConstraints.getInstance().getMaxSpeed());
        vy = min(max(vy, -GameConstraints.getInstance().getMaxSpeed()), GameConstraints.getInstance().getMaxSpeed());
        isMoving = true;
    }

    /**
     * Decelera l'astronave in base al coefficiente di decelerazione <br>
     * La velocità viene diminuita e divisa in due componenti, una per asse
     * in base alla direzione dell'astronave
     */
    public void decelerate(){
        vx = vx * GameConstraints.getInstance().getShipDeceleration();
        vy = vy * GameConstraints.getInstance().getShipDeceleration();
        if(vx < 0.1 && vx > -0.1)
            vx = 0;
        if(vy < 0.1 && vy > -0.1)
            vy = 0;
        if(vx == 0 && vy == 0)
            isMoving = false;
    }

    /**
     *  Ruota l'astronave a sinistra in base al coefficiente di rotazione
     */
    public void rotateLeft(){
        rotation -= GameConstraints.getInstance().getShipRotation();
    }

    /**
     *  Ruota l'astronave a destra in base al coefficiente di rotazione
     */
    public void rotateRight(){
        rotation += GameConstraints.getInstance().getShipRotation();
    }

    /**
     *  Spara un proiettile in base al coefficiente di sparo
     */
    public void shoot() {
        if(shotTick % Math.round(GameConstraints.getInstance().getBulletRatio(bulletType)) == 0) {
            double bulletVX = (GameConstraints.getInstance().getBulletSpeed(bulletType) * Math.sin(Math.toRadians(rotation)));
            double bulletVY = -(GameConstraints.getInstance().getBulletSpeed(bulletType) * Math.cos(Math.toRadians(rotation)));
            Game.getInstance().addBullet(new Bullet(x, y, bulletVX, bulletVY, GameConstraints.getInstance().getBulletDamage(bulletType), rotation, bulletType));
        }
        shotTick++;
    }

    /**
     * Inizia lo sparo
     */
    public void startShooting() {
        isShooting = true;
    }

    /**
     * Termina lo sparo
     */
    public void stopShooting() {
        isShooting = false;
        if (shotTick > GameConstraints.getInstance().getBulletRatio(bulletType))
            shotTick = 1;
    }

    /**
     * Restituisce lo stato dello sparo
     * @return true se sta sparando, false altrimenti
     */
    public boolean isShooting(){
        return isShooting;
    }

    /**
     * Inizia l'accelerazione
     */
    public void startAccelerating() {
        isAccelerating = true;
    }

    /**
     * Termina l'accelerazione
     */
    public void stopAccelerating() {
        isAccelerating = false;
    }

    /**
     * Inizia la rotazione a sinistra
     */
    public void startRotatingLeft() {
        isRotatingLeft = true;
    }

    /**
     * Termina la rotazione a sinistra
     */
    public void stopRotatingLeft() {
        isRotatingLeft = false;
    }


    /**
     * Inizia la rotazione a destra
     */
    public void startRotatingRight() {
        isRotatingRight = true;
    }

    /**
     * Termina la rotazione a destra
     */
    public void stopRotatingRight() {
        isRotatingRight = false;
    }

    /**
     * Resetta l'astronave
     */
    public void reset() {
        x = (double) GameConstraints.WORLDWIDTH / 2;
        y = (double) GameConstraints.WORLDHEIGHT / 2;
        vx = 0;
        vy = 0;
        rotation = 0;
    }

    /**
     * Imposta il tipo di proiettile
     * @return tipo di proiettile
     */
    public void setBulletType(int bulletType){
        this.bulletType = bulletType;
    }
}
