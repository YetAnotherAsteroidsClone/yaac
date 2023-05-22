package com.yaac;

public class Settings {
    private static Settings instance = null;
    public static final String TITLE = "YAAC - Yet Another Asteroids Clone";
    public static int width = 800;
    public static int height = 600;
    public static String resourcePath = "/main/resources/";
    public static String gameSpritePath = resourcePath +"/GameSprite/";

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }
}
