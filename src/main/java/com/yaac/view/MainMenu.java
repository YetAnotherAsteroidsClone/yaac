package com.yaac.view;

import com.yaac.Main;
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
    JLabel gameLogoLabel, highScore; // Logo del gioco, punteggio massimo
    ImageIcon gameLogoIcon; // Icona del logo del gioco
    ImageIcon[] flags = new ImageIcon[2]; // Icone delle bandiere
    String commandsText, highScoreText; // Testo dei comandi, testo del punteggio massimo
    JTextArea commands, highScoreValue; // Area di testo dei comandi, area di testo del punteggio massimo

    private final int windowWidth = GameConstraints.WORLDWIDTH;
    private final int windowHeight = GameConstraints.WORLDHEIGHT;
    private final int windowX = GameConstraints.WORLDWIDTH / 15;
    private final int windowY = GameConstraints.WORLDHEIGHT / 15;
    private final int widthCenter = GameConstraints.WORLDWIDTH / 2;
    private final int heightCenter = GameConstraints.WORLDHEIGHT / 2;
    private final JButton[] buttons = new JButton[5];
    //[0]playButton, [1]shopButton, [2]settingsButton, [3]creditsButton, [4]exitButton;

    private final JButton[] langButtons = new JButton[2];

    /**
     * Costruttore del menu principale
     */
    public MainMenu() {
        this.setLayout(null);                       //Elimina il layout di default
        createBG(bg, windowWidth, windowHeight);    // Caricamento e scaling dello sfondo
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
        commands = createTextArea(commandsText, 15, windowHeight - 240, 390, 250, font, Color.YELLOW);
        highScore = createLabel("HIGH SCORE", windowX*13, (windowY *13)+30, 200, 30, font, Color.YELLOW);
        highScoreText = String.valueOf(GameConstraints.getInstance().getHighScore());
        highScoreValue = createTextArea(highScoreText, windowX*13, (windowY *13)+60, 200, 30, font, Color.YELLOW);

        flags[0] = getImageIcon("/MenuSprite/ita.png", 60, 40);
        flags[1] = getImageIcon("/MenuSprite/eng.png", 60, 40);
        langButtons[0] = new JButton();
        langButtons[1] = new JButton();

        drawJButton(langButtons[0], flags[0], GameConstraints.WORLDWIDTH-125, 0, 60, 40);
        drawJButton(langButtons[1], flags[1], GameConstraints.WORLDWIDTH-60, 0, 60, 40);

        langButtons[0].addActionListener(e -> {
            // TODO
        });

        langButtons[1].addActionListener(e -> {
            // TODO
        });

        //Aggiunta del titolo e dei testi al pannello
        this.add(gameLogoLabel);
        this.add(commands);
        this.add(highScore);
        this.add(highScoreValue);
        this.add(langButtons[0]);
        this.add(langButtons[1]);
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
