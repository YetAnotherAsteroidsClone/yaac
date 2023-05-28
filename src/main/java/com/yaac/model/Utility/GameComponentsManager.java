package com.yaac.model.Utility;

import com.yaac.model.GameComponent.GameObject;

import java.util.ArrayList;

/**
 * Classe dedicata per gestire i componenti del gioco (asteroidi, proiettili, ecc.) <br>
 * Estende ArrayList<MovableObject>
 * Implementa il metodo update per aggiornare i componenti
 */
public class GameComponentsManager extends ArrayList<GameObject> {

    public GameComponentsManager() {
        super();
    }

    /**
     * Aggiorna i componenti del gioco
     */
    public void update(){
        for(GameObject obj : this){
            obj.update();
        }
    }

    public void clearAll(){
        this.clear();
    }

    public void add(GameComponentsManager obj){
        this.addAll(obj);
    }

    public void removeArray(GameComponentsManager obj){
        this.removeAll(obj);
    }
}
