package com.yaac.view;

import com.yaac.controller.MainMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainMenu extends JPanel {
    BufferedImage background;
    Image image;

    public MainMenu() {
        this.addMouseListener(new MainMenuController());
        background = ImageUtility.loadImage("GameSprite/Background.png");
        background = ImageUtility.scaleImage(background, ViewSettings.GameWidth, ViewSettings.GameHeight);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //TODO
        g.drawImage(background, 0, 0, null);
    }
}
