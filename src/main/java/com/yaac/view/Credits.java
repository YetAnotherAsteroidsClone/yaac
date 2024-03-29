package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.Language;
import com.yaac.model.SaveFileManager;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.logging.Level;

import static com.yaac.view.Utility.MenuUtility.*;
import static com.yaac.Settings.*;

@SuppressWarnings("unused")
public class Credits extends JPanel {
    final ObjectAnimation[] bg =  new ObjectAnimation[3];
    final ObjectAnimation flame;
    int hitX,hitY = 0;
    ObjectAnimation earth;
    final ImageIcon exitIcon;
    final Image gameLogo;
    final Font font;
    private boolean eeUnlocked = false;
    private boolean exploded = false;
    private int explosionTicks = 0;
    private int hitTicks = 0;
    private boolean hit = false;
    private final int earthBtnX;
    private final int earthBtnY;
    private final int wh = 350;

    public Credits() {
        this.setLayout(null);
        font = loadFont(FONT_SIZE);

        earthBtnX = width/2-(wh/2);
        earthBtnY = height/2-(wh/2);

        if(superSecretValue)
            initEasterEgg();

        createBG(bg, width, height);

        JLabel stringVersion = new JLabel(VERSION,SwingConstants.RIGHT);
        stringVersion.setFont(loadFont(FONT_SIZE));
        stringVersion.setForeground(Color.YELLOW);
        stringVersion.setBounds(0, height-50, width, 40);
        stringVersion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        this.add(stringVersion);

        flame = new ObjectAnimation("/GameSprite/BulletExplosionAnimation.png");
        earth = new ObjectAnimation("/MenuSprite/Earth.png", 96,96);
        earth.scaleImage(700, 700);
        int buttonsSize = 40;
        exitIcon = ImageUtility.getImageIcon("/MenuSprite/EscButton.png", buttonsSize, buttonsSize);

        gameLogo = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("MenuSprite/GameLogo.png"))).getImage();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Disegno e update dello sfondo
        drawAndUpdateBG(g, bg);

        if(!exploded) {
            g.drawImage(earth.getCurrentFrame(), width / 2 - 350, height / 2 - 350, null);
            earth.update();
        }
        if(eeUnlocked){
            explosionTicks++;
            if(explosionTicks == 20)
                exploded = true;
        }

        if(hit) {
            hitTicks++;
            g.drawImage(flame.getCurrentFrame(), earthBtnX+hitX-100, earthBtnY+hitY-100, null);
            flame.update();
            if(hitTicks == 5) {
                hit = false;
                hitTicks = 0;
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(font.deriveFont(35f));
        g.drawImage(exitIcon.getImage(), 25, height - 60, null);
        g.drawString(Language.allStrings.get(12), 80, height - 30);
        if(Settings.language == Language.languageList.JAP)
            g.setFont(font.deriveFont(40f));
        else
            g.setFont(font.deriveFont(70f));
        g.setColor(Color.YELLOW);
        g.drawString("Emanuele Galardo", 20, height/2 - 100);
        g.drawString("Giovanni Palermo", 20, height/2 + 100);
        g.drawString("Davide Petitto", width - 370, height/2 - 100);
        g.drawString("Francesco Zuco", width - 370, height/2 + 100);
    }

    public void update(){
        this.repaint();
    }

    private void initEasterEgg(){
        // Easter egg button
        JButton earthButton = new JButton();
        earthButton.setBounds(earthBtnX, earthBtnY, wh,wh);
        earthButton.setOpaque(false);
        earthButton.setContentAreaFilled(false);
        earthButton.setBorderPainted(false);

        // Easter egg label
        Font eeLabelFont = font.deriveFont(50f);
        if(language == Language.languageList.JAP)
            eeLabelFont = font.deriveFont(25f);
        JLabel easterEggCompleted = MenuUtility.createLabel(Language.allStrings.get(10), 0, 10,width,25,eeLabelFont, Color.YELLOW);
        easterEggCompleted.setVisible(false);
        easterEggCompleted.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(easterEggCompleted);
        earthButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                hitX = e.getX();
                hitY = e.getY();
            }
        });
        earthButton.addActionListener(actionEvent -> {
            superSecretCounter++;
            if(superSecretCounter == 5) {
                SaveFileManager.getInstance().cheatCode();
                earth = new ObjectAnimation("/GameSprite/BulletExplosionAnimation.png");
                earth.scaleImage(700, 700);
                eeUnlocked = true;
                easterEggCompleted.setVisible(true);
                SoundEngine.getInstance().playExplosion();
                LOGGER.log(Level.INFO, "Easter egg unlocked congratulations!");
                superSecretCounter = 0;
                this.grabFocus();
                this.remove(earthButton);
                this.revalidate();
            }
            else {
                hit = true;
                this.grabFocus();
                LOGGER.log(Level.INFO, "Click! " + (5 - superSecretCounter) + " clicks remaining");
            }
        });
        this.add(earthButton);
    }
}
