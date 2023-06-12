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

        // Queste due impostazioni servono a migliorare la qualità del font
        System.setProperty("awt.useSystemAAFontSettings","off");
        System.setProperty("swing.aatext", "false");

        SceneManager.getInstance().openMainFrame();
        SceneManager.getInstance().loadMainMenu();
    }
}