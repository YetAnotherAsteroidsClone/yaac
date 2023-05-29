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

public class GamePanel extends JPanel {

    Game game = Game.getInstance();

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
    private GameConstraints gameConstraints;
    //private Sound gameSound;

    public GamePanel(){
        backgroundL1 = new ObjectAnimation("/Background/BackgroundL1.png", 640, 360);
        backgroundL2 = new ObjectAnimation("/Background/GameBackgroundL2.png", 640, 360);
        backgroundL3 = new ObjectAnimation("/Background/GameBackgroundL3.png", 640, 360);
        backgroundL1.scaleImage(GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);
        backgroundL2.scaleImage(GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);
        backgroundL3.scaleImage(GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);

        gameConstraints = GameConstraints.getInstance();

        life = ImageUtility.loadImage("/GameSprite/Body1.png");
        life = ImageUtility.scaleImage(life,40,40);
        try {font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getResourceAsStream("/Font.ttf"))).deriveFont(45f);}
        catch (FontFormatException | IOException e) {throw new RuntimeException(e);}

        bulletsAnimation = new ArrayList<>();
        bulletsAnimation.add(new ObjectAnimation("/GameSprite/BulletBaseCannon.png"));
        bulletsAnimation.add(new ObjectAnimation("/GameSprite/BulletBigCannon.png"));
        bulletsAnimation.add(new ObjectAnimation("/GameSprite/BulletRocket.png"));
        bulletsAnimation.add(new ObjectAnimation("/GameSprite/BulletZapper.png"));

        asteroidsImage = ImageUtility.loadImage("/GameSprite/Asteroid.png");
        deadAsteroidsAnimation = new ObjectAnimation("/GameSprite/Asteroid-Explode.png");
        bulletExplosionAnimation = new ObjectAnimation("/GameSprite/BulletExplosionAnimation.png");
        bulletExplosionAnimation.scaleImage(32, 32);

        spaceShipView = new SpaceShipView(Settings.shipSize, Settings.shipSize);

        //gameSound = new Sound("Music.wav");
        //gameSound.loop();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        backgroundL1.update();
        backgroundL2.update();
        backgroundL3.update();
        g.drawImage(backgroundL1.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL2.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL3.getCurrentFrame(), 0, 0, null);
        g.drawImage(ImageUtility.rotateImage((BufferedImage) spaceShipView.getSpaceship().draw(), game.getSpaceShip().getRotation()), (int) (game.getSpaceShip().getX() - game.getSpaceShip().getRadius()), (int) (game.getSpaceShip().getY() - game.getSpaceShip().getRadius()), null);




        for(int i = 0; i < game.getAsteroids().size(); i++) {
            double asteroidSize = game.getAsteroids().get(i).getRadius();
            BufferedImage asteroid = ImageUtility.scaleImage(asteroidsImage, (int) (asteroidSize * 5), (int) (asteroidSize * 5));
            asteroid = ImageUtility.rotateImage(asteroid, game.getAsteroids().get(i).getRotation());
            g.drawImage(asteroid, (int) game.getAsteroids().get(i).getX() - (int)(asteroidSize * 2.5), (int) game.getAsteroids().get(i).getY() - (int) (asteroidSize * 2.5), null);
        }

        for(int i = 0; i < game.getDestroyedBullets().size(); i++){
            Image bulletExplosionFrame = bulletExplosionAnimation.getImage((int) ((game.getDestroyedBullets().get(i).getTick() + 1) / 2)% bulletExplosionAnimation.size());
            g.drawImage(bulletExplosionFrame, (int) game.getDestroyedBullets().get(i).getX() - 25, (int) game.getDestroyedBullets().get(i).getY() - 25, null);
        }

        for(int i = 0; i < game.getDestroyedAsteroids().size(); i++) {
            double asteroidSize = game.getDestroyedAsteroids().get(i).getRadius();
            Image asteroidCurrentFrame = deadAsteroidsAnimation.getImage((int) ((game.getDestroyedAsteroids().get(i).getTick() + 1) / 2) % deadAsteroidsAnimation.size());
            asteroidCurrentFrame = ImageUtility.scaleImage(ImageUtility.ImageToBuffered(asteroidCurrentFrame), (int) (asteroidSize * 5), (int) (asteroidSize * 5));
            asteroidCurrentFrame = ImageUtility.rotateImage((BufferedImage) asteroidCurrentFrame, game.getDestroyedAsteroids().get(i).getRotation());
            g.drawImage(asteroidCurrentFrame, (int) game.getDestroyedAsteroids().get(i).getX() - (int)(asteroidSize * 2.5), (int) game.getDestroyedAsteroids().get(i).getY() - (int) (asteroidSize * 2.5), null);
        }

        for(int i = 0; i < game.getBullets().size(); i++) {
            ObjectAnimation currentBulletTypeAnimation = bulletsAnimation.get(game.getBullets().get(i).getType());
            Image currentBulletFrame = currentBulletTypeAnimation.getImage((int) game.getBullets().get(i).getTick() % currentBulletTypeAnimation.size());
            g.drawImage(ImageUtility.rotateImage((BufferedImage) currentBulletFrame, game.getBullets().get(i).getRotation()), (int) game.getBullets().get(i).getX() - 16, (int) game.getBullets().get(i).getY() - 16, null);
        }

        g.setFont(font);
        g.drawString("SCORE: "+gameConstraints.getScore(),40,50);
        for(int i=0; i<gameConstraints.getLife();i++){
            g.drawImage((Image) life,40*(i+1),70,null);
        }

        tick++;
    }

    public void update(){
        super.repaint();
    }

}
