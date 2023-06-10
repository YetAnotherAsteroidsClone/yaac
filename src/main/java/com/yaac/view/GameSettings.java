package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.GameConstraints;
import com.yaac.model.SaveFileManager;
import com.yaac.view.Utility.ObjectAnimation;
import com.yaac.view.Utility.Sound;

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
    JButton[] leftButtons = new JButton[3];
    // 0 = leftRes, 1 = leftSound, 2 = leftMusic
    JButton[] rightButtons = new JButton[3];
    // 0 = rightRes, 1 = rightSound, 2 = rightMusic
    private final JButton backButton;
    private final int settingsX = GameConstraints.WORLDWIDTH / 20;
    private final int settingsY = GameConstraints.WORLDHEIGHT / 20;
    private final int buttonsSize = 30;
    private boolean layered = false;

    public GameSettings() throws IOException, FontFormatException {
        this.setLayout(null);
        if (!layered)
            createBG(bg, GameConstraints.WORLDWIDTH, GameConstraints.WORLDHEIGHT);

        font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(35f);
        resolution = createLabel("RISOLUZIONE DELLO SCHERMO", settingsX * 3, settingsY * 5+5, 350, 16, font, Color.WHITE);
        music = createLabel("MUSICA", settingsX * 3, settingsY * 10+5, 350, 16, font, Color.WHITE);
        sound = createLabel("EFFETTI SONORI", settingsX * 3, settingsY * 15+5, 350, 16, font, Color.WHITE);

        backIcon = getImageIcon("/MenuSprite/BackButton.png",buttonsSize, buttonsSize);
        leftArrowIcon = getImageIcon("/MenuSprite/leftArrow.png", buttonsSize, buttonsSize);
        rightArrowIcon = getImageIcon("/MenuSprite/rightArrow.png", buttonsSize, buttonsSize);
        leftArrowIconClicked = getImageIcon("/MenuSprite/leftArrowClicked.png", buttonsSize, buttonsSize);
        rightArrowIconClicked = getImageIcon("/MenuSprite/rightArrowClicked.png", buttonsSize, buttonsSize);

        backButton = new JButton();
        for (int i = 0; i < leftButtons.length; i++) {
            leftButtons[i] = new JButton();
            rightButtons[i] = new JButton();
        }

        for (int i = 0; i < leftButtons.length; i++) {
            drawJButton(leftButtons[i], leftArrowIcon, settingsX * 10, settingsY * (5 + (i * 5)), buttonsSize, buttonsSize, leftArrowIconClicked);
            drawJButton(rightButtons[i], rightArrowIcon, settingsX * 14, settingsY * (5 + (i * 5)), buttonsSize, buttonsSize, rightArrowIconClicked);
        }

        for (int i = 0; i < leftButtons.length; i++) {
            this.add(leftButtons[i]);
            this.add(rightButtons[i]);
        }


        // Se le impostazioni si trovano in un layer allora siamo certi che sono state caricate dal menu di pausa
        backButton.addActionListener(e -> {if(layered) SceneManager.getInstance().unloadSettings(); else SceneManager.getInstance().loadMainMenu();});

        int resTextMid = leftButtons[0].getX() + buttonsSize + settingsX;

        res = getResolution();
        selectableResolution = createLabel(res, resTextMid, resolution.getY(), 350, 16, font, Color.WHITE);

        rightButtons[0].addActionListener(e -> {changeResolution(); selectableResolution.setText(getResolution());});
        leftButtons[0].addActionListener(rightButtons[0].getActionListeners()[0]);

        rightButtons[1].addActionListener(e -> {
            SoundEngine.getInstance().setVolume(SaveFileManager.getInstance().getVolume() + 5f);
            SaveFileManager.getInstance().setVolume(SaveFileManager.getInstance().getVolume());
        });

        leftButtons[1].addActionListener(e -> {
            SoundEngine.getInstance().setVolume(SaveFileManager.getInstance().getVolume() - 5f);
            SaveFileManager.getInstance().setVolume(SaveFileManager.getInstance().getVolume());
        });

        rightButtons[2].addActionListener(e -> {
            SaveFileManager.getInstance().setVolume(SaveFileManager.getInstance().getVolume() + 5f);
            SaveFileManager.getInstance().setVolume(SaveFileManager.getInstance().getVolume());
        });

        leftButtons[2].addActionListener(e -> {
            SaveFileManager.getInstance().setVolume(SaveFileManager.getInstance().getVolume() - 5f);
            SaveFileManager.getInstance().setVolume(SaveFileManager.getInstance().getVolume());
        });


        this.add(resolution);
        this.add(music);
        this.add(sound);
        this.add(backButton);
        this.add(selectableResolution);

        setBackground(new Color(0,0,0,0));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);
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
