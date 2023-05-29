package com.yaac.model.GameComponent;

/**
 * Classe per la gestione delle gemme
 * Implementa la classe GameObject
 * @see GameObject
 */
public class Gem extends GameObject{
    private final int type;

    public Gem(double x, double y, int type){
        super(x,y);
        this.type = type;
        radius = 16;
    }

    @Override
    public void update() {
        tick++;
    }

    @Override
    public int getType() {
        return type;
    }
}
