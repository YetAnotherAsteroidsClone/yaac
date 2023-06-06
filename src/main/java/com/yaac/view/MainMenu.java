package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.GameConstraints;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static com.yaac.view.Utility.MenuUtility.*;

/**
 * Classe che gestisce la schermata del menu principale.
 */
public class MainMenu extends JPanel {
    ObjectAnimation[] bg =  new ObjectAnimation[3];
    Font font;
    JLabel gameLogoLabel, highScore;
    ImageIcon gameLogoIcon;
    String commandsText, highScoreText;
    JTextArea commands, highScoreValue;

    private final int windowWidth = GameConstraints.WORLDWIDTH;
    private final int windowHeight = GameConstraints.WORLDHEIGHT;
    private final int windowX = GameConstraints.WORLDWIDTH / 15;
    private final int windowY = GameConstraints.WORLDHEIGHT / 15;
    private final int widthCenter = GameConstraints.WORLDWIDTH / 2;
    private final int heightCenter = GameConstraints.WORLDHEIGHT / 2;
    private final JButton[] buttons = new JButton[5];
    //[0]playButton, [1]shopButton, [2]settingsButton, [3]creditsButton, [4]exitButton;

    /**
     * Costruttore del menu principale
     */
    public MainMenu() {
        //Elimina il layout di default
        this.setLayout(null);

        // Caricamento e scaling dello sfondo
        createBG(bg, windowWidth, windowHeight);

        // Caricamento del font
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(35f);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }

        int buttonXPos = widthCenter - 100;
        int buttonWidth = 200;
        int buttonHeight = 50;
        int firstButtonPos = heightCenter - 80; // Posizione del primo bottone
        //Caricamento dei bottoni e aggiunta dei listener
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
            b.addActionListener(e -> b.setBackground(Color.YELLOW));
            this.add(b);
        }

        //Caricamento del titolo e dei testi
        gameLogoIcon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("MenuSprite/GameLogo.png")));
        gameLogoLabel = new JLabel(gameLogoIcon);
        gameLogoLabel.setBounds(widthCenter - 300, heightCenter - 300, 600, 200);

        commandsText = """
                COMANDI
                A -> RUOTA A SINISTRA
                D -> RUOTA A DESTRA
                W -> ACCELERA
                BARRA SPAZIATRICE -> SPARA
                ESC -> PAUSA
                """;
        commands = createTextArea(commandsText, 15, windowHeight - 200, 328, 190, font, Color.YELLOW);
        highScore = createLabel("HIGH SCORE", windowX*13, (windowY *13)+30, 200, 30, font, Color.YELLOW);
        highScoreText = String.valueOf(GameConstraints.getInstance().getHighScore());
        highScoreValue = createTextArea(highScoreText, windowX*13, (windowY *13)+60, 200, 30, font, Color.YELLOW);

        //Aggiunta del titolo e dei testi al pannello
        this.add(gameLogoLabel);
        this.add(commands);
        this.add(highScore);
        this.add(highScoreValue);
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
