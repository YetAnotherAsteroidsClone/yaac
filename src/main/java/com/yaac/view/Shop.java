package com.yaac.view;
import com.yaac.Settings;
import com.yaac.controller.MainMenuController;
import com.yaac.model.GameConstraints;
import com.yaac.view.Utility.ImageUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Shop extends JPanel {
    BufferedImage background;
    BufferedImage[] spaceShipImages = new BufferedImage[3];     //BufferedImage[0] = SPACESHIP; BufferedImage[1] = ENGINE; BufferedImage[2] = WEAPON
    //BufferedImage[] PowerUpImages = new BufferedImage[5];      //PowerUpImages[0] = SPEED; PowerUpImages[1] = BULLET SPEED; PowerUpImages[2] = BULLET DAMAGE; PowerUpImages[3] = BULLET RATIO; PowerUpImages[4] = SHIELD

    private GameConstraints gameConstraints = GameConstraints.getInstance();

    public Shop(){
        this.addMouseListener(new MainMenuController());
        //bg image
        background = ImageUtility.loadImage("/Background/StaticBackground.png");
        background = ImageUtility.scaleImage(background, Settings.width, Settings.height);
        //spaceship images
        spaceShipImages[0] = ImageUtility.loadImage("/GameSprite/Body1.png");
        spaceShipImages[0] = ImageUtility.scaleImage(spaceShipImages[0],350,350);
        spaceShipImages[1] = ImageUtility.loadImage("/GameSprite/BaseEngine.png");
        spaceShipImages[1] = ImageUtility.scaleImage(spaceShipImages[1],150,150);
        spaceShipImages[2] = ImageUtility.loadImage("/GameSprite/Body1.png");
        spaceShipImages[2] = ImageUtility.scaleImage(spaceShipImages[2],10,10);
/*
        //PowerUp images
        PowerUpImages[0] = ImageUtility.loadImage("/GameSprite/");
        PowerUpImages[0] = ImageUtility.scaleImage(PowerUpImages[0],0,0);
        PowerUpImages[1] = ImageUtility.loadImage("/GameSprite/");
        PowerUpImages[1] = ImageUtility.scaleImage(PowerUpImages[1],0,0);
        PowerUpImages[2] = ImageUtility.loadImage("/GameSprite/");
        PowerUpImages[2] = ImageUtility.scaleImage(PowerUpImages[2],0,0);
        PowerUpImages[3] = ImageUtility.loadImage("/GameSprite/");
        PowerUpImages[3] = ImageUtility.scaleImage(PowerUpImages[3],0,0);
        PowerUpImages[4] = ImageUtility.loadImage("/GameSprite/");
        PowerUpImages[4] = ImageUtility.scaleImage(PowerUpImages[4],0,0);*/
    }

    private void fillPwUpBar(int x, int y, int levels, Graphics g, Color c){
        g.setColor(c);
        for(int i=0; i<levels; i++){
            g.fillRect(x,y,30,28);
            x+=30;
        }
        g.setColor(Color.WHITE);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage((Image)background,0,0,null);
        g.drawImage((Image) spaceShipImages[1],420,190,null);
        g.drawImage((Image) spaceShipImages[0],320,0,null);
        g.setColor(Color.WHITE);
        g.drawLine(200,350,800,350);

        //powerup images
        g.drawRect(80,400,40,40);
        g.drawRect(80,500,40,40);
        g.drawRect(550,400,40,40);
        g.drawRect(550,500,40,40);

        //powerup bars
        int barWidth = 301;
        int barHeight = 30;
        g.drawRect(135,410,barWidth,barHeight);
        fillPwUpBar(136,411,gameConstraints.getLvlMaxSpeed(),g, Color.YELLOW);

        g.drawRect(135,510,barWidth,barHeight);
        fillPwUpBar(136,511,gameConstraints.getLvlBulletSpeed(),g, Color.YELLOW);

        g.drawRect(605,410,barWidth,barHeight);
        fillPwUpBar(606,411,gameConstraints.getLvlBulletDamage(),g, Color.YELLOW);

        g.drawRect(605,510,barWidth,barHeight);
        fillPwUpBar(606,511,gameConstraints.getLvlBulletRatio(),g, Color.YELLOW);
    }
}