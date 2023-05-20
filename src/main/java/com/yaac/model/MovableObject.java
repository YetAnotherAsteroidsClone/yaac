package com.yaac.model;

public class MovableObject extends GameObject{
    private int vx = 0;
    private int vy = 0;
    boolean isMoving = false;


    public MovableObject(int x, int y){
        super(x, y);
    }

    public void move(){
        setX(getX() + getVx());
        setY(getY() + getVy());
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

}
