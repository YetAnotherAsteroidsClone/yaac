package com.yaac.view;
import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.GameConstraints;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Shop extends JPanel{

    //FONT AND COLORS
    Font font, scoreFont;
    private Color gemsColor;
    private Color powerUpColor;

    //IMAGES, ICONS AND ANIMATIONS
    private ObjectAnimation backgroundL1, backgroundL2, gems, shield;
    private BufferedImage[] PowerUpImages = new BufferedImage[5];      //PowerUpImages[0] = SPEED; PowerUpImages[1] = BULLET SPEED; PowerUpImages[2] = BULLET DAMAGE; PowerUpImages[3] = BULLET RATIO; PowerUpImages[4] = SHIELD
    private BufferedImage greenTick, locker;
    private ImageIcon plusIcon, menuIcon, backIcon, right, left;
    private SpaceShipView spaceShipView;

    //BUTTONS
    public JButton[] switchWeapon = new JButton[2];
    public JButton[] switchEngine = new JButton[2];
    public JButton[] pwUpButtons = new JButton[5];     //buttons[0] = SPEED; buttons[1] = BULLET SPEED; buttons[2] = BULLET DAMAGE; buttons[3] = BULLET RATIO; buttons[4] = SHIELD
    private JButton mainMenu, back;

    //OFFSETS
    private int widthOffset = Settings.width/2;
    private int heightOffset = Settings.height/2;

    //OTHERS
    private GameConstraints gameConstraints = GameConstraints.getInstance();


    //CONSTRUCTOR
    public Shop() throws IOException, FontFormatException {
        this.setPreferredSize(new Dimension(Settings.width, Settings.height));
        this.setLayout(null);
        //bg image
        backgroundL1 = new ObjectAnimation("/Background/BackgroundL1.png",640,360);
        backgroundL2 = new ObjectAnimation("/Background/MainBackgroundL2.png",640,360);
        backgroundL1.scaleImage(Settings.width, Settings.height);
        backgroundL2.scaleImage(Settings.width, Settings.height);

        //ImageIcons and animations
        menuIcon = MenuUtility.getImageIcon("/MenuSprite/HomeButton.png",38,38);
        backIcon = MenuUtility.getImageIcon("/MenuSprite/BackButton.png",38,38);
        plusIcon = MenuUtility.getImageIcon("/MenuSprite/plusButton.png",38,38);
        right = MenuUtility.getImageIcon("/MenuSprite/rightArrow.png", 48,58);
        left = MenuUtility.getImageIcon("/MenuSprite/leftArrow.png", 48,58);
        greenTick = ImageUtility.loadImage("/GameSprite/checkmark-64.png");
        greenTick = ImageUtility.scaleImage(greenTick,50,50);
        locker = ImageUtility.loadImage("/GameSprite/locker.png");
        locker = ImageUtility.scaleImage(locker,40,40);
        gems = new ObjectAnimation("/GameSprite/GemL1.png");
        gems.scaleImage(35,35);
        shield = new ObjectAnimation("/GameSprite/ShopShield.png");
        shield.scaleImage(70,70);

        //PowerUp images
        PowerUpImages[0] = ImageUtility.loadImage("/GameSprite/SpeedPwUp.png");
        PowerUpImages[0] = ImageUtility.scaleImage(PowerUpImages[0],38,38);
        /*
        PowerUpImages[1] = ImageUtility.loadImage("/GameSprite/");
        PowerUpImages[1] = ImageUtility.scaleImage(PowerUpImages[1],0,0);
        */
        PowerUpImages[2] = ImageUtility.loadImage("/GameSprite/bulletDamage.png");
        PowerUpImages[2] = ImageUtility.scaleImage(PowerUpImages[2],38,38);
        PowerUpImages[3] = ImageUtility.loadImage("/GameSprite/bulletRatio.png");
        PowerUpImages[3] = ImageUtility.scaleImage(PowerUpImages[3],38,38);

        //Font and colors
        font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getResourceAsStream("/Font.ttf"))).deriveFont(35f);
        scoreFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getResourceAsStream("/Font.ttf"))).deriveFont(50f);
        gemsColor = new Color(250,230,0);
        powerUpColor = new Color(76,44,254);

        //Buttons
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

        for(int i = 0; i<2; i++){
            switchWeapon[i] = new JButton();
            switchEngine[i] = new JButton();
        }

        for(int i = 0; i< pwUpButtons.length; i++){
            pwUpButtons[i] = new JButton();
        }

        //buttons listener
        pwUpButtons[0].addActionListener(actionEvent -> {
            gameConstraints.setGems(gameConstraints.getGems()-gameConstraints.getCost(gameConstraints.getLvlMaxSpeed()-1));
            gameConstraints.setLvlMaxSpeed(gameConstraints.getLvlMaxSpeed()+1);
        });
        pwUpButtons[1].addActionListener(actionEvent -> {
            gameConstraints.setGems(gameConstraints.getGems()-gameConstraints.getCost(gameConstraints.getLvlBulletSpeed()-1));
            gameConstraints.setLvlBulletSpeed(gameConstraints.getLvlBulletSpeed()+1);
        });
        pwUpButtons[2].addActionListener(actionEvent -> {
            gameConstraints.setGems(gameConstraints.getGems()-gameConstraints.getCost(gameConstraints.getLvlBulletDamage()-1));
            gameConstraints.setLvlBulletDamage(gameConstraints.getLvlBulletDamage()+1);
        });
        pwUpButtons[3].addActionListener(actionEvent -> {
            gameConstraints.setGems(gameConstraints.getGems()-gameConstraints.getCost(gameConstraints.getLvlBulletRatio()-1));
            gameConstraints.setLvlBulletRatio(gameConstraints.getLvlBulletRatio()+1);
        });
        pwUpButtons[4].addActionListener(actionEvent -> {
            gameConstraints.setGems(gameConstraints.getGems()-gameConstraints.getShieldCost());
            gameConstraints.setShopShield(true);
        });

        switchWeapon[0].addActionListener(actionEvent -> {spaceShipView.previousWeapon(); spaceShipView.setCurrentWeapon(true);});
        switchWeapon[1].addActionListener(actionEvent -> {spaceShipView.nextWeapon(); spaceShipView.setCurrentWeapon(true);});
        switchEngine[0].addActionListener(actionEvent -> {spaceShipView.previousEngine();});
        switchEngine[1].addActionListener(actionEvent -> {spaceShipView.nextEngine();});

        for(JButton button : switchWeapon)
            this.add(button);
        for(JButton button : switchEngine)
            this.add(button);

        int xOffset = widthOffset-175;
        int yOffset = heightOffset-350;
        MenuUtility.drawJButton(switchWeapon[0],left,xOffset-85,yOffset+50,50,60);
        MenuUtility.drawJButton(switchWeapon[1],right,xOffset+370,yOffset+50,50,60);
        MenuUtility.drawJButton(switchEngine[0],left,xOffset-85,yOffset+250,50,60);
        MenuUtility.drawJButton(switchEngine[1],right,xOffset+370,yOffset+250,50,60);

        //Show score
        JLabel score = new JLabel("SCORE: " + gameConstraints.getScore());
        this.add(score);
        score.setFont(scoreFont);
        score.setForeground(Color.LIGHT_GRAY);
        score.setBounds(52,-2,300,100);

        //spaceship
        spaceShipView = new SpaceShipView(350,350);
        spaceShipView.setCurrentWeapon(true);
        spaceShipView.setPowering(true);
    }

    private void drawShip(int x, int y, Graphics g){
        g.drawImage(spaceShipView.getSpaceship().draw(), x,y,null);
    }
    private void drawBar(int x, int y, String pwUp, int levels, BufferedImage img, JButton b, Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawRect(x-55,y-10,40,40);
        g.drawImage(img,x-54,y-9,null);
        g.drawRect(x,y,301,30);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x+1,y+1,299,28);
        g.setColor(Color.YELLOW);
        int drawPwUpX = x+1;
        for(int i=0; i<levels; i++){
            g.fillRect(drawPwUpX,y+1,30,28);
            drawPwUpX+=30;
        }
        if(levels<10){
            g.drawImage(gems.getCurrentFrame(),x+280,y-45,null);
            g.setColor(gemsColor);
            if(gameConstraints.getGems()<gameConstraints.getCost(levels-1)){
                if(b != null){this.remove(b);}
                g.setColor(Color.RED);
                g.drawImage(locker,x+309,y-5,null);
            }
            else{
                MenuUtility.drawShopButton(b,plusIcon,x+314,y,30,30,Color.LIGHT_GRAY,g);
                this.add(b);
            }
            g.drawString(""+gameConstraints.getCost(levels-1), x+314,y-15);

            g.setColor(Color.WHITE);
            g.drawString("LVL "+levels,x,y-10);
        }
        else{
            this.remove(b);
            g.setColor(Color.YELLOW);
            g.drawString("MAX!", x,y-10);
        }
        g.setColor(powerUpColor);
        g.drawString(pwUp, x+8,y+22);
    }

    private void drawShopShield(Graphics g, JButton b){
        g.setFont(font);
        if(gameConstraints.getShopShield()){
            this.remove(b);
            g.setColor(Color.GREEN);
            g.drawRect(widthOffset+470,heightOffset+160,70,70);
            g.drawImage(shield.getCurrentFrame(),widthOffset+471,heightOffset+161,null);
            g.drawImage(greenTick,widthOffset+480,heightOffset+170,null);
        }
        else {
            g.drawImage(gems.getCurrentFrame(),widthOffset+460, heightOffset+235,null);
            ImageIcon shieldIcon = new ImageIcon(shield.getCurrentFrame());
            if (gameConstraints.getGems() < gameConstraints.getShieldCost()) {
                this.remove(b);
                g.setColor(Color.RED);
                g.drawRect(widthOffset+470,heightOffset+160,70,70);
                g.drawImage(shield.getCurrentFrame(),widthOffset+471,heightOffset+161,null);
                g.drawImage(locker,widthOffset+485,heightOffset+175,null);
            }
            else {
                MenuUtility.drawShopButton(b, shieldIcon, widthOffset + 470, heightOffset + 160, 70, 70, gemsColor, g);
                this.add(b);
            }
            g.drawString(""+gameConstraints.getShieldCost(), widthOffset+495, heightOffset+265);
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //background
        g.drawImage(backgroundL1.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL2.getCurrentFrame(), 0, 0, null);
        backgroundL1.update();
        backgroundL2.update();

        //page buttons
        if(SceneManager.getInstance().isInGame()){
            MenuUtility.drawShopButton(back,backIcon,widthOffset+530,heightOffset-360,40,40,Color.WHITE,g);
            this.add(back);
        }
        MenuUtility.drawShopButton(mainMenu,menuIcon,widthOffset+600,heightOffset-360,40,40,Color.WHITE,g);
        this.add(mainMenu);


        //show gems
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawImage(gems.getCurrentFrame(),52,80,null);
        gems.update();
        g.drawString("x"+gameConstraints.getGems(),widthOffset-590,heightOffset-290);

        //draw ship and components
        drawShip(widthOffset-175,heightOffset-350, g);

        //separator
        g.setColor(Color.WHITE);
        g.drawLine(widthOffset-300,heightOffset+50,widthOffset+300,heightOffset+50);

        //powerup bars
        drawBar(widthOffset-530, heightOffset+130, "Speed", gameConstraints.getLvlMaxSpeed(), PowerUpImages[0], pwUpButtons[0], g);
        drawBar(widthOffset-530, heightOffset+250, "Bullet speed", gameConstraints.getLvlBulletSpeed(), PowerUpImages[2], pwUpButtons[1], g);
        drawBar(widthOffset, heightOffset+130, "Bullet damage", gameConstraints.getLvlBulletDamage(), PowerUpImages[2], pwUpButtons[2], g);
        drawBar(widthOffset, heightOffset+250, "Bullet ratio", gameConstraints.getLvlBulletRatio(), PowerUpImages[3], pwUpButtons[3], g);
        //2 PwUpimages missing

        //purchasable shield
        drawShopShield(g, pwUpButtons[4]);
        shield.update();
    }

    public void update() {super.repaint();}
}