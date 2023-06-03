package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.Game;
import com.yaac.model.GameConstraints;
import com.yaac.model.SaveFileManager;
import com.yaac.view.Utility.MenuUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PauseMenu extends JPanel {
    Font font;
    JLabel title;
    private final int windowX = GameConstraints.WORLDWIDTH/15;
    private final int windowY = GameConstraints.WORLDHEIGHT/15;
    private final JButton[] buttons = new JButton[4];
    // [0] resumeButton, [1] saveButton, [2] settingsButton, [3] exitButton;

    public PauseMenu() {
        this.setLayout(null);

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(35f);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }

        int widthCenter = GameConstraints.WORLDWIDTH / 2;/*SceneManager.getInstance().unloadScene(pauseMenu);*/
        int heightCenter = GameConstraints.WORLDHEIGHT / 2;
        buttons[0] = MenuUtility.createButton("RIPRENDI", widthCenter - 100, heightCenter - 80, 200, 50, font);
        buttons[1] = MenuUtility.createButton("SALVA", widthCenter - 100, heightCenter, 200, 50, font);
        buttons[2] = MenuUtility.createButton("IMPOSTAZIONI", widthCenter - 100, heightCenter + 80, 200, 50, font);
        buttons[3] = MenuUtility.createButton("ABBANDONA", widthCenter - 100, heightCenter + 160, 200, 50, font);

        buttons[0].addActionListener(e -> SceneManager.getInstance().unloadPauseMenu());
        buttons[1].addActionListener(e -> SaveFileManager.getInstance().saveData());
        buttons[2].addActionListener(e -> SceneManager.getInstance().loadSettings(true));
        //È necessario resettare il gioco e salvare i dati quando si esce dal menu di pausa
        buttons[3].addActionListener(e -> {SceneManager.getInstance().loadMainMenu(); SaveFileManager.getInstance().saveData(); Game.reset();});

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ESCAPE)
                    SceneManager.getInstance().unloadPauseMenu();
            }
        });

        title = new JLabel("PAUSA", SwingConstants.CENTER);
        title.setFont(font.deriveFont(100f));
        title.setBounds(widthCenter - 300, heightCenter - 300, 600, 200);
        title.setForeground(Color.YELLOW);
        this.add(title);

        for (JButton b : buttons)
            this.add(b);
        //Trasparenza
        setBackground(new Color(0,0,0,0));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0,0,0,150));
        g.fillRoundRect(windowX, windowY, Settings.width-(windowX *2),Settings.height-(windowY *2), 20, 20);
    }

    public void update(){
        this.repaint();
    }
}
