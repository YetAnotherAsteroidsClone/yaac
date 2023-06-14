package com.yaac.controller;

import com.yaac.view.Credits;
import com.yaac.view.SceneManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CreditsController extends KeyAdapter implements Updatable {

    private final Credits credits;

    public CreditsController(Credits credits){
        this.credits = credits;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            SceneManager.getInstance().loadMainMenu();
        }
    }
    @Override
    public boolean update(){
        if(!SceneManager.isLoaded(this.credits))
            return false;
        this.credits.update();
        return true;
    }

}
