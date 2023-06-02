package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.GameConstraints;
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
    ObjectAnimation[] bg =  new ObjectAnimation[3];
    Font font;
    JLabel title, highScore;
    Icon icon;
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
     * Costruttore del menu principale.
     * @throws IOException eccezione da lettura file
     * @throws FontFormatException eccezione di formato del font
     */
    public MainMenu() {
        //Elimina il layout di default
        this.setLayout(null);

        // Caricamento e scaling dello sfondo

        MenuUtility.createBG(bg, windowWidth, windowHeight);

        // Caricamento del font
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(35f);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }

        int buttonXPos = widthCenter - 100;
        int buttonWidth = 200;
        int buttonHeight = 50;
        int firstButtonPos = heightCenter - 80;
        //Caricamento dei bottoni e aggiunta dei listener
        buttons[0] = MenuUtility.createButton("GIOCA", buttonXPos, firstButtonPos, buttonWidth, buttonHeight, font);
        buttons[1] = MenuUtility.createButton("NEGOZIO", buttonXPos, firstButtonPos + 80, buttonWidth, buttonHeight, font);
        buttons[2] = MenuUtility.createButton("IMPOSTAZIONI", buttonXPos, firstButtonPos + 160, 200, 50, font);
        buttons[3] = MenuUtility.createButton("CREDITI", buttonXPos, firstButtonPos + 240, 200, 50, font);
        buttons[4] = MenuUtility.createButton("ESCI", buttonXPos, firstButtonPos + 320, 200, 50, font);
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
        title.setBounds(widthCenter - 300, heightCenter - 300, 600, 200);

        commandsText = """
                COMANDI
                A -> RUOTA A SINISTRA
                D -> RUOTA A DESTRA
                W -> ACCELERA
                BARRA SPAZIATRICE -> SPARA
                ESC -> PAUSA
                """;
        commands = MenuUtility.createTextArea(commandsText, 15, windowHeight - 200, 328, 190, font, Color.YELLOW);
        highScore = MenuUtility.createLabel("HIGH SCORE", windowX*13, (windowY *13)+30, 200, 30, font, Color.YELLOW);
        highScoreText = String.valueOf(GameConstraints.getInstance().getHighScore());
        highScoreValue = MenuUtility.createTextArea(highScoreText, windowX*13, (windowY *13)+60, 200, 30, font, Color.YELLOW);

        //Aggiunta del titolo e dei testi al pannello
        this.add(title);
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
        g.drawImage(bg[0].getCurrentFrame(), 0, 0, null);
        g.drawImage(bg[1].getCurrentFrame(), 0, 0, null);
        g.drawImage(bg[2].getCurrentFrame(), 0, 0, null);
        bg[0].update();
        bg[1].update();
        bg[2].update();
    }

    /**
     * Metodo che aggiorna il menu principale.
     */
    public void update(){
        this.repaint();
    }
}
