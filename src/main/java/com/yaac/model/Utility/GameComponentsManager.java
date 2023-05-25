package com.yaac.model.Utility;

import com.yaac.model.GameComponent.MovableObject;
import java.util.ArrayList;

/**
 * Classe dedicata per gestire i componenti del gioco (asteroidi, proiettili, ecc.) <br>
 * Estende ArrayList<MovableObject>
 * Implementa il metodo update per aggiornare i componenti
 */
public class GameComponentsManager extends ArrayList<MovableObject> {

    public GameComponentsManager() {
        super();
    }

    /**
     * Aggiorna i componenti del gioco
     */
    public void update(){
        for(MovableObject obj : this){
            obj.update();
        }
    }

    public void clearAll(){
        this.clear();
    }

    public void clearDeadObjects(){
        ArrayList<MovableObject> toRemove = new ArrayList<>();
        for(MovableObject obj : this){
            if(obj.toBeDeleted()){
                toRemove.add(obj);
            }
        }
        this.removeAll(toRemove);
    }
}
