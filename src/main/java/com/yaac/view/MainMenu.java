package com.yaac.view;

import com.yaac.Settings;
import com.yaac.controller.MainMenuController;
import com.yaac.view.Utility.ImageUtility;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MainMenu extends JPanel {
    BufferedImage background;

    public MainMenu() {
        this.addMouseListener(new MainMenuController());
        background = ImageUtility.loadImage("/GameSprite/Background.png");
        background = ImageUtility.scaleImage(background, Settings.width, Settings.height);


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //TODO
        g.drawImage(background, 0, 0, null);

    }
}
