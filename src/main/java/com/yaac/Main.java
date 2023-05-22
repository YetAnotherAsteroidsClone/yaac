package com.yaac;

import com.yaac.view.SceneManager;

public class Main {
    private static final Settings settings = Settings.getInstance();
    public static void main(String[] args) {
        SceneManager.getInstance().openMainFrame();
        SceneManager.getInstance().loadMainMenu();
    }
}