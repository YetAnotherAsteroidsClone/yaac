package com.yaac.view;
import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.Game;
import com.yaac.model.GameConstraints;
import com.yaac.model.Language;
import com.yaac.model.SaveFileManager;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public class Shop extends JPanel{

    //FONT AND COLORS
    private Font font, scoreFont;
    private JLabel scoreCount, gemCount;
    private Color gemsColor;
    private Color powerUpColor;

    //IMAGES, ICONS AND ANIMATIONS
    private ObjectAnimation backgroundL1, backgroundL2, gems, shield, boost;
    private BufferedImage[] PowerUpImages = new BufferedImage[5];      //PowerUpImages[0] = SPEED; PowerUpImages[1] = BULLET SPEED; PowerUpImages[2] = BULLET DAMAGE; PowerUpImages[3] = BULLET RATIO; PowerUpImages[4] = SHIELD; PowerUpImages[5] = BOOST
    private BufferedImage locker;
    private ImageIcon plusIcon, menuIcon, backIcon, right, left, right1, left1;
    private SpaceShipView spaceShipView;

    //BUTTONS
    public JButton[] switchWeapon = new JButton[2];
    public JButton[] switchEngine = new JButton[2];
    public JButton[] pwUpButtons = new JButton[6];     //buttons[0] = SPEED; buttons[1] = BULLET SPEED; buttons[2] = BULLET DAMAGE; buttons[3] = BULLET RATIO; buttons[4] = SHIELD; buttons[5] = BOOST
    private JButton mainMenu, back;

    //OTHERS
    private GameConstraints gameConstraints = GameConstraints.getInstance();
    private boolean currentWeaponUnlocked;
    private boolean currentEngineUnlocked;

    private boolean layered = false;

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
        menuIcon = ImageUtility.getImageIcon("/MenuSprite/HomeButton.png",38,38);
        backIcon = ImageUtility.getImageIcon("/MenuSprite/BackButton0.png",38,38);
        plusIcon = ImageUtility.getImageIcon("/MenuSprite/plusButton.png",38,38);
        right = ImageUtility.getImageIcon("/MenuSprite/rightArrow0.png", 48,58);
        left = ImageUtility.getImageIcon("/MenuSprite/leftArrow0.png", 48,58);
        right1 = ImageUtility.getImageIcon("/MenuSprite/rightArrow1.png", 48,58);
        left1 = ImageUtility.getImageIcon("/MenuSprite/leftArrow1.png", 48,58);
        locker = ImageUtility.loadImage("/GameSprite/locker.png");
        locker = ImageUtility.scaleImage(locker,40,40);
        gems = new ObjectAnimation("/GameSprite/GemL1.png");
        gems.scaleImage(35,35);
        shield = new ObjectAnimation("/GameSprite/ShopShield.png");
        shield.scaleImage(70,70);
        boost = new ObjectAnimation("/GameSprite/ShopBoost.png");
        boost.scaleImage(70,70);

        //PowerUp images
        PowerUpImages[0] = ImageUtility.loadImage("/GameSprite/SpeedPwUp.png");
        PowerUpImages[0] = ImageUtility.scaleImage(PowerUpImages[0],38,38);
        PowerUpImages[1] = ImageUtility.loadImage("/GameSprite/BulletSpeed.png");
        PowerUpImages[1] = ImageUtility.scaleImage(PowerUpImages[1],38,38);
        PowerUpImages[2] = ImageUtility.loadImage("/GameSprite/bulletDamage.png");
        PowerUpImages[2] = ImageUtility.scaleImage(PowerUpImages[2],38,38);
        PowerUpImages[3] = ImageUtility.loadImage("/GameSprite/bulletRatio.png");
        PowerUpImages[3] = ImageUtility.scaleImage(PowerUpImages[3],38,38);

        //Font and colors
        font = MenuUtility.loadFont(Settings.FONT_SIZE);
        scoreFont = MenuUtility.loadFont(50);
        gemsColor = new Color(250,230,0);
        powerUpColor = new Color(76,44,254);

        //Buttons
        mainMenu = new JButton();
        mainMenu.addActionListener(actionEvent -> {
            if(layered) {
                SceneManager.getInstance().unloadGameOver();
                Game.reset();
            }
            SceneManager.getInstance().loadMainMenu();});

        back = new JButton();
        back.addActionListener(actionEvent -> {SceneManager.getInstance().unloadShop();});

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
            SaveFileManager.getInstance().updateGems();
            gameConstraints.setLvlMaxSpeed(gameConstraints.getLvlMaxSpeed()+1);
            Settings.LOGGER.log(Level.INFO, "Speed upgraded at level "+gameConstraints.getLvlMaxSpeed());
            SaveFileManager.getInstance().saveSpeedLvl();
            gemCount.setText("x"+gameConstraints.getGems());
        });
        pwUpButtons[1].addActionListener(actionEvent -> {
            gameConstraints.setGems(gameConstraints.getGems()-gameConstraints.getCost(gameConstraints.getLvlBulletSpeed()-1));
            SaveFileManager.getInstance().updateGems();
            gameConstraints.setLvlBulletSpeed(gameConstraints.getLvlBulletSpeed()+1);
            Settings.LOGGER.log(Level.INFO, "Bullet speed upgraded at level "+gameConstraints.getLvlBulletSpeed());
            SaveFileManager.getInstance().saveBulletSpeedLvl();
            gemCount.setText("x"+gameConstraints.getGems());
        });
        pwUpButtons[2].addActionListener(actionEvent -> {
            gameConstraints.setGems(gameConstraints.getGems()-gameConstraints.getCost(gameConstraints.getLvlBulletDamage()-1));
            SaveFileManager.getInstance().updateGems();
            gameConstraints.setLvlBulletDamage(gameConstraints.getLvlBulletDamage()+1);
            Settings.LOGGER.log(Level.INFO, "Bullet damage upgraded at level "+gameConstraints.getLvlBulletDamage());
            SaveFileManager.getInstance().saveBulletDmgLvl();
            gemCount.setText("x"+gameConstraints.getGems());
        });
        pwUpButtons[3].addActionListener(actionEvent -> {
            gameConstraints.setGems(gameConstraints.getGems()-gameConstraints.getCost(gameConstraints.getLvlBulletRatio()-1));
            SaveFileManager.getInstance().updateGems();
            gameConstraints.setLvlBulletRatio(gameConstraints.getLvlBulletRatio()+1);
            Settings.LOGGER.log(Level.INFO, "Bullet ratio upgraded at level "+gameConstraints.getLvlBulletRatio());
            SaveFileManager.getInstance().saveBulletRatioLvl();
            gemCount.setText("x"+gameConstraints.getGems());
        });
        pwUpButtons[4].addActionListener(actionEvent -> {
            gameConstraints.setGems(gameConstraints.getGems()-gameConstraints.getShieldCost());
            SaveFileManager.getInstance().updateGems();
            gameConstraints.setShopShield(true);
            Game.getInstance().updatePwUpShield();
            Settings.LOGGER.log(Level.INFO, "Shield bought");
            SaveFileManager.getInstance().setShield(gameConstraints.getShopShield());
            gemCount.setText("x"+gameConstraints.getGems());
        });
        pwUpButtons[5].addActionListener(actionEvent -> {
            gameConstraints.setGems(gameConstraints.getGems()-gameConstraints.getBoostCost());
            SaveFileManager.getInstance().updateGems();
            gameConstraints.setShopBoost(true);
            Game.getInstance().updatePwUpBoost();
            Settings.LOGGER.log(Level.INFO, "Boost bought");
            SaveFileManager.getInstance().setSpeed(gameConstraints.getShopBoost());
            gemCount.setText("x"+gameConstraints.getGems());
        });

        switchWeapon[0].addActionListener(actionEvent ->
        {   spaceShipView.previousWeapon();
            spaceShipView.setCurrentWeaponAnimation(true);
            currentWeaponUnlocked = SaveFileManager.getInstance().saveWeapon(spaceShipView.getCurrentWeapon());
            if(currentWeaponUnlocked)
                Settings.LOGGER.log(Level.INFO, "Weapon changed to "+spaceShipView.getCurrentWeapon());
            else
                Settings.LOGGER.log(Level.INFO, "Weapon "+ spaceShipView.getCurrentWeapon() +" not unlocked");
            this.grabFocus();
        });
        switchWeapon[1].addActionListener(actionEvent ->
        {   spaceShipView.nextWeapon();
            spaceShipView.setCurrentWeaponAnimation(true);
            currentWeaponUnlocked = SaveFileManager.getInstance().saveWeapon(spaceShipView.getCurrentWeapon());
            if(currentWeaponUnlocked)
                Settings.LOGGER.log(Level.INFO, "Weapon changed to "+spaceShipView.getCurrentWeapon());
            else
                Settings.LOGGER.log(Level.INFO, "Weapon "+ spaceShipView.getCurrentWeapon() +" not unlocked");
            this.grabFocus();
        });
        switchEngine[0].addActionListener(actionEvent ->
        {   spaceShipView.previousEngine();
            currentEngineUnlocked = SaveFileManager.getInstance().saveEngine(spaceShipView.getCurrentEngine());
            if(currentEngineUnlocked)
                Settings.LOGGER.log(Level.INFO, "Engine changed to "+spaceShipView.getCurrentEngine());
            else
                Settings.LOGGER.log(Level.INFO, "Engine "+ spaceShipView.getCurrentEngine() +" not unlocked");
            this.grabFocus();
        });
        switchEngine[1].addActionListener(actionEvent ->
        {   spaceShipView.nextEngine();
            currentEngineUnlocked = SaveFileManager.getInstance().saveEngine(spaceShipView.getCurrentEngine());
            if(currentEngineUnlocked)
                Settings.LOGGER.log(Level.INFO, "Engine changed to "+spaceShipView.getCurrentEngine());
            else
                Settings.LOGGER.log(Level.INFO, "Engine "+ spaceShipView.getCurrentEngine() +" not unlocked");
            this.grabFocus();
        });

        for(JButton button : switchWeapon)
            this.add(button);
        for(JButton button : switchEngine)
            this.add(button);

        MenuUtility.drawJButton(switchWeapon[0],left,(Settings.width/2)-260,100,60,60,left1);
        MenuUtility.drawJButton(switchWeapon[1],right,(Settings.width/2)+195,100,60,60,right1);
        MenuUtility.drawJButton(switchEngine[0],left,(Settings.width/2)-260,300,60,60,left1);
        MenuUtility.drawJButton(switchEngine[1],right,(Settings.width/2)+195,300,60,60,right1);

        //Show score and gem count
        scoreCount = MenuUtility.createLabel(Language.allStrings.get(16) + ": " + gameConstraints.getScore(),50,40,300,25,scoreFont,Color.LIGHT_GRAY);
        gemCount = MenuUtility.createLabel("x"+gameConstraints.getGems(),90,95,300,15,font,Color.WHITE);
        this.add(scoreCount);
        this.add(gemCount);

        //spaceship
        spaceShipView = new SpaceShipView(350,350);
        spaceShipView.setCurrentWeaponAnimation(true);
        spaceShipView.setPowering(true);
        currentWeaponUnlocked = true;
        currentEngineUnlocked = true;
    }

    public void setCurrentGadgets(){
        spaceShipView.setCurrentEngine(SaveFileManager.getInstance().getEngine());
        spaceShipView.setCurrentWeapon(SaveFileManager.getInstance().getWeapon());
        spaceShipView.setPowering(true);
        spaceShipView.setCurrentWeaponAnimation(true);
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //background
        g.drawImage(backgroundL1.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL2.getCurrentFrame(), 0, 0, null);

        //gem count image
        g.drawImage(gems.getCurrentFrame(),52,80,null);

        //page buttons
        if(SceneManager.getInstance().isInGame()){
            MenuUtility.drawShopButton(back,backIcon,Settings.width-150,40,40,40,Color.WHITE,g);
            this.add(back);
        }
        MenuUtility.drawShopButton(mainMenu,menuIcon,Settings.width-80,40,40,40,Color.WHITE,g);
        this.add(mainMenu);

        //draw ship and components
        g.drawImage(spaceShipView.getSpaceship().draw(),(Settings.width/2)-175,50,null);

        g.setFont(font);
        g.setColor(Color.RED);
        if(!currentWeaponUnlocked){
            g.drawImage(locker,Settings.width/2-225,20,null);
            g.drawString(Language.allStrings.get(17) + ": "+gameConstraints.getUnlockWeaponsScore(spaceShipView.getCurrentWeapon()-1),Settings.width/2-180,50);
        }

        if(!currentEngineUnlocked){
            g.drawImage(locker,Settings.width/2-225,380,null);
            g.drawString(Language.allStrings.get(18) + ": "+gameConstraints.getUnlockEnginesScore(spaceShipView.getCurrentEngine()-1),Settings.width/2-180,410);
        }

        //separator
        g.setColor(Color.WHITE);
        g.drawLine((Settings.width/2)-350,(Settings.height/2)+70,(Settings.width/2)+330,(Settings.height/2)+70);

        //powerup bars
        MenuUtility.drawShopPwUpBar(this,pwUpButtons[0],gemsColor,powerUpColor,PowerUpImages[0],locker,plusIcon,gems.getCurrentFrame(),150, Settings.height-220,font,Language.allStrings.get(19),gameConstraints.getLvlMaxSpeed(),g);
        MenuUtility.drawShopPwUpBar(this,pwUpButtons[1],gemsColor,powerUpColor,PowerUpImages[1],locker,plusIcon,gems.getCurrentFrame(),150, Settings.height-100,font,Language.allStrings.get(20),gameConstraints.getLvlBulletSpeed(),g);
        MenuUtility.drawShopPwUpBar(this,pwUpButtons[2],gemsColor,powerUpColor,PowerUpImages[2],locker,plusIcon,gems.getCurrentFrame(),Settings.width-685, Settings.height-220,font,Language.allStrings.get(21),gameConstraints.getLvlBulletDamage(),g);
        MenuUtility.drawShopPwUpBar(this,pwUpButtons[3],gemsColor,powerUpColor,PowerUpImages[3],locker,plusIcon,gems.getCurrentFrame(),Settings.width-685, Settings.height-100,font,Language.allStrings.get(22),gameConstraints.getLvlBulletRatio(),g);

        //purchasable shield and boost
        MenuUtility.drawPurchasableShopPwUp(this,pwUpButtons[4],gemsColor,shield.getCurrentFrame(),gems.getCurrentFrame(),locker,gameConstraints.getShieldCost(),gameConstraints.getShopShield(),Settings.width-200,Settings.height-290,70,70,font,g);
        MenuUtility.drawPurchasableShopPwUp(this,pwUpButtons[5],gemsColor,boost.getCurrentFrame(),gems.getCurrentFrame(),locker,gameConstraints.getBoostCost(), gameConstraints.getShopBoost(),Settings.width-200,Settings.height-140,70,70,font,g);

        //update animations
        backgroundL1.update();
        backgroundL2.update();
        gems.update();
        shield.update();
        boost.update();
    }

    public void update() {super.repaint();}

    public void setLayered(boolean layered){
        this.layered = layered;
    }
}