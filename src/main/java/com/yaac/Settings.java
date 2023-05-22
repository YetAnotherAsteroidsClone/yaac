package com.yaac;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {
    public static final Logger LOGGER = Logger.getLogger(Settings.class.getName());
    public static final Level logLevel = Level.INFO;
    private static Settings instance = null;
    public static final String TITLE = "YAAC - Yet Another Asteroids Clone";
    public static int width = 800;
    public static int height = 600;
    public static String resourcePath = Main.class.getResource("/").getPath();
    public static String gameSpritePath = resourcePath +"/GameSprite/";
}
