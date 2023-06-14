package com.yaac.controller;

import com.yaac.view.SceneManager;
import com.yaac.view.Shop;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;


/**
 * Controller dello shop
 */
public class ShopController extends MouseAdapter implements Updatable, KeyListener {
    private final Shop shop;

    public ShopController(Shop shop){this.shop=shop;}

    /**
     * Gestisce il refresh dello shop.
     */
    @Override
    public boolean update(){
        if(!SceneManager.isLoaded(this.shop))
            return false;
        this.shop.update();
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ESCAPE && !SceneManager.getInstance().isInGame())
            SceneManager.getInstance().loadMainMenu();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
