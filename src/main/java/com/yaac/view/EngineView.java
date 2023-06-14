package com.yaac.view;

import com.yaac.view.Utility.ObjectAnimation;

import java.awt.Image;

/** Classe che rappresenta il motore della navicella
 */
@SuppressWarnings("unused")
public class EngineView {

    /** Animazione del motore in stato di idle
     */
    private ObjectAnimation idleState;
    /** Animazione del motore in stato di accensione
     */
    private ObjectAnimation poweringState;

    private Image engine;

    /** Costruttore della classe
     * @param idleState animazione del motore in stato di idle
     * @param poweringState animazione del motore in stato di accensione
     * @param engine immagine del motore
     */
    public EngineView(ObjectAnimation idleState, ObjectAnimation poweringState, Image engine){
        this.idleState = idleState;
        this.poweringState = poweringState;
        this.engine = engine;
    }

    /** Ritorna l'animazione del motore in stato di idle
     * @return animazione del motore in stato di idle
     */
    public ObjectAnimation getIdleState() {
        return idleState;
    }

    /** Ritorna l'animazione del motore in stato di accensione
     * @return animazione del motore in stato di accensione
     */
    public ObjectAnimation getPoweringState() {
        return poweringState;
    }

    /** Ritorna l'immagine del motore
     * @return immagine del motore
     */
    public Image getEngine() {
        return engine;
    }

    /** Imposta l'animazione del motore in stato di idle
     * @param idleState animazione del motore in stato di idle
     */
    public void setIdleState(ObjectAnimation idleState) {
        this.idleState = idleState;
    }

    /** Imposta l'animazione del motore in stato di accensione
     * @param poweringState animazione del motore in stato di accensione
     */
    public void setPoweringState(ObjectAnimation poweringState) {
        this.poweringState = poweringState;
    }

    /** Imposta l'immagine del motore
     * @param engine immagine del motore
     */
    public void setEngine(Image engine) {
        this.engine = engine;
    }
}
