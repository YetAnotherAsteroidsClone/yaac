package com.yaac.model.GameComponent;

/**
 * Classe per la gestione delle gemme <br>
 * Implementa la classe GameObject
 * @see GameObject
 */
@SuppressWarnings("unused")
public class Gem extends GameObject{
    private final int type;

    /**
     * Costruttore della gemma
     * @param x posizione x
     * @param y posizione y
     * @param type tipo di gemma
     */
    public Gem(double x, double y, int type){
        super(x,y);
        this.type = type;
        radius = 16;
    }

    /**
     * Metodo per aggiornare i tick della gemma
     */
    @Override
    public void update() {
        tick++;
    }

    /**
     * Getter del tipo di gemma (1,2,3) <br>
     * @return tipo di gemma
     */
    @Override
    public int getType() {
        return type;
    }
}
