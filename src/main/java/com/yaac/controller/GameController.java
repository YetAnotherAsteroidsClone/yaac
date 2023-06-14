package com.yaac.controller;

import com.yaac.Loop;
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
    private Loop loop;

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
            case KeyEvent.VK_W -> {
                Game.getInstance().startAccelerate();
                gamePanel.getSpaceShipView().setPowering(true);}
            case KeyEvent.VK_SPACE -> {
                Game.getInstance().startShoot();
                gamePanel.getSpaceShipView().setCurrentWeaponAnimation(true);}
            case KeyEvent.VK_ESCAPE -> {
                Game.getInstance().stopAllActions();
                loop.stop();
                gamePanel.getSpaceShipView().setPowering(false);
                SceneManager.getInstance().loadPauseMenu();
            }
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
            case KeyEvent.VK_SPACE -> {
                Game.getInstance().stopShot();
                gamePanel.getSpaceShipView().setCurrentWeaponAnimation(false);}
            case KeyEvent.VK_S -> Game.getInstance().activateShield();
            case KeyEvent.VK_B -> Game.getInstance().activateBoost();
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

    /** Metodo che setta il loop (fondamentale per poter fermare il gioco)
     * @see Loop
     * @param loop loop di gioco
     */
    public void setLoop(Loop loop){
        this.loop = loop;
    }
}
