package com.yaac.controller;
import com.yaac.Settings;
import com.yaac.model.GameConstraints;
import com.yaac.view.Shop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;

public class ShopController extends MouseAdapter {
    private Shop shop = new Shop();
    private GameConstraints gameConstraints = GameConstraints.getInstance();
    public ShopController(Shop shop){this.shop=shop;}
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }



}
