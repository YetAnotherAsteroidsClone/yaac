package com.yaac.view;
import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.Game;
import com.yaac.model.GameConstraints;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe che gestisce il pannello di gioco.
 */
public class GamePanel extends JPanel {

    Game game;
    int tick = 0;

    private SpaceShipView spaceShipView;
    private ObjectAnimation backgroundL1;
    private ObjectAnimation backgroundL2;
    private ObjectAnimation backgroundL3;
    private Font font;
    private BufferedImage life;
    private ArrayList<ObjectAnimation> bulletsAnimation;
    private BufferedImage asteroidsImage;
    private ObjectAnimation deadAsteroidsAnimation;
    private ObjectAnimation bulletExplosionAnimation;
    private ArrayList<ObjectAnimation> gemsAnimation;

    public GamePanel(){
        super();
        game = Game.getInstance();

        // Caricamento del background e eventualmente scala lo sfondo
        backgroundL1 = new ObjectAnimation("/Background/BackgroundL1.png", 640, 360);
        backgroundL2 = new ObjectAnimation("/Background/GameBackgroundL2.png", 640, 360);
        backgroundL3 = new ObjectAnimation("/Background/GameBackgroundL3.png", 640, 360);
        backgroundL1.scaleImage(GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);
        backgroundL2.scaleImage(GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);
        backgroundL3.scaleImage(GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);

        // Caricamento della vita e scala l'immagine
        life = ImageUtility.loadImage("/GameSprite/Body1.png");
        life = ImageUtility.scaleImage(life,40,40);

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
        }

        // Caricamento delle animazioni degli asteroidi
        asteroidsImage = ImageUtility.loadImage("/GameSprite/Asteroid.png");
        deadAsteroidsAnimation = new ObjectAnimation("/GameSprite/Asteroid-Explode.png");

        // Caricamento dell'animazione dell'esplosione dei proiettili
        bulletExplosionAnimation = new ObjectAnimation("/GameSprite/BulletExplosionAnimation.png");
        bulletExplosionAnimation.scaleImage(32, 32);

        // Caricamento della nave
        spaceShipView = new SpaceShipView(Settings.shipSize, Settings.shipSize);
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

        switch (game.getLives()) {
            case 3 -> spaceShipView.setBody(1);
            case 2 -> spaceShipView.setBody(2);
            case 1 -> spaceShipView.setBody(3);
        }

        // Disegna la nave
        g.drawImage(ImageUtility.rotateImage((BufferedImage) spaceShipView.getSpaceship().draw(), game.getSpaceShip().getRotation()), (int) (game.getSpaceShip().getX() - game.getSpaceShip().getRadius()), (int) (game.getSpaceShip().getY() - game.getSpaceShip().getRadius()), null);

        // Disegna gli asteroidi
        for(int i = 0; i < game.getAsteroids().size(); i++) {
            double asteroidSize = game.getAsteroids().get(i).getRadius();
            BufferedImage asteroid = ImageUtility.scaleImage(asteroidsImage, (int) (asteroidSize * 5), (int) (asteroidSize * 5));
            asteroid = ImageUtility.rotateImage(asteroid, game.getAsteroids().get(i).getRotation());
            g.drawImage(asteroid, (int) game.getAsteroids().get(i).getX() - (int)(asteroidSize * 2.5), (int) game.getAsteroids().get(i).getY() - (int) (asteroidSize * 2.5), null);
        }

        // Disegna l'esplosione dei proiettili
        for(int i = 0; i < game.getDestroyedBullets().size(); i++){
            Image bulletExplosionFrame = bulletExplosionAnimation.getImage((int) ((game.getDestroyedBullets().get(i).getTick() + 1) / 2)% bulletExplosionAnimation.size());
            g.drawImage(bulletExplosionFrame, (int) game.getDestroyedBullets().get(i).getX() - 25, (int) game.getDestroyedBullets().get(i).getY() - 25, null);
        }

        // Disegna l'esplosione degli asteroidi
        for(int i = 0; i < game.getDestroyedAsteroids().size(); i++) {
            double asteroidSize = game.getDestroyedAsteroids().get(i).getRadius();
            Image asteroidCurrentFrame = deadAsteroidsAnimation.getImage((int) ((game.getDestroyedAsteroids().get(i).getTick() + 1) / 2) % deadAsteroidsAnimation.size());
            asteroidCurrentFrame = ImageUtility.scaleImage(ImageUtility.ImageToBuffered(asteroidCurrentFrame), (int) (asteroidSize * 5), (int) (asteroidSize * 5));
            asteroidCurrentFrame = ImageUtility.rotateImage((BufferedImage) asteroidCurrentFrame, game.getDestroyedAsteroids().get(i).getRotation());
            g.drawImage(asteroidCurrentFrame, (int) game.getDestroyedAsteroids().get(i).getX() - (int)(asteroidSize * 2.5), (int) game.getDestroyedAsteroids().get(i).getY() - (int) (asteroidSize * 2.5), null);
        }

        // Disegna i proiettili
        for(int i = 0; i < game.getBullets().size(); i++) {
            ObjectAnimation currentBulletTypeAnimation = bulletsAnimation.get(game.getBullets().get(i).getType());
            Image currentBulletFrame = currentBulletTypeAnimation.getImage((int) game.getBullets().get(i).getTick() % currentBulletTypeAnimation.size());
            g.drawImage(ImageUtility.rotateImage(ImageUtility.ImageToBuffered(currentBulletFrame), game.getBullets().get(i).getRotation()), (int) game.getBullets().get(i).getX() - 16, (int) game.getBullets().get(i).getY() - 16, null);
        }

        // Disegna le gemme
        for(int i = 0; i < game.getGems().size(); i++) {
            ObjectAnimation currentGemTypeAnimation = gemsAnimation.get(game.getGems().get(i).getType() - 1);
            Image currentGemFrame = currentGemTypeAnimation.getImage((int) (game.getGems().get(i).getTick() / 2 )% currentGemTypeAnimation.size());
            g.drawImage(currentGemFrame, (int) (game.getGems().get(i).getX() - game.getGems().get(i).getRadius()), (int) (game.getGems().get(i).getY() - game.getGems().get(i).getRadius()), null);
        }

        // Disegna il punteggio e le vite
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("SCORE: " + game.getScore(),40,50);
        for(int i=0; i < game.getLives();i++){
            g.drawImage((Image) life,40*(i+1),70,null);
        }
        g.setColor(Color.YELLOW);
        g.drawRect(0, 0, GameConstraints.WORLDWIDTH-1, GameConstraints.WORLDHEIGHT-1);
    }

    /**
     * Aggiorna il pannello di gioco.
     */
    public void update(){
        super.repaint();
        tick++;
    }

    //Restituisce la maschera di creazione della nave
    public SpaceShipView getSpaceShipView() {
        return spaceShipView;
    }
}
