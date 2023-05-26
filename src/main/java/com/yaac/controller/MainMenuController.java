package com.yaac.controller;

import com.yaac.Settings;
import com.yaac.view.GamePanel;
import com.yaac.view.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;

public class MainMenuController extends MouseAdapter {

    private MainMenu mainMenu;

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
}
