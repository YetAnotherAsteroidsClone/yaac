package com.yaac.view;

import com.yaac.Settings;
import com.yaac.model.SaveFileManager;
import com.yaac.view.Utility.ObjectAnimation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.yaac.view.Utility.MenuUtility.*;
import static com.yaac.view.Utility.ImageUtility.*;

/*
 * Francesco
 */

public class GameSettings extends JPanel {
    Font font;
    JLabel language, sound, music;
    ImageIcon backIcon, leftArrowIcon, rightArrowIcon, leftArrowIconClicked, rightArrowIconClicked;
    ImageIcon[] flags = new ImageIcon[2]; // Icone delle bandiere
    // [0] = IT, [1] = EN
    ObjectAnimation[] bg =  new ObjectAnimation[3];
    JButton[] leftButtons = new JButton[3];
    // 0 = leftLang, 1 = leftSound, 2 = leftMusic
    JButton[] rightButtons = new JButton[3];
    // 0 = rightLang, 1 = rightSound, 2 = rightMusic
    private final JButton backButton;
    private final int settingsY = Settings.height / 20;
    private final int buttonsSize = 30;
    private final JButton[] langButtons = new JButton[2];
    private boolean layered = false;

    public GameSettings() throws IOException, FontFormatException {
        this.setLayout(null);
        if (!layered)
            createBG(bg, Settings.width, Settings.height);

        int textX = Settings.width - 1088;
        font = loadFont(35f);
        language = createLabel("LINGUA", textX, Settings.height - 535, 350, 16, font, Color.WHITE);
        music = createLabel("MUSICA", textX, Settings.height - 355, 350, 16, font, Color.WHITE);
        sound = createLabel("EFFETTI SONORI", textX, Settings.height - 175, 350, 16, font, Color.WHITE);

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

        int leftButtonX = Settings.width / 2;
        int rightButtonX = Settings.width - 300;
        drawJButton(leftButtons[0], leftArrowIcon, leftButtonX, language.getY()-5, buttonsSize, buttonsSize, leftArrowIconClicked);
        drawJButton(rightButtons[0], rightArrowIcon, rightButtonX, language.getY()-5, buttonsSize, buttonsSize, rightArrowIconClicked);
        drawJButton(leftButtons[1], leftArrowIcon, leftButtonX, music.getY()-5, buttonsSize, buttonsSize, leftArrowIconClicked);
        drawJButton(rightButtons[1], rightArrowIcon, rightButtonX, music.getY()-5, buttonsSize, buttonsSize, rightArrowIconClicked);
        drawJButton(leftButtons[2], leftArrowIcon, leftButtonX, sound.getY()-5, buttonsSize, buttonsSize, leftArrowIconClicked);
        drawJButton(rightButtons[2], rightArrowIcon, rightButtonX, sound.getY()-5, buttonsSize, buttonsSize, rightArrowIconClicked);

        for (int i = 0; i < leftButtons.length; i++) {
            this.add(leftButtons[i]);
            this.add(rightButtons[i]);
        }


        // Se le impostazioni si trovano in un layer allora siamo certi che sono state caricate dal menu di pausa
        backButton.addActionListener(e -> {if(layered) SceneManager.getInstance().unloadSettings(); else SceneManager.getInstance().loadMainMenu();});


        flags[0] = getImageIcon("/MenuSprite/ita.png", 60, 40);
        flags[1] = getImageIcon("/MenuSprite/eng.png", 60, 40);
        langButtons[0] = new JButton();
        langButtons[1] = new JButton();

        // TODO: getLanguage(), setLanguage()

        // TODO: CAMBIO LINGUA
        int flagX = 825-flags[0].getIconWidth()/2;
        leftButtons[0].addActionListener(e -> {/*setLanguage();*/drawJButton(langButtons[0], flags[0], flagX, language.getY()-10, 60, 40);});
        rightButtons[0].addActionListener(e -> {/*setLanguage();*/drawJButton(langButtons[0], flags[1], flagX, language.getY()-10, 60, 40);});


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

        langButtons[0].addActionListener(e -> {
            // TODO
        });

        langButtons[1].addActionListener(e -> {
            // TODO
        });

        this.add(language);
        this.add(music);
        this.add(sound);
        this.add(backButton);
        this.add(langButtons[0]);
        this.add(langButtons[1]);

        setBackground(new Color(0,0,0,0));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);
        drawAndUpdateBG(g, bg);
        drawBox(g);
        drawShopButton(backButton, backIcon, Settings.width-1216, Settings.height-684, buttonsSize, buttonsSize, Color.WHITE, g);
    }

    public void update(){
        this.repaint();
    }

    public void setLayered(boolean layered) {
        this.layered = layered;
    }
}
