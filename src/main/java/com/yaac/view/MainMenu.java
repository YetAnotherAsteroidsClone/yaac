package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.GameConstraints;
import com.yaac.model.Language;
import com.yaac.view.Utility.ObjectAnimation;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.yaac.view.Utility.MenuUtility.*;

/**
 * Classe che gestisce la schermata del menu principale.
 */
public class MainMenu extends JPanel {
    ObjectAnimation[] bg =  new ObjectAnimation[3]; // Immagini di background
    Font font;
    JLabel gameLogoLabel, highscore, numHighScore; // Logo del gioco, punteggio massimo
    ImageIcon gameLogoIcon; // Icona del logo del gioco
    String commandsText; // Testo dei comandi, testo del punteggio massimo
    JTextArea commands; // Area di testo dei comandi, area di testo del punteggio massimo

    private final int widthCenter = Settings.width / 2;
    private final int heightCenter = Settings.height / 2;
    private final JButton[] buttons = new JButton[5];
    //[0]playButton, [1]shopButton, [2]settingsButton, [3]creditsButton, [4]exitButton;

    /**
     * Costruttore del menu principale
     */
    public MainMenu() {
        this.setLayout(null);                               //Elimina il layout di default
        createBG(bg, Settings.width, Settings.height);      // Caricamento e scaling dello sfondo
        font = loadFont(Settings.FONT_SIZE);                           // Caricamento del font

        int buttonXPos = widthCenter - 100;
        int buttonWidth = 200;
        int buttonHeight = 50;
        int firstButtonPos = heightCenter - 80; // Posizione del primo bottone

        //Caricamento dei bottoni e aggiunta dei relativi listener
        buttons[0] = createButton(Language.allStrings.get(0), buttonXPos, firstButtonPos, buttonWidth, buttonHeight, font);
        buttons[1] = createButton(Language.allStrings.get(1), buttonXPos, firstButtonPos + 80, buttonWidth, buttonHeight, font);
        buttons[2] = createButton(Language.allStrings.get(2), buttonXPos, firstButtonPos + 160, 200, 50, font);
        buttons[3] = createButton(Language.allStrings.get(3), buttonXPos, firstButtonPos + 240, 200, 50, font);
        buttons[4] = createButton(Language.allStrings.get(4), buttonXPos, firstButtonPos + 320, 200, 50, font);
        buttons[0].addActionListener(e -> SceneManager.getInstance().loadGame());
        buttons[1].addActionListener(e -> SceneManager.getInstance().loadShop(false));
        buttons[2].addActionListener(e -> SceneManager.getInstance().loadSettings(false));
        buttons[3].addActionListener(e -> SceneManager.getInstance().loadCredits());
        buttons[4].addActionListener(e -> System.exit(0));

        //Aggiunta dei bottoni al pannello
        for (JButton b : buttons) {
            this.add(b);
        }

        //Caricamento del logo del gioco e dei testi
        gameLogoIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("MenuSprite/GameLogo.png")));
        gameLogoLabel = new JLabel(gameLogoIcon);

        int gameLogoWidth = gameLogoIcon.getIconWidth();
        int gameLogoHeight = gameLogoIcon.getIconHeight();

        gameLogoLabel.setBounds(widthCenter - gameLogoWidth/2, heightCenter - 280, gameLogoWidth, gameLogoHeight);

        commandsText = Language.allStrings.get(5)+"\n"+ "A -> " + Language.allStrings.get(6)+"\n"+ "D -> " + Language.allStrings.get(7)+"\n"+ "W -> " + Language.allStrings.get(8) +"\n"+ Language.allStrings.get(9) + " -> " + Language.allStrings.get(27) +"\n" + "ESC -> " + Language.allStrings.get(23);
        commands = createTextArea(commandsText, 15, Settings.height - 200, 390, 250, font.deriveFont(34f), Color.YELLOW);

        highscore = createLabel(Language.allStrings.get(11),Settings.width - 250, Settings.height - 70,300,20, font, Color.YELLOW);
        numHighScore = createLabel(""+GameConstraints.getInstance().getHighScore(),Settings.width - 250,Settings.height - 40,300,20, font, Color.WHITE);

        //Aggiunta del titolo e dei testi al pannello
        this.add(gameLogoLabel);
        this.add(highscore);
        this.add(numHighScore);
        this.add(commands);
    }

    /**
     * Metodo che gestisce il disegno del menu principale.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Disegno e update dello sfondo
        drawAndUpdateBG(g, bg);
    }

    /**
     * Metodo che aggiorna il menu principale.
     */
    public void update(){
        this.repaint();
    }
}
