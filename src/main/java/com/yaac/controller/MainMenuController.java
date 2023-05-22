package com.yaac.controller;

import com.yaac.Settings;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;

public class MainMenuController extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        Settings.LOGGER.log(Level.INFO, "Mouse clicked on: " + e.getX() + " " + e.getY());
    }
}
