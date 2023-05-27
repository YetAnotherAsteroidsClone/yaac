package com.yaac;

import com.yaac.view.SceneManager;

import java.awt.*;
import java.io.IOException;
import java.util.logging.FileHandler;

public class Main {
    public static void main(String[] args) throws IOException, FontFormatException {
        Settings.LOGGER.setLevel(Settings.logLevel);
        Settings.LOGGER.addHandler(new FileHandler("yaac.log"));
        SceneManager.getInstance().openMainFrame();
        SceneManager.getInstance().loadMainMenu();
    }
}