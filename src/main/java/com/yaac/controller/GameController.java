package com.yaac.controller;

import com.yaac.model.Game;
import com.yaac.view.GamePanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController extends KeyAdapter {

    private GamePanel gamePanel;

    public GameController(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A -> Game.getInstance().startRotateLeft();
            case KeyEvent.VK_D -> Game.getInstance().startRotateRight();
            case KeyEvent.VK_W -> Game.getInstance().startAccelerate();
            case KeyEvent.VK_SPACE -> Game.getInstance().startShoot();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A -> Game.getInstance().stopRotateLeft();
            case KeyEvent.VK_D -> Game.getInstance().stopRotateRight();
            case KeyEvent.VK_W -> Game.getInstance().stopAccelerate();
            case KeyEvent.VK_SPACE -> Game.getInstance().stopShot();
        }
    }

    public void update(){
        Game.getInstance().update();
        gamePanel.update();
    }
}
