package com.yaac.view.UIComponent;

import com.yaac.Settings;
import com.yaac.model.Language;
import com.yaac.view.Utility.MenuUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static com.yaac.view.Utility.ImageUtility.*;
import static com.yaac.view.Utility.ImageUtility.getImageIcon;
import static com.yaac.view.Utility.MenuUtility.loadFont;

public class LanguageSwitcher extends JPanel {
    private final JButton leftButton, rightButton;
    private final ImageIcon flag;
    private final JLabel flagLabel, languageLabel;
    BufferedImage[] flags = new BufferedImage[Language.languageList.values().length];

    public LanguageSwitcher() {
        super();
        setOpaque(false);
        setLayout(null);

        languageLabel = new JLabel();
        languageLabel.setText(Language.allStrings.get(13));
        languageLabel.setFont(loadFont(Settings.FONT_SIZE));
        languageLabel.setForeground(Color.WHITE);
        languageLabel.setBounds(0,0,150,40);
        add(languageLabel);

        leftButton = new JButton();

        MenuUtility.drawJButton(leftButton, getImageIcon("/MenuSprite/leftArrow0.png", 30, 30), 450,0,30,30, getImageIcon("/MenuSprite/leftArrow1.png", 30, 30));

        add(leftButton);

        flags[0] = loadImage("/Languages/ita.png");
        flags[0] = scaleImage(flags[0],60,40);
        flags[1] = loadImage("/Languages/eng.png");
        flags[1] = scaleImage(flags[1],60,40);
        flags[2] = loadImage("/Languages/spa.png");
        flags[2] = scaleImage(flags[2],60,40);
        flags[3] = loadImage("/Languages/fra.png");
        flags[3] = scaleImage(flags[3],60,40);
        flags[4] = loadImage("/Languages/cal.png");
        flags[4] = scaleImage(flags[4],60,40);

        flagLabel = new JLabel();

        flag = new ImageIcon();
        updateFlag();
        flagLabel.setBounds(650,0,60,40);
        add(flagLabel);

        rightButton = new JButton();
        MenuUtility.drawJButton(rightButton, getImageIcon("/MenuSprite/rightArrow0.png", 30, 30), 850,0,30,30, getImageIcon("/MenuSprite/rightArrow1.png", 30, 30));
        add(rightButton);
    }

    public void addLeftButtonActionListener(ActionListener actionListener){
        leftButton.addActionListener(actionListener);
    }

    public void addRightButtonActionListener(ActionListener actionListener){
        rightButton.addActionListener(actionListener);
    }

    public void updateFlag(){
        switch (Settings.language){
            case ITA -> flag.setImage(flags[0]);
            case ENG -> flag.setImage(flags[1]);
            case SPA -> flag.setImage(flags[2]);
            case FRA -> flag.setImage(flags[3]);
            case CAL -> flag.setImage(flags[4]);
        }
        flagLabel.setIcon(flag);
    }

    public void updateLanguageLabel(){
        languageLabel.setText(Language.allStrings.get(13));
    }
}
