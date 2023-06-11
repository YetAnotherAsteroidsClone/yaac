package com.yaac.view;

import com.yaac.Main;
import com.yaac.model.GameConstraints;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.yaac.view.Utility.MenuUtility.*;

public class Credits extends JPanel {
    ObjectAnimation[] bg =  new ObjectAnimation[3];
    ImageIcon exitIcon;
    Image gameLogo;
    JTextArea credits;
    Font font;
    String creditsText;
    private final int windowWidth = GameConstraints.WORLDWIDTH;
    private final int windowHeight = GameConstraints.WORLDHEIGHT;
    public Credits() {
        this.setLayout(null);
        font = loadFont(35f);

        createBG(bg, windowWidth, windowHeight);
        int buttonsSize = 40;
        exitIcon = ImageUtility.getImageIcon("/MenuSprite/EscButton.png", buttonsSize, buttonsSize);

        gameLogo = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("MenuSprite/GameLogo.png"))).getImage();
        creditsText = """
                Sviluppato da:
                Emanuele Galardo
                Giovanni Palermo
                Davide Petitto
                Francesco Zuco""";
        int textWidth = 445;
        int textHeight = 355;
        credits = createTextArea(creditsText, windowWidth/2 - textWidth/2, windowHeight /2 - textHeight/2, textWidth, textHeight, font.deriveFont(80f), Color.WHITE);
        credits.setRows(5);
        this.add(credits);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Disegno e update dello sfondo
        drawAndUpdateBG(g, bg);

        g.setColor(Color.WHITE);
        g.setFont(font.deriveFont(35f));
        g.drawImage(exitIcon.getImage(), 25, windowHeight - 60, null);
        g.drawImage(gameLogo, windowWidth/2 - gameLogo.getWidth(null)/2, windowHeight-100, null);
        g.drawString("Premi ESC per tornare al menu", 80, windowHeight - 30);
    }

    public void update(){
        this.repaint();
    }
}
