package com.yaac.view;

import javax.swing.*;
import java.awt.*;

import com.yaac.model.Game;
import com.yaac.model.Language;

import static com.yaac.view.Utility.MenuUtility.*;
import static com.yaac.Settings.*;

@SuppressWarnings("unused")
public class GameOver extends JPanel {
    final Font font;
    final JLabel gameOverLabel;

    public GameOver() {
        this.setLayout(null);
        font = loadFont(FONT_SIZE);
        int widthCenter = width / 2;
        int heightCenter = height / 2;

        // [0] restartButton, [1] menuButton, [2] shopButton, [3] exitButton;
        JButton[] buttons = new JButton[4];

        Font mainMenuFont = font.deriveFont(25f);
        if(language == Language.languageList.JAP) {
            mainMenuFont = font.deriveFont(20f);
        }

        buttons[0] = createButton(Language.allStrings.get(29), widthCenter - 100, heightCenter - 80, 200, 50, font);
        buttons[1] = createButton(Language.allStrings.get(30), widthCenter - 100, heightCenter, 200, 50, mainMenuFont);
        buttons[2] = createButton(Language.allStrings.get(1), widthCenter - 100, heightCenter + 80, 200, 50, font);
        buttons[3] = createButton(Language.allStrings.get(4), widthCenter - 100, heightCenter + 160, 200, 50, font);

        buttons[0].addActionListener(e -> {
            Game.reset();
            SceneManager.getInstance().unloadGameOver();});
        buttons[1].addActionListener(e -> {
            Game.reset();
            SceneManager.getInstance().loadMainMenu();});
        buttons[2].addActionListener(e -> SceneManager.getInstance().loadShop(true));
        buttons[3].addActionListener(e -> {
            Game.reset();
            System.exit(0);});

        gameOverLabel = new JLabel(Language.allStrings.get(28) + "!", SwingConstants.CENTER);
        gameOverLabel.setFont(font.deriveFont(100f));
        gameOverLabel.setBounds(0, heightCenter - 300, width, 200);
        gameOverLabel.setForeground(Color.RED);
        this.add(gameOverLabel);

        for (JButton b : buttons)
            this.add(b);

        setBackground(new Color(0,0,0,0));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBox(g);
    }

    public void update(){
        this.repaint();
    }

}
