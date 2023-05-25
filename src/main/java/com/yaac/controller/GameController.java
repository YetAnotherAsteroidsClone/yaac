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
            case KeyEvent.VK_LEFT -> Game.getInstance().rotateShipLeft();
            case KeyEvent.VK_RIGHT -> Game.getInstance().rotateShipRight();
            case KeyEvent.VK_SPACE -> Game.getInstance().accelerateShip();
        }
    }

    public void update(){
        Game.getInstance().update();
        gamePanel.update();
    }
}
