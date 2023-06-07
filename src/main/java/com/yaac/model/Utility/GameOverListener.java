package com.yaac.model.Utility;

/** Interfaccia per la gestione del GameOver <br>
 *  Implementata da Game
 * @see com.yaac.model.Game
 */
public interface GameOverListener {
    /**
     * Metodo che viene chiamato alla fine del gioco (quando le vite sono a 0)
     * @see com.yaac.model.Game
     */
    void onGameOver();
}
