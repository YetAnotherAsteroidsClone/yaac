package com.yaac.model.Utility;

/** Interfaccia per la gestione dello scudo <br>
 *  Implementata da Game
 * @see com.yaac.model.Game
 */
public interface OnShieldStatusChangedListener {
    /**
     * Metodo che viene chiamato quando lo scudo cambia stato
     * @see com.yaac.model.Game
     */
    void onShieldStatusChanged();
}
