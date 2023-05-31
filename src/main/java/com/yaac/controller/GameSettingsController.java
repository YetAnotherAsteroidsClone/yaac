package com.yaac.controller;

import com.yaac.view.GameSettings;
import com.yaac.view.SceneManager;

import java.awt.event.MouseAdapter;

/**
 * Controller del menu Impostazioni.
 */

public class GameSettingsController extends MouseAdapter implements Updatable {

    private final GameSettings gameSettings;

    /**
     * Costruttore del controller del menu Impostazioni.
     *
     * @param gameSettings menu Impostazioni
     */
    public GameSettingsController(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    /**
     * Gestisce il refresh del menu Impostazioni.
     */
    @Override
    public boolean update() {
        if (!SceneManager.isLoaded(this.gameSettings))
            return false;
        this.gameSettings.update();
        return true;
    }
}