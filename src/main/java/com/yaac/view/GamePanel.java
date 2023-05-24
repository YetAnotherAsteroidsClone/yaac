package com.yaac.view;

import com.yaac.controller.GameController;
import com.yaac.model.Game;
import com.yaac.view.Utility.ImageUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {

    public GamePanel(GameController controller){
        this.addKeyListener(controller);
    }

    private BufferedImage spaceship = ImageUtility.loadImage("/GameSprite/Body1.png");

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageUtility.rotateImage(spaceship, Game.getInstance().getSpaceShip().getRotation()), 0, 0, null);
    }

    public void update(){
        super.repaint();
    }

}
