package com.yaac.model;

public class SpaceShip extends GameObject{
    private int acceleration;
    private int rotation;

    public SpaceShip(int x, int y, int vx, int vy, int acceleration, int rotation){
        super(x, y, vx, vy);
        this.acceleration = acceleration;
        this.rotation = rotation;
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    public void move(){
        setX(getX() + getVx());
        setY(getY() + getVy());
    }

    public void accelerate(){
        int accX = (int) (acceleration * Math.cos(rotation));
        int accY = (int) (acceleration * Math.sin(rotation));
        setVx(getVx() + accX);
        setVy(getVy() + accY);
    }
}
