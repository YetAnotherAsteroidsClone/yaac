package com.yaac.view;

import com.yaac.Settings;
import com.yaac.model.Language;
import com.yaac.model.SaveFileManager;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.ObjectAnimation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static com.yaac.view.Utility.MenuUtility.*;
import static com.yaac.view.Utility.ImageUtility.*;
import static com.yaac.Settings.*;

public class GameSettings extends JPanel {
    Font font;
    JLabel language, sound, music;
    ImageIcon[] settingsIcons = new ImageIcon[6];
    // [0] backIcon [1] leftArrowIcon [2] rightArrowIcon [3] leftArrowIconClicked
    // [4] rightArrowIconClicked, [5] backIconClicked
    BufferedImage[] flags = new BufferedImage[Language.languageList.values().length]; // immagini delle bandiere
    ObjectAnimation[] bg =  new ObjectAnimation[3];
    JButton[] leftButtons = new JButton[3];
    // 0 = leftLang, 1 = leftSound, 2 = leftMusic
    JButton[] rightButtons = new JButton[3];
    // 0 = rightLang, 1 = rightSound, 2 = rightMusic
    private final JButton backButton;
    private final int settingsY = height / 20;
    private final int buttonsSize = 30;
    private final JButton[] langButtons = new JButton[2];
    private boolean layered = false;

    public GameSettings() {
        this.setLayout(null);
        if (!layered)
            createBG(bg, width, height);


        int textX = width - 1088;
        font = loadFont(35f);
        // creazione delle label
        language = createLabel(Language.allStrings.get(13), textX, height - 535, 350, 16, font, Color.WHITE);
        music = createLabel(Language.allStrings.get(14), textX, height - 355, 350, 16, font, Color.WHITE);
        sound = createLabel(Language.allStrings.get(15), textX, height - 175, 350, 16, font, Color.WHITE);

        // creazione delle icone del menu delle impostazioni
        settingsIcons[0] = getImageIcon("/MenuSprite/BackButton0.png",buttonsSize+10, buttonsSize+10);
        settingsIcons[1] = getImageIcon("/MenuSprite/leftArrow0.png", buttonsSize, buttonsSize);
        settingsIcons[2] = getImageIcon("/MenuSprite/rightArrow0.png", buttonsSize, buttonsSize);
        settingsIcons[3] = getImageIcon("/MenuSprite/leftArrow1.png", buttonsSize, buttonsSize);
        settingsIcons[4] = getImageIcon("/MenuSprite/rightArrow1.png", buttonsSize, buttonsSize);
        settingsIcons[5] = getImageIcon("/MenuSprite/backButton1.png", buttonsSize+10, buttonsSize+10);

        backButton = new JButton();
        backButton.setRolloverIcon(settingsIcons[5]);
        drawJButton(backButton, settingsIcons[0], width-1216, height-684, buttonsSize, buttonsSize, settingsIcons[5]);

        for (int i = 0; i < leftButtons.length; i++) {
            leftButtons[i] = new JButton();
            rightButtons[i] = new JButton();
        }

        // creazione dei bottoni per le impostazioni
        int leftButtonX = width / 2;
        int rightButtonX = width - 300;
        drawJButton(leftButtons[0], settingsIcons[1], leftButtonX, language.getY()-5, buttonsSize, buttonsSize, settingsIcons[3]);
        drawJButton(rightButtons[0], settingsIcons[2], rightButtonX, language.getY()-5, buttonsSize, buttonsSize, settingsIcons[4]);
        drawJButton(leftButtons[1], settingsIcons[1], leftButtonX, music.getY()-5, buttonsSize, buttonsSize, settingsIcons[3]);
        drawJButton(rightButtons[1], settingsIcons[2], rightButtonX, music.getY()-5, buttonsSize, buttonsSize, settingsIcons[4]);
        drawJButton(leftButtons[2], settingsIcons[1], leftButtonX, sound.getY()-5, buttonsSize, buttonsSize, settingsIcons[3]);
        drawJButton(rightButtons[2], settingsIcons[2], rightButtonX, sound.getY()-5, buttonsSize, buttonsSize, settingsIcons[4]);

        // aggiunta dei bottoni al pannello
        for (int i = 0; i < leftButtons.length; i++) {
            this.add(leftButtons[i]);
            this.add(rightButtons[i]);
        }

        // Se le impostazioni si trovano in un layer allora siamo certi che sono state caricate dal menu di pausa
        backButton.addActionListener(e -> {if(layered) SceneManager.getInstance().unloadSettings(); else SceneManager.getInstance().loadMainMenu();});

        // creazione delle bandiere e dei bottoni per la lingua
        flags[0] = loadImage("/MenuSprite/ita.png");
        flags[0] = scaleImage(flags[0],60,40);
        flags[1] = loadImage("/MenuSprite/eng.png");
        flags[1] = scaleImage(flags[1],60,40);
        leftButtons[0].addActionListener(e -> {MenuUtility.setPreviousLanguage(this);});
        rightButtons[0].addActionListener(e -> {MenuUtility.setNextLanguage(this);});

        
        // cambio del volume della musica
        rightButtons[1].addActionListener(e -> {
            SoundEngine.getInstance().setVolume(SaveFileManager.getInstance().getVolume() + 5f);
            SaveFileManager.getInstance().setVolume(SaveFileManager.getInstance().getVolume());
        });

        leftButtons[1].addActionListener(e -> {
            SoundEngine.getInstance().setVolume(SaveFileManager.getInstance().getVolume() - 5f);
            SaveFileManager.getInstance().setVolume(SaveFileManager.getInstance().getVolume());
        });

        // cambio del volume degli effetti sonori
        rightButtons[2].addActionListener(e -> {
            SaveFileManager.getInstance().setVolume(SaveFileManager.getInstance().getVolume() + 5f);
            SaveFileManager.getInstance().setVolume(SaveFileManager.getInstance().getVolume());
        });

        leftButtons[2].addActionListener(e -> {
            SaveFileManager.getInstance().setVolume(SaveFileManager.getInstance().getVolume() - 5f);
            SaveFileManager.getInstance().setVolume(SaveFileManager.getInstance().getVolume());
        });

        this.add(language);
        this.add(music);
        this.add(sound);
        this.add(backButton);

        setBackground(new Color(0,0,0,0));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAndUpdateBG(g, bg);
        drawBox(g);
        switch (Settings.language){
            case ITA -> g.drawImage(flags[0], width/2+160,language.getY()-10,null );
            case ENG -> g.drawImage(flags[1],width/2+160,language.getY()-10,null);
        }

    }

    public void updateStrings(){
        language.setText(Language.allStrings.get(13));
        music.setText(Language.allStrings.get(14));
        sound.setText(Language.allStrings.get(15));
    }
    public void update(){
        this.repaint();
    }

    public void setLayered(boolean layered) {
        this.layered = layered;
    }
}
