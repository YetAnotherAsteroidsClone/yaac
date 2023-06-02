package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.controller.GameController;
import com.yaac.model.GameConstraints;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.ObjectAnimation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Credits extends JPanel {
    ObjectAnimation[] bg =  new ObjectAnimation[3];
    ImageIcon exitIcon;
    Font font;
    GameController gameController;
    private int windowWidth = GameConstraints.WORLDWIDTH;
    private int windowHeight = GameConstraints.WORLDHEIGHT;
    public Credits() {
        this.setLayout(null);

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(35f);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }

        MenuUtility.createBG(bg, windowWidth, windowHeight);
        exitIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("MenuSprite/EscButton.png")));
        int buttonsSize = 40;
        exitIcon = ImageUtility.getImageIcon("/MenuSprite/EscButton.png", buttonsSize, buttonsSize);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Disegno e update dello sfondo
        g.setColor(Color.WHITE);
        g.drawImage(bg[0].getCurrentFrame(), 0, 0, null);
        g.drawImage(bg[1].getCurrentFrame(), 0, 0, null);
        g.drawImage(bg[2].getCurrentFrame(), 0, 0, null);
        bg[0].update();
        bg[1].update();
        bg[2].update();
        g.drawImage(exitIcon.getImage(), 25, windowHeight - 60, null);
        g.setFont(font);
        g.drawString("Premi ESC per tornare al menu", 80, windowHeight - 30);
    }

    public void update(){
        this.repaint();
    }

}
