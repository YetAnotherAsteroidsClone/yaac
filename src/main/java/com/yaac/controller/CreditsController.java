package com.yaac.controller;

import com.yaac.Loop;
import com.yaac.model.Game;
import com.yaac.view.Credits;
import com.yaac.view.GamePanel;
import com.yaac.view.SceneManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CreditsController extends KeyAdapter implements Updatable {

    private final Credits credits;
    private Loop loop;

    public CreditsController(Credits credits){
        this.credits = credits;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_ESCAPE -> SceneManager.getInstance().loadMainMenu();
        }
    }
    @Override
    public boolean update(){
        if(!SceneManager.isLoaded(this.credits))
            return false;
        this.credits.update();
        return true;
    }

    public void setLoop(Loop loop){
        this.loop = loop;
    }
}
