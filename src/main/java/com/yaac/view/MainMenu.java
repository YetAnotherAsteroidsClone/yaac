package com.yaac.view;

import com.yaac.controller.MainMenuController;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    public MainMenu() {
        this.addMouseListener(new MainMenuController());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //TODO
    }
}
