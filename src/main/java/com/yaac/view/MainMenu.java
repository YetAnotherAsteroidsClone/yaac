package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.controller.MainMenuController;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.ObjectAnimation;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainMenu extends JPanel {
    ObjectAnimation backgroundL1, backgroundL2, backgroundL3;
    Font fontButtons;
    JLabel title;
    Icon icon;
    private final int widthOffset = Settings.width/2;
    private final int heightOffset = Settings.height/2;
    private final JButton[] buttons = new JButton[5];
    //[0]playButton, [1]shopButton, [2]settingsButton, [3]creditsButton, [4]exitButton;

    public MainMenu() throws IOException, FontFormatException {
        this.setLayout(null);
        backgroundL1 = new ObjectAnimation("/Background/BackgroundL1.png", 640, 360);
        backgroundL2 = new ObjectAnimation("/Background/MainBackgroundL2.png", 640, 360);
        backgroundL3 = new ObjectAnimation("/Background/MainBackgroundL3.png", 640, 360);

        backgroundL1.scaleImage(Settings.width, Settings.height);
        backgroundL2.scaleImage(Settings.width, Settings.height);
        backgroundL3.scaleImage(Settings.width, Settings.height);

        fontButtons = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(35f);

        buttons[0] = MenuUtility.createButton("GIOCA", widthOffset - 100, heightOffset - 80, 200, 50, fontButtons);
        buttons[1] = MenuUtility.createButton("NEGOZIO", widthOffset - 100, heightOffset, 200, 50, fontButtons);
        buttons[2] = MenuUtility.createButton("IMPOSTAZIONI", widthOffset - 100, heightOffset + 80, 200, 50, fontButtons);
        buttons[3] = MenuUtility.createButton("CREDITI", widthOffset - 100, heightOffset + 160, 200, 50, fontButtons);
        buttons[4] = MenuUtility.createButton("ESCI", widthOffset - 100, heightOffset + 240, 200, 50, fontButtons);

        buttons[0].addActionListener(e -> SceneManager.getInstance().loadGame());
        buttons[1].addActionListener(e -> SceneManager.getInstance().loadShop());
        buttons[2].addActionListener(e -> SceneManager.getInstance().loadSettings());
        buttons[3].addActionListener(e -> SceneManager.getInstance().loadCredits());
        buttons[4].addActionListener(e -> System.exit(0));

        for (JButton b : buttons) {
            this.add(b);
        }

        icon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("MenuSprite/Title.png")));
        title = new JLabel(icon);
        title.setBounds(widthOffset - 300, heightOffset - 300, 600, 200);


        //title.setVisible(true);

        String commandsText = """
                COMANDI
                A -> RUOTA A SINISTRA
                D -> RUOTA A DESTRA
                W -> ACCELERA
                BARRA SPAZIATRICE -> SPARA
                """;

        //title = createLabel(titleText, fontTitle, Color.YELLOW, SwingConstants.CENTER);
        // TODO: commands


        this.add(title);
        //this.add(commands);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundL1.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL2.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL3.getCurrentFrame(), 0, 0, null);
        backgroundL1.update();
        backgroundL2.update();
        backgroundL3.update();
    }

    public void update(){
        this.repaint();
    }
}
