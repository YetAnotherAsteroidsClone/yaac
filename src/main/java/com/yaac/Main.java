package com.yaac;

import com.yaac.model.SaveFileManager;
import com.yaac.view.SceneManager;

import java.awt.*;
import java.io.IOException;
import java.util.logging.FileHandler;

//Dove tutto ha inizio
public class Main {
    public static void main(String[] args) throws IOException {
        Settings.LOGGER.setLevel(Settings.logLevel);
        Settings.LOGGER.addHandler(new FileHandler("yaac.log"));

        // Queste due impostazioni servono a migliorare la qualità del font
        System.setProperty("awt.useSystemAAFontSettings","off");
        System.setProperty("swing.aatext", "false");

        Settings.width = Integer.parseInt(SaveFileManager.getInstance().getResolution().split("x")[0]);
        Settings.height = Integer.parseInt(SaveFileManager.getInstance().getResolution().split("x")[1]);

        SceneManager.getInstance().openMainFrame();
        SceneManager.getInstance().loadMainMenu();
    }
}