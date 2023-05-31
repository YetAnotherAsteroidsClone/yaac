package com.yaac.model.Utility;

/** Interfaccia per la gestione della morte di un oggetto <br>
 *  Implementata da Game
 * @see com.yaac.model.Game
 */
public interface OnDeathListener {

    /**
     * Metodo che viene chiamato alla morte della nave
     * @see com.yaac.model.Game
     */
    void onDeath();
}
