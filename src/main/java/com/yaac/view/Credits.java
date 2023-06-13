package com.yaac.view;

import com.yaac.Main;
import com.yaac.model.Language;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.yaac.view.Utility.MenuUtility.*;
import static com.yaac.Settings.*;

public class Credits extends JPanel {
    ObjectAnimation[] bg =  new ObjectAnimation[3];
    ObjectAnimation earth;
    ImageIcon exitIcon;
    Image gameLogo;
    Font font;
    public Credits() {
        this.setLayout(null);
        font = loadFont(35f);

        createBG(bg, width, height);

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

        g.drawImage(earth.getCurrentFrame(), width/2-350, height/2-350, null);
        earth.update();

        g.setColor(Color.WHITE);
        g.setFont(font.deriveFont(35f));
        g.drawImage(exitIcon.getImage(), 25, height - 60, null);
        g.drawString(Language.allStrings.get(12), 80, height - 30);
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
}
