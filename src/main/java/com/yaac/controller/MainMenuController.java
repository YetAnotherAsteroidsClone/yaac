package com.yaac.controller;

import com.yaac.view.MainMenu;
import com.yaac.view.SceneManager;

import java.awt.event.MouseAdapter;

/**
 * Controller del menu principale.
 */
public class MainMenuController extends MouseAdapter implements Updatable {

    private final MainMenu mainMenu;

    /**
     * Costruttore del controller del menu principale.
     * @param mainMenu menu principale
     */
    public MainMenuController(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    /**
     * Gestisce il refresh del menu principale.
     */
    @Override
    public boolean update() {
        if (!SceneManager.isLoaded(this.mainMenu))
            return false;
        this.mainMenu.update();
        return true;
    }

}