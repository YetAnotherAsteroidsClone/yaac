package com.yaac.view;

import com.yaac.Settings;
import com.yaac.model.Game;
import com.yaac.model.SaveFileManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.yaac.view.Utility.MenuUtility.*;

public class PauseMenu extends JPanel {
    Font font;
    JLabel pauseLabel;

    private final int widthCenter = Settings.width / 2;
    private final int heightCenter = Settings.height / 2;
    private final JButton[] buttons = new JButton[4];
    // [0] resumeButton, [1] saveButton, [2] settingsButton, [3] exitButton;

    public PauseMenu() {
        this.setLayout(null);

        font = loadFont(35f);

        buttons[0] = createButton("RIPRENDI", widthCenter - 100, heightCenter - 80, 200, 50, font);
        buttons[1] = createButton("SALVA", widthCenter - 100, heightCenter, 200, 50, font);
        buttons[2] = createButton("IMPOSTAZIONI", widthCenter - 100, heightCenter + 80, 200, 50, font);
        buttons[3] = createButton("ABBANDONA", widthCenter - 100, heightCenter + 160, 200, 50, font);

        buttons[0].addActionListener(e -> SceneManager.getInstance().unloadPauseMenu());
        buttons[1].addActionListener(e -> SaveFileManager.getInstance().saveData());
        buttons[2].addActionListener(e -> SceneManager.getInstance().loadSettings(true));
        //È necessario resettare il gioco e salvare i dati quando si esce dal menu di pausa
        buttons[3].addActionListener(e -> {SaveFileManager.getInstance().saveData(); SceneManager.getInstance().loadMainMenu(); Game.reset();});

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ESCAPE)
                    SceneManager.getInstance().unloadPauseMenu();
            }
        });

        pauseLabel = new JLabel("PAUSA", SwingConstants.CENTER);
        pauseLabel.setFont(font.deriveFont(100f));
        pauseLabel.setBounds(widthCenter - 300, heightCenter - 300, 600, 200);
        pauseLabel.setForeground(Color.YELLOW);
        this.add(pauseLabel);

        for (JButton b : buttons)
            this.add(b);
        //Trasparenza
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
