package com.yaac.view;

import com.yaac.model.Game;
import com.yaac.model.GameConstraints;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    Game game = Game.getInstance();

    int tick = 0;

    private BufferedImage spaceship;
    private ObjectAnimation backgroundL1;
    private ObjectAnimation backgroundL2;
    private ObjectAnimation backgroundL3;
    private ArrayList<ObjectAnimation> bullet;

    public GamePanel(){
        spaceship = ImageUtility.loadImage("/GameSprite/Body1.png");
        backgroundL1 = new ObjectAnimation("/Background/BackgroundL1.png", 640, 360);
        backgroundL2 = new ObjectAnimation("/Background/GameBackgroundL2.png", 640, 360);
        backgroundL3 = new ObjectAnimation("/Background/GameBackgroundL3.png", 640, 360);
        bullet = new ArrayList<>();
        bullet.add(new ObjectAnimation("/GameSprite/BulletBaseCannon.png"));
        bullet.add(new ObjectAnimation("/GameSprite/BulletBigCannon.png"));
        bullet.add(new ObjectAnimation("/GameSprite/BulletRocket.png"));
        bullet.add(new ObjectAnimation("/GameSprite/BulletZapper.png"));
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
        for(int i = 0; i < game.getBullets().size(); i++) {
            ObjectAnimation currentBulletTypeAnimation = bullet.get(game.getBullets().get(i).getType());
            Image currentBulletFrame = currentBulletTypeAnimation.getImage((int) game.getBullets().get(i).getTick() % currentBulletTypeAnimation.size());
            g.drawImage(ImageUtility.rotateImage((BufferedImage) currentBulletFrame, game.getBullets().get(i).getRotation()), (int) game.getBullets().get(i).getX() - 16, (int) game.getBullets().get(i).getY() - 16, null);
        }
        tick++;
    }

    public void update(){
        super.repaint();
    }

}
