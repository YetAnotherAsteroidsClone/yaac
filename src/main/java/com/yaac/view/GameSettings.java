package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.controller.GameSettingsController;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class GameSettings extends JPanel {
    ObjectAnimation backgroundL1, backgroundL2, backgroundL3;
    Font font;
    JLabel resolution, sound, music;
    ImageIcon menuIcon;
    private final int widthOffset = Settings.width/2;
    private final int heightOffset = Settings.height/2;
    private GameSettingsController controller;

    private JButton backToMenuButton, applyButton, resetButton ;

    private int windowWidth = Settings.width/15;

    private int windowHeight = Settings.height/15;

    public GameSettings() throws IOException, FontFormatException {
        this.setLayout(null);
        backgroundL1 = new ObjectAnimation("/Background/BackgroundL1.png", 640, 360);
        backgroundL2 = new ObjectAnimation("/Background/MainBackgroundL2.png", 640, 360);
        backgroundL3 = new ObjectAnimation("/Background/MainBackgroundL3.png", 640, 360);

        backgroundL1.scaleImage(Settings.width, Settings.height);
        backgroundL2.scaleImage(Settings.width, Settings.height);
        backgroundL3.scaleImage(Settings.width, Settings.height);

        font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(35f);
        resolution = MenuUtility.createLabel("RISOLUZIONE DELLO SCHERMO", windowWidth*3, windowHeight*3, 600, 200, font, Color.WHITE);
        music = MenuUtility.createLabel("MUSICA", windowWidth * 3, windowHeight * 6, 600, 200, font, Color.WHITE);
        sound = MenuUtility.createLabel("EFFETTI SONORI", windowWidth * 3, windowHeight * 9, 600, 200, font, Color.WHITE);

        menuIcon = MenuUtility.getImageIcon("/MenuSprite/HomeButton.png",40,40);

        backToMenuButton = new JButton();
        backToMenuButton.addActionListener(e -> {
            try {
                SceneManager.getInstance().loadMainMenu();
            } catch (IOException | FontFormatException ex) {
                throw new RuntimeException(ex);
            }
        });

        this.add(resolution);
        this.add(music);
        this.add(sound);
        this.add(backToMenuButton);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundL1.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL2.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL3.getCurrentFrame(), 0, 0, null);
        backgroundL1.update();
        backgroundL2.update();
        backgroundL3.update();
        g.setColor(new Color(0,0,0,150));
        g.fillRoundRect(windowWidth, windowHeight, Settings.width-(windowWidth*2),Settings.height-(windowHeight*2), 20, 20);
        MenuUtility.drawShopButton(backToMenuButton, menuIcon, windowWidth,windowHeight,40,40,Color.WHITE,g);
        backToMenuButton.setBorder(null);

    }
    public void update(){
        this.repaint();
    }
}
