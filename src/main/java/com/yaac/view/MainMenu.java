package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * Classe che gestisce la schermata del menu principale.
 */
public class MainMenu extends JPanel {
    ObjectAnimation backgroundL1, backgroundL2, backgroundL3;
    Font font;
    JLabel title;
    Icon icon;
    JTextArea commands, highScore;
    private final int widthOffset = Settings.width/2;
    private final int heightOffset = Settings.height/2;
    private final JButton[] buttons = new JButton[5];
    //[0]playButton, [1]shopButton, [2]settingsButton, [3]creditsButton, [4]exitButton;

    /**
     * Costruttore del menu principale.
     * @throws IOException eccezione da lettura file
     * @throws FontFormatException eccezione di formato del font
     */
    public MainMenu() throws IOException, FontFormatException {
        //Elimina il layout di default
        this.setLayout(null);

        // Caricamento e scala dello sfondo
        backgroundL1 = new ObjectAnimation("/Background/BackgroundL1.png", 640, 360);
        backgroundL2 = new ObjectAnimation("/Background/MainBackgroundL2.png", 640, 360);
        backgroundL3 = new ObjectAnimation("/Background/MainBackgroundL3.png", 640, 360);
        backgroundL1.scaleImage(Settings.width, Settings.height);
        backgroundL2.scaleImage(Settings.width, Settings.height);
        backgroundL3.scaleImage(Settings.width, Settings.height);

        // Caricamento del font
        font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(35f);

        //Caricamento dei bottoni e aggiunta dei listener
        buttons[0] = MenuUtility.createButton("GIOCA", widthOffset - 100, heightOffset - 80, 200, 50, font);
        buttons[1] = MenuUtility.createButton("NEGOZIO", widthOffset - 100, heightOffset, 200, 50, font);
        buttons[2] = MenuUtility.createButton("IMPOSTAZIONI", widthOffset - 100, heightOffset + 80, 200, 50, font);
        buttons[3] = MenuUtility.createButton("CREDITI", widthOffset - 100, heightOffset + 160, 200, 50, font);
        buttons[4] = MenuUtility.createButton("ESCI", widthOffset - 100, heightOffset + 240, 200, 50, font);
        buttons[0].addActionListener(e -> SceneManager.getInstance().loadGame());
        buttons[1].addActionListener(e -> SceneManager.getInstance().loadShop());
        buttons[2].addActionListener(e -> SceneManager.getInstance().loadSettings());
        buttons[3].addActionListener(e -> SceneManager.getInstance().loadCredits());
        buttons[4].addActionListener(e -> System.exit(0));

        //Aggiunta dei bottoni al pannello
        for (JButton b : buttons)
            this.add(b);

        //Caricamento del titolo e dei testi
        icon = new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("MenuSprite/Title.png")));
        title = new JLabel(icon);
        title.setBounds(widthOffset - 300, heightOffset - 300, 600, 200);
        String commandsText = """
                COMANDI
                A -> RUOTA A SINISTRA
                D -> RUOTA A DESTRA
                W -> ACCELERA
                BARRA SPAZIATRICE -> SPARA
                ESC -> PAUSA
                """;
        commands = MenuUtility.createTextArea(commandsText, 15, Settings.height - 200, 328, 190, font, Color.YELLOW);
        String highScoreText = "HIGHSCORE";
        highScore = MenuUtility.createTextArea(highScoreText, Settings.width - 200, Settings.height - 170, 200, 160, font, Color.YELLOW);

        //Aggiunta del titolo e dei testi al pannello
        this.add(title);
        this.add(commands);
        this.add(highScore);
    }

    /**
     * Metodo che gestisce il disegno del menu principale.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Disegno e update dello sfondo
        g.drawImage(backgroundL1.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL2.getCurrentFrame(), 0, 0, null);
        g.drawImage(backgroundL3.getCurrentFrame(), 0, 0, null);
        backgroundL1.update();
        backgroundL2.update();
        backgroundL3.update();
    }

    /**
     * Metodo che aggiorna il menu principale.
     */
    public void update(){
        this.repaint();
    }
}
