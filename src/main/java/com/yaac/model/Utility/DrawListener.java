package com.yaac.model.Utility;

import com.yaac.view.Utility.CompositeSprite;

/** Interfaccia per la gestione degli eventi di disegno <br>
 *  Implementata da {@link CompositeSprite}
 *  @see CompositeSprite
 */
public interface DrawListener {

    /** Chiamato dopo {@link CompositeSprite#draw()} <br>
     *  Azione da eseguire dopo il disegno
     * @see CompositeSprite#draw()
     */
    void drawAction();
}
