package com.yaac;

import com.yaac.model.Game;
import com.yaac.model.Language;
import com.yaac.model.SaveFileManager;
import com.yaac.view.Utility.Sound;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {
    public static final Logger LOGGER = Logger.getLogger(Settings.class.getName());
    public static final Level logLevel = Level.INFO;
    public static final String TITLE = "YAAC - Yet Another Asteroids Clone";
    public static int width = 1280;
    public static int height = 720;
    public static String resourcePath = Main.class.getResource("/").getPath();
    public static final int shipSize = (int) (Game.getInstance().getSpaceShip().getRadius()*2);
    public static final String FONT = "Font.ttf";
    public static Language.languageList language = SaveFileManager.getInstance().getLanguage();
}
