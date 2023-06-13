package com.yaac.view;

import javax.swing.*;
import java.awt.*;
import com.yaac.model.Language;

import static com.yaac.view.Utility.MenuUtility.*;
import static com.yaac.Settings.*;

public class GameOver extends JPanel {
    Font font;
    JLabel gameOverLabel;

    public GameOver() {
        this.setLayout(null);
        font = loadFont(35f);
        int widthCenter = width / 2;
        int heightCenter = height / 2;

        // [0] restartButton, [1] menuButton, [2] shopButton, [3] exitButton;
        JButton[] buttons = new JButton[4];

        buttons[0] = createButton(Language.allStrings.get(29), widthCenter - 100, heightCenter - 80, 200, 50, font);
        buttons[1] = createButton(Language.allStrings.get(30), widthCenter - 100, heightCenter, 200, 50, font);
        buttons[2] = createButton(Language.allStrings.get(1), widthCenter - 100, heightCenter + 80, 200, 50, font);
        buttons[3] = createButton(Language.allStrings.get(28), widthCenter - 100, heightCenter + 160, 200, 50, font);

        buttons[0].addActionListener(e -> SceneManager.getInstance().unloadGameOver());
        buttons[1].addActionListener(e -> SceneManager.getInstance().loadMainMenu());
        buttons[2].addActionListener(e -> SceneManager.getInstance().loadShop());
        buttons[3].addActionListener(e -> System.exit(0));

        gameOverLabel = new JLabel(Language.allStrings.get(31) + "!", SwingConstants.CENTER);
        gameOverLabel.setFont(font.deriveFont(100f));
        gameOverLabel.setBounds(widthCenter - 300, heightCenter - 300, 600, 200);
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
