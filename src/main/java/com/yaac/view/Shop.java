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
    BufferedImage plusButton;

    private int widthOffset = Settings.width/2;
    private int heightOffset = Settings.height/2;
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

        plusButton = ImageUtility.loadImage("/MenuSprite/plusButton.png");
        plusButton = ImageUtility.scaleImage(plusButton, 28,28);
    }

    private void drawShip(int x, int y, Graphics g){
        g.drawImage((Image) spaceShipImages[0], x, y, null);
    }

    private void drawBar(int x, int y, int levels, Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(x-55,y-10,40,40);
        g.drawRect(x,y,301,30);
        g.setColor(Color.YELLOW);
        int drawX = x+1;
        for(int i=0; i<levels; i++){
            g.fillRect(drawX,y+1,30,28);
            drawX+=30;
        }
        g.setColor(Color.WHITE);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x+314,y,30,30);
        g.drawImage(plusButton, x+315,y+1, null);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage((Image)background,0,0,null);
        drawShip(widthOffset-175,heightOffset-300, g);
        g.setColor(Color.WHITE);
        g.drawLine(widthOffset-300,heightOffset+50,widthOffset+300,heightOffset+50);
        drawBar(widthOffset-377, heightOffset+110, gameConstraints.getLvlMaxSpeed(),g);
        drawBar(widthOffset-377, heightOffset+210, gameConstraints.getLvlBulletSpeed(),g);
        drawBar(widthOffset+93, heightOffset+110, gameConstraints.getLvlBulletDamage(),g);
        drawBar(widthOffset+93, heightOffset+210, gameConstraints.getLvlBulletRatio(),g);
    }
}