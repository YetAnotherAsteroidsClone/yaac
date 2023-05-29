package com.yaac.model.Utility;

import com.yaac.model.GameComponent.GameObject;

import java.util.ArrayList;

/**
 * Classe dedicata per gestire i componenti del gioco (asteroidi, proiettili, ecc.) <br>
 * Estende ArrayList<GameObject>
 * Implementa il metodo update per aggiornare i componenti
 * @see ArrayList
 */
public class GameComponentsManager extends ArrayList<GameObject> {

    /**
     * Costruttore di default
     */
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

    /**
     * Svuota il GameComponentsManager
     */
    public void clearAll(){
        this.clear();
    }

    /**
     * Aggiunge un oggetto al GameComponentsManager
     * @param obj oggetto da aggiungere
     */
    public void add(GameComponentsManager obj){
        this.addAll(obj);
    }

    /**
     * Rimuove un array di oggetti dal GameComponentsManager
     * @param obj array di oggetti da rimuovere
     */
    public void removeArray(GameComponentsManager obj){
        this.removeAll(obj);
    }
}
