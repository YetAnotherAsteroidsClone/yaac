package com.yaac;

import com.yaac.view.SceneManager;

import java.awt.*;
import java.io.IOException;
import java.util.logging.FileHandler;

//Dove tutto ha inizio
public class Main {
    public static void main(String[] args) throws IOException, FontFormatException {
        Settings.LOGGER.setLevel(Settings.logLevel);
        Settings.LOGGER.addHandler(new FileHandler("yaac.log"));

        // Ho trovato online queste due impostazioni che mi sono servite per non far vedere il titolo nel main menu
        // sfocato. Se non le metto, il font viene sfocato e non so perché.
        System.setProperty("awt.useSystemAAFontSettings","off");
        System.setProperty("swing.aatext", "false");

        SceneManager.getInstance().openMainFrame();
        SceneManager.getInstance().loadMainMenu();
    }
}