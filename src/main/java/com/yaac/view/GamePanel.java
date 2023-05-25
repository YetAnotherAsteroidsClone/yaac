package com.yaac.view;

import com.yaac.model.Game;
import com.yaac.model.GameConstraints;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.ObjectAnimation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {

    Game game = Game.getInstance();

    int tick = 0;

    private BufferedImage spaceship;
    private ObjectAnimation backgroundL1;
    private ObjectAnimation backgroundL2;
    private ObjectAnimation backgroundL3;

    public GamePanel(){
        spaceship = ImageUtility.loadImage("/GameSprite/Body1.png");
        backgroundL1 = new ObjectAnimation("/Background/BackgroundL1.png", 640, 360);
        backgroundL2 = new ObjectAnimation("/Background/GameBackgroundL2.png", 640, 360);
        backgroundL3 = new ObjectAnimation("/Background/GameBackgroundL3.png", 640, 360);
        backgroundL1.scaleImage(GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);
        backgroundL2.scaleImage(GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);
        backgroundL3.scaleImage(GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        backgroundL1.update();
        backgroundL2.update();
        backgroundL3.update();
        g.drawImage(backgroundL1.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL2.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL3.getCurrentFrame(), 0, 0, null);
        g.drawImage(ImageUtility.rotateImage(spaceship, game.getSpaceShip().getRotation()), (int) game.getSpaceShip().getX() - 24, (int) game.getSpaceShip().getY() - 24, null);
        g.setColor(Color.WHITE);
        for(int i = 0; i < game.getBullets().size(); i++){
            g.fillOval((int) game.getBullets().get(i).getX(), (int) game.getBullets().get(i).getY(), (int) game.getBullets().get(i).getRadius(), (int) game.getBullets().get(i).getRadius());
            System.out.println("Bullet " + i + " X: " + game.getBullets().get(i).getX() + " Y: " + game.getBullets().get(i).getY());
        }
        tick++;
    }

    public void update(){
        super.repaint();
    }

}
