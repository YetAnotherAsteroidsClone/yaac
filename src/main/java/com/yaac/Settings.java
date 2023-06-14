package com.yaac;

import com.yaac.model.Game;
import com.yaac.model.Language;
import com.yaac.model.SaveFileManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {
    public static final Logger LOGGER = Logger.getLogger(Settings.class.getName());
    public static final Level logLevel = Level.INFO;
    public static final String TITLE = "YAAC - Yet Another Asteroids Clone";
    public static int width;
    public static int height;
    public static int superSecretCounter = 0;
    public static boolean superSecretValue = true;
    public static final String[] resolutions = new String[]{"1920x1080", "1280x720"};
    public static final int shipSize = (int) (Game.getInstance().getSpaceShip().getRadius()*2);
    public static final String FONT = "Font.ttf";
    public static final float FONT_SIZE = 35f;
    public static Language.languageList language = SaveFileManager.getInstance().getLanguage();
}
