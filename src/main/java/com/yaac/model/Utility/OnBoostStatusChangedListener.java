package com.yaac.model.Utility;
/**
 * Interfaccia che rappresenta un listener per il cambio di stato del boost <br>
 *  Implementata da Game
 * @see com.yaac.model.Game
 */
public interface OnBoostStatusChangedListener {
    /**
     * Metodo che viene chiamato quando il boost cambia stato
     * @see com.yaac.model.Game
     */
    void onBoostStatusChanged();
}
