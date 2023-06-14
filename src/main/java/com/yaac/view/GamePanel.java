package com.yaac.view;
import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.Game;
import com.yaac.model.GameConstraints;
import com.yaac.model.Language;
import com.yaac.model.SaveFileManager;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import com.yaac.Loop;

/**
 * Classe che gestisce il pannello di gioco.
 */
public class GamePanel extends JPanel{
    Loop loop;
    private final SpaceShipView spaceShipView;
    private final ObjectAnimation backgroundL1;
    private final ObjectAnimation backgroundL2;
    private final ObjectAnimation backgroundL3;
    private final Font font;
    private BufferedImage life;
    private final BufferedImage asteroidsImage;
    private BufferedImage esc;
    private final ArrayList<ObjectAnimation> bulletsAnimation;
    private final ObjectAnimation deadAsteroidsAnimation;
    private final ObjectAnimation bulletExplosionAnimation;
    private final ArrayList<ObjectAnimation> gemsAnimation;
    private ObjectAnimation gemCounter;
    private ObjectAnimation shieldPwUp, boostPwUp;

    public GamePanel(){
        super();

        // Caricamento del background e eventualmente scala lo sfondo
        backgroundL1 = new ObjectAnimation("/Background/BackgroundL1.png", 640, 360);
        backgroundL2 = new ObjectAnimation("/Background/GameBackgroundL2.png", 640, 360);
        backgroundL3 = new ObjectAnimation("/Background/GameBackgroundL3.png", 640, 360);
        backgroundL1.scaleImage(GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);
        backgroundL2.scaleImage(GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);
        backgroundL3.scaleImage(GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);

        // Caricamento della vita e del pulsante "esc"
        life = ImageUtility.loadImage("/GameSprite/Body1.png");
        life = ImageUtility.scaleImage(life,40,40);
        esc = ImageUtility.loadImage("/MenuSprite/EscButton.png");
        esc = ImageUtility.scaleImage(esc,40,40);

        // Caricamento del font
        try {font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getResourceAsStream("/Font.ttf"))).deriveFont(45f);}
        catch (FontFormatException | IOException e) {throw new RuntimeException(e);}

        // Caricamento delle animazioni dei proiettili
        bulletsAnimation = new ArrayList<>();
        bulletsAnimation.add(new ObjectAnimation("/GameSprite/BulletBaseCannon.png"));
        bulletsAnimation.add(new ObjectAnimation("/GameSprite/BulletBigCannon.png"));
        bulletsAnimation.add(new ObjectAnimation("/GameSprite/BulletRocket.png"));
        bulletsAnimation.add(new ObjectAnimation("/GameSprite/BulletZapper.png"));

        // Caricamento delle animazioni delle gemme e scala dei frame
        gemsAnimation = new ArrayList<>();
        gemsAnimation.add(new ObjectAnimation("/GameSprite/GemL1.png"));
        gemsAnimation.add(new ObjectAnimation("/GameSprite/GemL2.png"));
        gemsAnimation.add(new ObjectAnimation("/GameSprite/GemL3.png"));
        for (ObjectAnimation gemAnimation : gemsAnimation) {
            gemAnimation.scaleImage(32, 32);
            gemCounter = new ObjectAnimation("/GameSprite/GemL1.png");
            gemCounter.scaleImage(45,45);
        }

        //Caricamento delle animazioni dei PwUp disponibili
        shieldPwUp = new ObjectAnimation("/GameSprite/ShopShield.png");
        shieldPwUp.scaleImage(50,50);
        boostPwUp = new ObjectAnimation("/GameSprite/ShopBoost.png");
        boostPwUp.scaleImage(50,50);


        // Caricamento delle animazioni degli asteroidi
        asteroidsImage = ImageUtility.loadImage("/GameSprite/Asteroid.png");
        deadAsteroidsAnimation = new ObjectAnimation("/GameSprite/Asteroid-Explode.png");

        // Caricamento dell'animazione dell'esplosione dei proiettili
        bulletExplosionAnimation = new ObjectAnimation("/GameSprite/BulletExplosionAnimation.png");
        bulletExplosionAnimation.scaleImage(32, 32);

        // Caricamento della nave
        spaceShipView = new SpaceShipView(Settings.shipSize, Settings.shipSize);
        spaceShipView.setCurrentWeapon(SaveFileManager.getInstance().getWeapon());
        spaceShipView.setCurrentEngine(SaveFileManager.getInstance().getEngine());
        spaceShipView.setBody(GameConstraints.lives - SaveFileManager.getInstance().getLives());
        Game.getInstance().addOnDeathListener(() -> {
            spaceShipView.setBoost(false);
            spaceShipView.setExplosion(true);
            spaceShipView.nextBody();
            SoundEngine.getInstance().playExplosion();});
        Game.getInstance().addGameOverListener(() -> {
            spaceShipView.setExplosion(true);
            SoundEngine.getInstance().playExplosion();
            SaveFileManager.getInstance().saveData();
            SaveFileManager.getInstance().resetCurrent();
            SaveFileManager.getInstance().resetLives();
            GameConstraints.getInstance().setLife(GameConstraints.lives);
            loop.stop();
            SceneManager.getInstance().loadGameOver();});
        Game.getInstance().addOnShieldStatusChangedListener(() -> spaceShipView.setShield(Game.getInstance().isShielded()));
        Game.getInstance().addOnSpeedBoostStatusChangedListener(() -> spaceShipView.setBoost(Game.getInstance().isBoosted()));
        Game.getInstance().setBulletType(spaceShipView.getCurrentWeapon());

        Game.getInstance().setScore(SaveFileManager.getInstance().getCurrentScore());
    }

    /**
     * Gestisce il disegno del pannello di gioco.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Disegna lo sfondo
        backgroundL1.update();
        backgroundL2.update();
        backgroundL3.update();
        g.drawImage(backgroundL1.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL2.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL3.getCurrentFrame(), 0, 0, null);

        // Disegna la nave
        g.drawImage(ImageUtility.rotateImage((BufferedImage) spaceShipView.getSpaceship().draw(), Game.getInstance().getSpaceShip().getRotation()), (int) (Game.getInstance().getSpaceShip().getX() - Game.getInstance().getSpaceShip().getRadius()), (int) (Game.getInstance().getSpaceShip().getY() - Game.getInstance().getSpaceShip().getRadius()), null);

        // Disegna i proiettili
        for(int i = 0; i < Game.getInstance().getBullets().size(); i++) {
            ObjectAnimation currentBulletTypeAnimation = bulletsAnimation.get(Game.getInstance().getBullets().get(i).getType());
            Image currentBulletFrame = currentBulletTypeAnimation.getImage((int) Game.getInstance().getBullets().get(i).getTick() % currentBulletTypeAnimation.size());
            g.drawImage(ImageUtility.rotateImage(ImageUtility.ImageToBuffered(currentBulletFrame), Game.getInstance().getBullets().get(i).getRotation()), (int) Game.getInstance().getBullets().get(i).getX() - 16, (int) Game.getInstance().getBullets().get(i).getY() - 16, null);
        }


        // Disegna gli asteroidi
        for(int i = 0; i < Game.getInstance().getAsteroids().size(); i++) {
            double asteroidSize = Game.getInstance().getAsteroids().get(i).getRadius();
            BufferedImage asteroid = ImageUtility.scaleImage(asteroidsImage, (int) (asteroidSize * 5), (int) (asteroidSize * 5));
            asteroid = ImageUtility.rotateImage(asteroid, Game.getInstance().getAsteroids().get(i).getRotation());
            g.drawImage(asteroid, (int) Game.getInstance().getAsteroids().get(i).getX() - (int)(asteroidSize * 2.5), (int) Game.getInstance().getAsteroids().get(i).getY() - (int) (asteroidSize * 2.5), null);
        }

        // Disegna l'esplosione dei proiettili
        for(int i = 0; i < Game.getInstance().getDestroyedBullets().size(); i++){
            double bulletSize = Game.getInstance().getDestroyedBullets().get(i).getRadius();
            Image bulletExplosionFrameUnscaled = bulletExplosionAnimation.getImage((int) ((Game.getInstance().getDestroyedBullets().get(i).getTick() + 1) / 2) % bulletExplosionAnimation.size());
            BufferedImage bulletExplosion = ImageUtility.scaleImage(ImageUtility.ImageToBuffered(bulletExplosionFrameUnscaled) , (int) bulletSize, (int) bulletSize);
            g.drawImage(bulletExplosion, (int) (Game.getInstance().getDestroyedBullets().get(i).getX() - bulletSize * 0.8), (int) (Game.getInstance().getDestroyedBullets().get(i).getY() - bulletSize * 0.8), null);
        }

        // Disegna l'esplosione degli asteroidi
        for(int i = 0; i < Game.getInstance().getDestroyedAsteroids().size(); i++) {
            double asteroidSize = Game.getInstance().getDestroyedAsteroids().get(i).getRadius();
            Image asteroidCurrentFrame = deadAsteroidsAnimation.getImage((int) ((Game.getInstance().getDestroyedAsteroids().get(i).getTick() + 1) / 2) % deadAsteroidsAnimation.size());
            asteroidCurrentFrame = ImageUtility.scaleImage(ImageUtility.ImageToBuffered(asteroidCurrentFrame), (int) (asteroidSize * 5), (int) (asteroidSize * 5));
            asteroidCurrentFrame = ImageUtility.rotateImage((BufferedImage) asteroidCurrentFrame, Game.getInstance().getDestroyedAsteroids().get(i).getRotation());
            g.drawImage(asteroidCurrentFrame, (int) Game.getInstance().getDestroyedAsteroids().get(i).getX() - (int)(asteroidSize * 2.5), (int) Game.getInstance().getDestroyedAsteroids().get(i).getY() - (int) (asteroidSize * 2.5), null);
        }

        // Disegna le gemme
        for(int i = 0; i < Game.getInstance().getGems().size(); i++) {
            if(Game.getInstance().getGems().get(i).getTick() > 192 && Game.getInstance().getGems().get(i).getTick() % 6 > 3)
                continue;
            ObjectAnimation currentGemTypeAnimation = gemsAnimation.get(Game.getInstance().getGems().get(i).getType() - 1);
            Image currentGemFrame = currentGemTypeAnimation.getImage((int) (Game.getInstance().getGems().get(i).getTick() / 2 )% currentGemTypeAnimation.size());
            g.drawImage(currentGemFrame, (int) (Game.getInstance().getGems().get(i).getX() - Game.getInstance().getGems().get(i).getRadius()), (int) (Game.getInstance().getGems().get(i).getY() - Game.getInstance().getGems().get(i).getRadius()), null);
        }

        // OVERLAY: punteggio, vite, gemme, istruzioni di pausa, stage, PwUp disponibili
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(Language.allStrings.get(16) + ": " + Game.getInstance().getScore(),40,50);
        for(int i=0; i < Game.getInstance().getLives();i++){
            g.drawImage(life,40*(i+1),70,null);
        }
        g.drawImage(gemCounter.getCurrentFrame(),GameConstraints.WORLDWIDTH-150,20,null);
        gemCounter.update();
        g.setColor(Color.WHITE);
        g.drawString("x" + Game.getInstance().getGemCount(),GameConstraints.WORLDWIDTH-105,55);
        g.drawImage(esc,25,Settings.height-60,null);
        g.drawString(": " + Language.allStrings.get(23),70,Settings.height-30);
        if(Game.getInstance().getStagePause()){
            g.setColor(Color.YELLOW);
            g.drawString(Language.allStrings.get(24) + " " + Game.getInstance().getStage(),(Settings.width/2)-50,(Settings.height/2)-10);
        }
        g.setColor(Color.WHITE);
        if(GameConstraints.getInstance().getShopShield()){
            g.drawString("S: ",Settings.width-100,Settings.height-80);
            g.drawImage(shieldPwUp.getCurrentFrame(),Settings.width-70,Settings.height-112,null);
            shieldPwUp.update();
        }

        if(GameConstraints.getInstance().getShopBoost()){
            g.drawString("B: ",Settings.width-100,Settings.height-30);
            g.drawImage(boostPwUp.getCurrentFrame(),Settings.width-70,Settings.height-62,null);
            boostPwUp.update();
        }
    }

    /**
     * Aggiorna il pannello di gioco.
     */
    public void update(){
        super.repaint();
    }

    //Restituisce la maschera di creazione della nave
    public SpaceShipView getSpaceShipView() {
        return spaceShipView;
    }

    public void addLoop(Loop loop){
        this.loop = loop;
    }
}
