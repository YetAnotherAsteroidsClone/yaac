package com.yaac;

public class Settings {
    private static Settings instance = null;
    public static final String TITLE = "YAAC - Yet Another Asteroids Clone";
    public int width = 800;
    public int height = 600;
    public String resourcePath = "/main/resources/";

    private Settings(){}

    public static Settings getInstance(){
        if(instance == null){
            instance = new Settings();
        }
        return instance;
    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }
}
