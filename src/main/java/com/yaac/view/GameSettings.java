package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.GameConstraints;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.ObjectAnimation;
//import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class GameSettings extends JPanel {

    private final ObjectAnimation[] bg = new ObjectAnimation[3];
    Font font;
    JLabel resolution, sound, music;
    ImageIcon backIcon;
    private final JButton backButton;

    private final int windowX = GameConstraints.WORLDWIDTH /15;

    private final int windowY = GameConstraints.WORLDHEIGHT/15;
    private final int windowWidth = GameConstraints.WORLDWIDTH;
    private final int windowHeight = GameConstraints.WORLDHEIGHT;

    private boolean layered = false;

    public GameSettings() throws IOException, FontFormatException {
        this.setLayout(null);

        MenuUtility.createBG(bg, windowWidth, windowHeight);

        font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(35f);
        resolution = MenuUtility.createLabel("RISOLUZIONE DELLO SCHERMO", windowX *3, windowY *3, 600, 200, font, Color.WHITE);
        music = MenuUtility.createLabel("MUSICA", windowX * 3, windowY * 6, 600, 200, font, Color.WHITE);
        sound = MenuUtility.createLabel("EFFETTI SONORI", windowX * 3, windowY * 9, 600, 200, font, Color.WHITE);

        backIcon = ImageUtility.getImageIcon("/MenuSprite/BackButton.png",38,38);

        backButton = new JButton();
        // Se le impostazioni si trovano in un layer allora siamo certi che sono state caricate dal menu di pausa
        backButton.addActionListener(e -> {if(layered) SceneManager.getInstance().unloadSettings(); else SceneManager.getInstance().loadMainMenu();});

        this.add(resolution);
        this.add(music);
        this.add(sound);
        this.add(backButton);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg[0].getCurrentFrame(), 0, 0, null);
        g.drawImage(bg[1].getCurrentFrame(), 0, 0, null);
        g.drawImage(bg[2].getCurrentFrame(), 0, 0, null);
        bg[0].update();
        bg[1].update();
        bg[2].update();
        g.setColor(new Color(0,0,0,150));
        int buttonsSize = 30;
        g.fillRoundRect(windowX, windowY, windowWidth - (windowX *2), windowHeight - (windowY *2), 20, 20);
        MenuUtility.drawShopButton(backButton, backIcon, windowX, windowY, buttonsSize, buttonsSize, Color.WHITE, g);
    }
    public void update(){
        this.repaint();
    }

    public void setLayered(boolean layered) {
        this.layered = layered;
    }
}
