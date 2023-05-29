package com.yaac.controller;

import com.yaac.model.Game;
import com.yaac.view.GamePanel;
import com.yaac.view.SceneManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Classe che gestisce gli input da tastiera del gioco
 * e aggiorna la view
 */
public class GameController extends KeyAdapter implements Updatable {

    private final GamePanel gamePanel;

    /**
     * Costruttore
     * @param gamePanel pannello di gioco
     */
    public GameController(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    /**
     * Metodo che gestisce le pressioni dei tasti
     * @param e evento da tastiera
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A -> Game.getInstance().startRotateLeft();
            case KeyEvent.VK_D -> Game.getInstance().startRotateRight();
            case KeyEvent.VK_W -> {Game.getInstance().startAccelerate() ; gamePanel.getSpaceShipView().setPowering(true);}
            case KeyEvent.VK_SPACE -> {Game.getInstance().startShoot(); gamePanel.getSpaceShipView().setCurrentWeapon(true);}
        }
    }

    /**
     * Metodo che gestisce il rilascio dei tasti
     * @param e evento da tastiera
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A -> Game.getInstance().stopRotateLeft();
            case KeyEvent.VK_D -> Game.getInstance().stopRotateRight();
            case KeyEvent.VK_W -> {Game.getInstance().stopAccelerate(); gamePanel.getSpaceShipView().setPowering(false);}
            case KeyEvent.VK_SPACE -> {Game.getInstance().stopShot(); gamePanel.getSpaceShipView().setCurrentWeapon(false);}
        }
    }

    /**
     * Metodo che aggiorna il gioco
     * @return true se l'oggetto è stato aggiornato correttamente false altrimenti <br>
     */
    @Override
    public boolean update(){
        if(!SceneManager.isLoaded(this.gamePanel))
            return false;
        Game.getInstance().update();
        gamePanel.update();
        return true;
    }
}
