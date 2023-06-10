package com.yaac.controller;
import com.yaac.Settings;
import com.yaac.model.Game;
import com.yaac.model.GameConstraints;
import com.yaac.view.SceneManager;
import com.yaac.view.Shop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;

/**
 * Controller dello shop
 */
public class ShopController extends MouseAdapter implements Updatable{
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

}
