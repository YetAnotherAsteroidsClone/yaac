package com.yaac.controller;

import javax.swing.JPanel;

/**
 * Interfaccia che rende un oggetto aggiornabile. <br>
 * Ogni classe che vuole utilizzare il loop deve implementare questa interfaccia <br>
 * Suggerimenti per l'implementazione: <br>
 * &emsp; - Implementare updatable su un controller <br>
 * &emsp; - Utilizzare un riferimento al JPanel che si sta manipolando all'interno del controller <br>
 * &emsp; - Utilizzare il metodo statico di SceneManager isLoaded passando il JPanel in questione per capire se è in scena o meno <br>
 * @see com.yaac.view.SceneManager#isLoaded(JPanel)
 * @see com.yaac.Loop
 */
public interface Updatable {
    /**
     * Metodo che aggiorna l'oggetto
     * @return true se l'oggetto è stato aggiornato correttamente false altrimenti <br>
     * Il valore di ritorno verrà utilizzato per fermare il loop
     */
    boolean update();
}
