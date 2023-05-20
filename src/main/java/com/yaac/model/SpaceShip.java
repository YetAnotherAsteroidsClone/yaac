package com.yaac.model;

public class SpaceShip extends MovableObject{
    private int accelerationCoefficient = 1;
    private int rotationCoefficient = 1;

    boolean isShooting = false;
    boolean isAccelerating = false;

    public SpaceShip(int x, int y){
        super(x, y);
    }

    public void setAccelerationCoefficient(int accelerationCoefficient) {
        this.accelerationCoefficient = accelerationCoefficient;
    }

    public void setRotationCoefficient(int rotationCoefficient) {
        this.rotationCoefficient = rotationCoefficient;
    }

    public void accelerate(){
        int accX = (int) (accelerationCoefficient * Math.cos(rotation));
        int accY = (int) (accelerationCoefficient * Math.sin(rotation));
        setVx(getVx() + accX);
        setVy(getVy() + accY);
    }
    public void rotateLeft(){
        rotation -= rotationCoefficient;
    }
    public void rotateRight(){
        rotation += rotationCoefficient;
    }
}
