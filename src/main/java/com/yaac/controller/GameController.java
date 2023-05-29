package com.yaac.controller;

import com.yaac.model.Game;
import com.yaac.view.GamePanel;
import com.yaac.view.SceneManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController extends KeyAdapter implements Updatable {

    private final GamePanel gamePanel;

    public GameController(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A -> Game.getInstance().startRotateLeft();
            case KeyEvent.VK_D -> Game.getInstance().startRotateRight();
            case KeyEvent.VK_W -> {Game.getInstance().startAccelerate() ; gamePanel.getSpaceShipView().setPowering(true);}
            case KeyEvent.VK_SPACE -> {Game.getInstance().startShoot(); gamePanel.getSpaceShipView().setCurrentWeapon(true);}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A -> Game.getInstance().stopRotateLeft();
            case KeyEvent.VK_D -> Game.getInstance().stopRotateRight();
            case KeyEvent.VK_W -> {Game.getInstance().stopAccelerate(); gamePanel.getSpaceShipView().setPowering(false);}
            case KeyEvent.VK_SPACE -> {Game.getInstance().stopShot(); gamePanel.getSpaceShipView().setCurrentWeapon(false);}
        }
    }

    @Override
    public boolean update(){
        if(!SceneManager.isLoaded(this.gamePanel))
            return false;
        Game.getInstance().update();
        gamePanel.update();
        return true;
    }
}
