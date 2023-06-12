package com.yaac.view;

import javax.swing.*;
import java.awt.*;
import com.yaac.Settings;

import static com.yaac.view.Utility.MenuUtility.*;

public class GameOver extends JPanel {
    Font font;
    JLabel gameOverLabel;
    private final int widthCenter = Settings.width / 2;
    private final int heightCenter = Settings.height / 2;
    private final JButton[] buttons = new JButton[4];
    // [0] restartButton, [1] menuButton, [2] shopButton, [3] exitButton;
    public GameOver() {
        this.setLayout(null);
        font = loadFont(35f);
    buttons[0] = createButton("RIPROVA", widthCenter - 100, heightCenter - 80, 200, 50, font);
    buttons[1] = createButton("MENU", widthCenter - 100, heightCenter, 200, 50, font);
    buttons[2] = createButton("NEGOZIO", widthCenter - 100, heightCenter + 80, 200, 50, font);
    buttons[3] = createButton("ESCI", widthCenter - 100, heightCenter + 160, 200, 50, font);

    buttons[0].addActionListener(e -> SceneManager.getInstance().loadGame());
    buttons[1].addActionListener(e -> SceneManager.getInstance().loadMainMenu());
    buttons[2].addActionListener(e -> SceneManager.getInstance().loadShop());
    buttons[3].addActionListener(e -> System.exit(0));

    gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
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