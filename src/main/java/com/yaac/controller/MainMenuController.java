package com.yaac.controller;

import com.yaac.Settings;
import com.yaac.view.MainMenu;
import com.yaac.view.SceneManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;

public class MainMenuController extends MouseAdapter implements Updatable {

    private final MainMenu mainMenu;

    public MainMenuController(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Settings.LOGGER.log(Level.INFO, "Mouse clicked on: " + e.getX() + " " + e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //TODO
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //TODO
    }

    @Override
    public boolean update() {
        if (!SceneManager.isLoaded(this.mainMenu))
            return false;
        this.mainMenu.update();
        return true;
    }

}