package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static com.yaac.view.Utility.MenuUtility.*;
import static com.yaac.view.Utility.ImageUtility.*;

/*
 * Francesco
 */

public class GameSettings extends JPanel {
    Font font;
    JLabel resolution, sound, music, selectableResolution;
    String res;
    ImageIcon backIcon, leftArrowIcon, rightArrowIcon, leftArrowIconClicked, rightArrowIconClicked;
    ObjectAnimation[] bg =  new ObjectAnimation[3];
    private final JButton backButton, leftArrowButton, rightArrowButton;
    private final int windowWidth = Settings.width;
    private final int windowHeight = Settings.height;
    private final int settingsX = Settings.width / 20;
    private final int settingsY = Settings.height / 20;
    private final int buttonsSize = 30;
    private boolean layered = false;

    public GameSettings() throws IOException, FontFormatException {
        this.setLayout(null);
        if (!layered)
            createBG(bg, windowWidth, windowHeight);


        font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(35f);
        resolution = createLabel("RISOLUZIONE DELLO SCHERMO", settingsX * 3, settingsY * 5, 350, 16, font, Color.WHITE);
        music = createLabel("MUSICA", settingsX * 3, settingsY * 10, 350, 16, font, Color.WHITE);
        sound = createLabel("EFFETTI SONORI", settingsX * 3, settingsY * 15, 350, 16, font, Color.WHITE);

        backIcon = getImageIcon("/MenuSprite/BackButton.png",buttonsSize, buttonsSize);
        leftArrowIcon = getImageIcon("/MenuSprite/leftArrow.png", buttonsSize, buttonsSize);
        rightArrowIcon = getImageIcon("/MenuSprite/rightArrow.png", buttonsSize, buttonsSize);
        leftArrowIconClicked = getImageIcon("/MenuSprite/leftArrowClicked.png", buttonsSize, buttonsSize);
        rightArrowIconClicked = getImageIcon("/MenuSprite/rightArrowClicked.png", buttonsSize, buttonsSize);

        backButton = new JButton();
        rightArrowButton = new JButton();
        leftArrowButton = new JButton();

        // Se le impostazioni si trovano in un layer allora siamo certi che sono state caricate dal menu di pausa
        backButton.addActionListener(e -> {if(layered) SceneManager.getInstance().unloadSettings(); else SceneManager.getInstance().loadMainMenu();});

        drawJButton(leftArrowButton, leftArrowIcon, settingsX * 10 , resolution.getY()-resolution.getHeight()/2, buttonsSize, buttonsSize, leftArrowIconClicked);
        drawJButton(rightArrowButton, rightArrowIcon, settingsX * 14, resolution.getY()-resolution.getHeight()/2, buttonsSize, buttonsSize, rightArrowIconClicked);

        int resTextMid = leftArrowButton.getX() + buttonsSize + settingsX;

        res = getResolution();
        selectableResolution = createLabel(res, resTextMid, resolution.getY(), 350, 16, font, Color.WHITE);

        rightArrowButton.addActionListener(e -> changeResolution());
        leftArrowButton.addActionListener(rightArrowButton.getActionListeners()[0]);

        selectableResolution = createLabel(res, resTextMid, resolution.getY(), 350, 16, font, Color.WHITE);


        this.add(resolution);
        this.add(music);
        this.add(sound);
        this.add(backButton);
        this.add(rightArrowButton);
        this.add(leftArrowButton);
        this.add(selectableResolution);

        setBackground(new Color(0,0,0,0));


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);
        if (!layered)
            drawAndUpdateBG(g, bg);
        drawBox(g);
        drawShopButton(backButton, backIcon, settingsX, settingsY, buttonsSize, buttonsSize, Color.WHITE, g);
    }

    public void update(){
        this.repaint();
    }

    public void setLayered(boolean layered) {
        this.layered = layered;
    }
}
