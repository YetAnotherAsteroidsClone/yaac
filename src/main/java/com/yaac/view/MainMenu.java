package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.controller.MainMenuController;
import com.yaac.view.Utility.ImageUtility;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MainMenu extends JPanel {
    BufferedImage background;
    Font fontButtons, fontTitle;
    private int widthOffset = Settings.width/2;
    private int heightOffset = Settings.height/2;
    private JButton[] buttons = new JButton[5];
    //[0]playButton, [1]shopButton, [2]settingsButton, [3]creditsButton, [4]exitButton;



    public MainMenu() throws IOException, FontFormatException {
        this.addMouseListener(new MainMenuController(this));
        background = ImageUtility.loadImage("/Background/StaticBackground.png");
        background = ImageUtility.scaleImage(background, Settings.width, Settings.height);
        fontTitle = new Font("Font", Font.PLAIN, 100);
        /*TODO: titolo non centrato con questo font
        fontTitle = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(100f);
        */
        fontButtons = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(35f);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.setColor(Color.WHITE);
        g.setFont(fontTitle);
        g.drawString("YAAC", widthOffset - 130, heightOffset - 230);
        buttons[0] = createButton("GIOCA", widthOffset - 100, heightOffset - 100, 200, 50);
        buttons[0].addActionListener(e -> SceneManager.getInstance().loadGame());
        buttons[1] = createButton("NEGOZIO", widthOffset - 100, heightOffset, 200, 50);
        buttons[1].addActionListener(e -> SceneManager.getInstance().loadShop());
        buttons[2] = createButton("IMPOSTAZIONI", widthOffset - 100, heightOffset + 100, 200, 50);
        buttons[2].addActionListener(e -> SceneManager.getInstance().loadSettings());
        buttons[3] = createButton("CREDITI", widthOffset - 100, heightOffset + 200, 200, 50);
        buttons[3].addActionListener(e -> SceneManager.getInstance().loadCredits());
        buttons[4] = createButton("ESCI", widthOffset - 100, heightOffset + 300, 200, 50);
        buttons[4].addActionListener(e -> System.exit(0));

        for (JButton button : buttons)
            this.add(button);

        this.setVisible(true);
    }

    /*
       * Crea un oggetto JButton con le caratteristiche passate come parametri e ritornarlo.
    */
    public JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(fontButtons);
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.addMouseListener(new MainMenuController(this));
        return button;
    }
}
