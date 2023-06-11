package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.GameConstraints;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static com.yaac.view.Utility.MenuUtility.*;
import static com.yaac.view.Utility.ImageUtility.*;

/**
 * Classe che gestisce la schermata del menu principale.
 */
public class MainMenu extends JPanel {
    ObjectAnimation[] bg =  new ObjectAnimation[3]; // Immagini di background
    Font font;
    JLabel gameLogoLabel; // Logo del gioco, punteggio massimo
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
        this.setLayout(null);                       //Elimina il layout di default
        createBG(bg, Settings.width, Settings.height);    // Caricamento e scaling dello sfondo
        font = loadFont(35f);                  // Caricamento del font

        int buttonXPos = widthCenter - 100;
        int buttonWidth = 200;
        int buttonHeight = 50;
        int firstButtonPos = heightCenter - 80; // Posizione del primo bottone

        //Caricamento dei bottoni e aggiunta dei relativi listener
        buttons[0] = createButton("GIOCA", buttonXPos, firstButtonPos, buttonWidth, buttonHeight, font);
        buttons[1] = createButton("NEGOZIO", buttonXPos, firstButtonPos + 80, buttonWidth, buttonHeight, font);
        buttons[2] = createButton("IMPOSTAZIONI", buttonXPos, firstButtonPos + 160, 200, 50, font);
        buttons[3] = createButton("CREDITI", buttonXPos, firstButtonPos + 240, 200, 50, font);
        buttons[4] = createButton("ESCI", buttonXPos, firstButtonPos + 320, 200, 50, font);
        buttons[0].addActionListener(e -> SceneManager.getInstance().loadGame());
        buttons[1].addActionListener(e -> SceneManager.getInstance().loadShop());
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

        commandsText = """
                COMANDI
                A -> RUOTA A SINISTRA
                D -> RUOTA A DESTRA
                W -> ACCELERA
                BARRA SPAZIATRICE -> SPARA
                ESC -> PAUSA
                BACKSPACE -> ESCI DALLA PARTITA
                """;
        commands = createTextArea(commandsText, 15, Settings.height - 240, 390, 250, font, Color.YELLOW);
        //Aggiunta del titolo e dei testi al pannello
        this.add(gameLogoLabel);
        this.add(commands);
    }

    /**
     * Metodo che gestisce il disegno del menu principale.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Disegno e update dello sfondo
        drawAndUpdateBG(g, bg);
        g.setColor(Color.YELLOW);
        g.setFont(font);
        g.drawString("HIGH SCORE", Settings.width - 200, Settings.height - 50);
        g.drawString(String.valueOf(GameConstraints.getInstance().getHighScore()), Settings.width - 200, Settings.height - 20);
    }

    /**
     * Metodo che aggiorna il menu principale.
     */
    public void update(){
        this.repaint();
    }
}
