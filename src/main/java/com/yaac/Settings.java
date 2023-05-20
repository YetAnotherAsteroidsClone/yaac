package com.yaac;

public class Settings {
    private Settings instance = null;

    private Settings(){}

    public Settings getInstance(){
        if(instance == null){
            instance = new Settings();
        }
        return instance;
    }
}
