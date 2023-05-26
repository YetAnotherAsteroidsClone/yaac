package com.yaac.view;
import com.yaac.Settings;
import com.yaac.controller.ShopController;
import com.yaac.model.GameConstraints;
import com.yaac.view.Utility.ImageUtility;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Shop extends JPanel {
    BufferedImage background;
    private BufferedImage[] spaceShipImages = new BufferedImage[3];     //spaceShipImages[0] = SPACESHIP; spaceShipImages[1] = ENGINE; spaceShipImages[2] = WEAPON
    private BufferedImage[] PowerUpImages = new BufferedImage[5];      //PowerUpImages[0] = SPEED; PowerUpImages[1] = BULLET SPEED; PowerUpImages[2] = BULLET DAMAGE; PowerUpImages[3] = BULLET RATIO; PowerUpImages[4] = SHIELD

    public JButton[] buttons = new JButton[5];     //buttons[0] = SPEED; buttons[1] = BULLET SPEED; buttons[2] = BULLET DAMAGE; buttons[3] = BULLET RATIO; buttons[4] = SHIELD

    private int widthOffset = Settings.width/2;
    private int heightOffset = Settings.height/2;
    private ShopController controller = new ShopController(this);
    private GameConstraints gameConstraints = GameConstraints.getInstance();

    public Shop(){
        this.setPreferredSize(new Dimension(Settings.width, Settings.height));
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

        //PowerUp images
        /*
        PowerUpImages[0] = ImageUtility.loadImage("/GameSprite/");
        PowerUpImages[0] = ImageUtility.scaleImage(PowerUpImages[0],0,0);
        PowerUpImages[1] = ImageUtility.loadImage("/GameSprite/");
        PowerUpImages[1] = ImageUtility.scaleImage(PowerUpImages[1],0,0);
        */
        PowerUpImages[2] = ImageUtility.loadImage("/GameSprite/bulletDamage.png");
        PowerUpImages[2] = ImageUtility.scaleImage(PowerUpImages[2],38,38);
        PowerUpImages[3] = ImageUtility.loadImage("/GameSprite/bulletRatio.png");
        PowerUpImages[3] = ImageUtility.scaleImage(PowerUpImages[3],38,38);
        PowerUpImages[4] = ImageUtility.loadImage("/GameSprite/shieldShop.png");
        PowerUpImages[4] = ImageUtility.scaleImage(PowerUpImages[4],38,38);

        for(int i=0; i<buttons.length; i++){
            buttons[i] = new JButton();
        }
        buttons[0].addActionListener(actionEvent -> {gameConstraints.setLvlMaxSpeed(gameConstraints.getLvlMaxSpeed()+1); super.repaint();});
        buttons[1].addActionListener(actionEvent -> {gameConstraints.setLvlBulletSpeed(gameConstraints.getLvlBulletSpeed()+1); super.repaint();});
        buttons[2].addActionListener(actionEvent -> {gameConstraints.setLvlBulletDamage(gameConstraints.getLvlBulletDamage()+1); super.repaint();});
        buttons[3].addActionListener(actionEvent -> {gameConstraints.setLvlBulletRatio(gameConstraints.getLvlBulletRatio()+1); super.repaint();});
        buttons[4].addActionListener(actionEvent -> {gameConstraints.setShopShield(true); super.repaint();});
    }

    private void drawShip(int x, int y, Graphics g){
        g.drawImage((Image) spaceShipImages[0], x, y, null);
    }

    private void drawBar(int x, int y, int levels, BufferedImage img, JButton b, Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(x-55,y-10,40,40);
        g.drawImage((Image) img,x-54,y-9,null);
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

        b.setBounds(x+315,y+1,28,28);

        ImageIcon plusButton = new ImageIcon(getClass().getResource("/MenuSprite/plusButton.png"));
        Image image = plusButton.getImage();
        Image resizedImage = image.getScaledInstance(30,30, Image.SCALE_SMOOTH);
        plusButton = new ImageIcon(resizedImage);
        b.setIcon(plusButton);
        this.add(b);
    }

    private void drawShopShield(Graphics g, JButton b){
        g.setColor(Color.BLUE);
        if(gameConstraints.getShopShield()){
            g.setColor(Color.GREEN);
        }
        g.drawRect(widthOffset+470, heightOffset+160, 70,70);
        b.setBounds(widthOffset+471,heightOffset+161,69,69);
        ImageIcon shieldButton = new ImageIcon(getClass().getResource("/GameSprite/shieldShop.png"));
        Image image = shieldButton.getImage();
        Image resizedImage = image.getScaledInstance(68,68, Image.SCALE_SMOOTH);
        shieldButton = new ImageIcon(resizedImage);
        b.setIcon(shieldButton);
        this.add(b);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage((Image)background,0,0,null);
        drawShip(widthOffset-175,heightOffset-350, g);
        g.setColor(Color.WHITE);
        g.drawLine(widthOffset-300,heightOffset+50,widthOffset+300,heightOffset+50);


        drawBar(widthOffset-530, heightOffset+130, gameConstraints.getLvlMaxSpeed(), PowerUpImages[2], buttons[0], g);
        drawBar(widthOffset-530, heightOffset+250, gameConstraints.getLvlBulletSpeed(), PowerUpImages[2], buttons[1], g);
        drawBar(widthOffset, heightOffset+130, gameConstraints.getLvlBulletDamage(), PowerUpImages[2], buttons[2], g);
        drawBar(widthOffset, heightOffset+250, gameConstraints.getLvlBulletRatio(), PowerUpImages[3], buttons[3], g);

        drawShopShield(g, buttons[4]);

    }
}