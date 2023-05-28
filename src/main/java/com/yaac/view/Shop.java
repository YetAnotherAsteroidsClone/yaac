package com.yaac.view;
import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.controller.ShopController;
import com.yaac.model.GameConstraints;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.ObjectAnimation;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Shop extends JPanel{
    private ObjectAnimation backgroundL1, backgroundL2;
    Font font;
    private BufferedImage[] spaceShipImages = new BufferedImage[3];     //spaceShipImages[0] = SPACESHIP; spaceShipImages[1] = ENGINE; spaceShipImages[2] = WEAPON
    private BufferedImage[] PowerUpImages = new BufferedImage[5];      //PowerUpImages[0] = SPEED; PowerUpImages[1] = BULLET SPEED; PowerUpImages[2] = BULLET DAMAGE; PowerUpImages[3] = BULLET RATIO; PowerUpImages[4] = SHIELD
    private int[] costs = {1,2,3,4,5,6,7,8,9};

    //COLORS
    private Color orbsColor = Color.CYAN;


    public JButton[] buttons = new JButton[5];     //buttons[0] = SPEED; buttons[1] = BULLET SPEED; buttons[2] = BULLET DAMAGE; buttons[3] = BULLET RATIO; buttons[4] = SHIELD
    private JButton mainMenu, back;
    private int widthOffset = Settings.width/2;
    private int heightOffset = Settings.height/2;
    private ShopController controller = new ShopController(this);
    private GameConstraints gameConstraints = GameConstraints.getInstance();

    public Shop() throws IOException, FontFormatException {
        this.setPreferredSize(new Dimension(Settings.width, Settings.height));
        //bg image
        backgroundL1 = new ObjectAnimation("/Background/BackgroundL1.png", 640, 360);
        backgroundL2 = new ObjectAnimation("/Background/MainBackgroundL2.png", 640, 360);
        backgroundL1.scaleImage(Settings.width, Settings.height);
        backgroundL2.scaleImage(Settings.width, Settings.height);

        font = new Font("font",Font.BOLD,25);

        // Se vuoi cambiare il font ti ho incollato qua la parte di codice che lo fa e ho aggiunto le exception
        //font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getResourceAsStream("/Font.ttf"))).deriveFont(25f);

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

        mainMenu = new JButton();
        mainMenu.addActionListener(actionEvent -> {
            try {
                SceneManager.getInstance().loadMainMenu();
            } catch (IOException | FontFormatException e) {
                throw new RuntimeException(e);
            }
        });
        back = new JButton();
        back.addActionListener(actionEvent -> {SceneManager.getInstance().loadGame();});


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
        int drawPwUpX = x+1;
        for(int i=0; i<levels; i++){
            g.fillRect(drawPwUpX,y+1,30,28);
            drawPwUpX+=30;
        }
        if(levels<10){
            MenuUtility.drawShopButton(b,"/MenuSprite/plusButton.png",x+314,y,30,30,Color.LIGHT_GRAY,g);
            this.add(b);
            g.setColor(orbsColor);
            g.setFont(font);
            g.drawString(""+costs[levels-1], x,y-10);
        }
        else{
            if (b!=null){this.remove(b);}
            g.setColor(Color.YELLOW);
            g.setFont(font);
            g.drawString("MAX!", x,y-10);
        }
    }

    private void drawShopShield(Graphics g, JButton b){
        g.setFont(font);
        if(gameConstraints.getShopShield()){
            MenuUtility.drawShopButton(b,"/GameSprite/shieldShop.png",widthOffset+470,heightOffset+160,70,70,Color.GREEN,g);
            //missing "spunta verde"
        }
        else {MenuUtility.drawShopButton(b,"/GameSprite/shieldShop.png",widthOffset+470,heightOffset+160,70,70,orbsColor,g);}
        g.drawString("missingCost", widthOffset+470, heightOffset+260);
        this.add(b);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundL1.getCurrentFrame(),0, 0, null);
        g.drawImage(backgroundL2.getCurrentFrame(),0, 0, null);
        backgroundL1.update();
        backgroundL2.update();

        if(SceneManager.getInstance().isInGame()){
            MenuUtility.drawShopButton(back,"/MenuSprite/BackButton.png",widthOffset-570,heightOffset-360,40,40,Color.WHITE,g);
            this.add(back);
        }
        MenuUtility.drawShopButton(mainMenu,"/MenuSprite/HomeButton.png",widthOffset-640,heightOffset-360,40,40,Color.WHITE,g);
        this.add(mainMenu);

        g.setFont(font);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("SCORE: "+gameConstraints.getScore(),widthOffset+430,heightOffset-360);
        g.setColor(orbsColor);
        g.drawString("ORBS: "+gameConstraints.getOrbs(),widthOffset+430,heightOffset-325);

        drawShip(widthOffset-175,heightOffset-350, g);
        //missing weapon and engine


        g.setColor(Color.WHITE);
        g.drawLine(widthOffset-300,heightOffset+50,widthOffset+300,heightOffset+50);


        //2 PwUpimages missing
        drawBar(widthOffset-530, heightOffset+130, gameConstraints.getLvlMaxSpeed(), PowerUpImages[2], buttons[0], g);
        drawBar(widthOffset-530, heightOffset+250, gameConstraints.getLvlBulletSpeed(), PowerUpImages[2], buttons[1], g);
        drawBar(widthOffset, heightOffset+130, gameConstraints.getLvlBulletDamage(), PowerUpImages[2], buttons[2], g);
        drawBar(widthOffset, heightOffset+250, gameConstraints.getLvlBulletRatio(), PowerUpImages[3], buttons[3], g);

        drawShopShield(g, buttons[4]);
    }

    public void update() {
        this.repaint();
    }
}