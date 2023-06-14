package com.yaac.view;

import com.yaac.Settings;
import com.yaac.model.Game;
import com.yaac.model.Language;
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
    private Language.languageList lang = Settings.language;

    public PauseMenu() {
        this.setLayout(null);

        font = loadFont(Settings.FONT_SIZE);

        buttons[0] = createButton(Language.allStrings.get(25), widthCenter - 100, heightCenter - 80, 200, 50, font);
        buttons[1] = createButton(Language.allStrings.get(26), widthCenter - 100, heightCenter, 200, 50, font);
        buttons[2] = createButton(Language.allStrings.get(2), widthCenter - 100, heightCenter + 80, 200, 50, font);
        buttons[3] = createButton(Language.allStrings.get(4), widthCenter - 100, heightCenter + 160, 200, 50, font);

        buttons[0].addActionListener(e -> SceneManager.getInstance().unloadPauseMenu());
        buttons[1].addActionListener(e -> SaveFileManager.getInstance().saveData());
        buttons[2].addActionListener(e -> SceneManager.getInstance().loadSettings(true));
        //È necessario resettare il gioco e salvare i dati quando si esce dal menu di pausa
        buttons[3].addActionListener(e -> {
            SceneManager.getInstance().loadMainMenu();
            Game.reset();});

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ESCAPE)
                    SceneManager.getInstance().unloadPauseMenu();
            }
        });

        generateLabel();
        this.add(pauseLabel);

        for (JButton b : buttons)
            this.add(b);
        //Trasparenza
        setBackground(new Color(0,0,0,0));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBox(g);
        if(lang != Settings.language)
            updateLanguage();
    }

    private void updateLanguage(){
        lang = Settings.language;
        this.remove(pauseLabel);
        //Se non viene ricreato il label avviene un bug che rende visibile i confini della label rendendone lo sfondo più scuro
        generateLabel();
        this.add(pauseLabel);
        buttons[0].setText(Language.allStrings.get(25));
        buttons[1].setText(Language.allStrings.get(26));
        buttons[2].setText(Language.allStrings.get(2));
        buttons[3].setText(Language.allStrings.get(4));
    }


    private void generateLabel(){
        pauseLabel = new JLabel(Language.allStrings.get(23), SwingConstants.CENTER);
        pauseLabel.setFont(font.deriveFont(100f));
        pauseLabel.setBounds(widthCenter - 300, heightCenter - 300, 600, 200);
        pauseLabel.setForeground(Color.YELLOW);
    }

    public void update(){
        this.repaint();
    }
}
