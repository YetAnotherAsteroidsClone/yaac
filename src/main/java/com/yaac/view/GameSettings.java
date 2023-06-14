package com.yaac.view;

import com.yaac.Settings;
import com.yaac.model.Language;
import com.yaac.model.SaveFileManager;
import com.yaac.view.UIComponent.LanguageSwitcher;
import com.yaac.view.UIComponent.ResolutionSelector;
import com.yaac.view.UIComponent.VolumeSlider;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.ObjectAnimation;
import com.yaac.view.Utility.Sound;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

import static com.yaac.view.Utility.MenuUtility.*;
import static com.yaac.view.Utility.ImageUtility.*;
import static com.yaac.Settings.*;

public class GameSettings extends JPanel {
    ImageIcon[] settingsIcons = new ImageIcon[6];
    ObjectAnimation[] bg =  new ObjectAnimation[3];
    private boolean layered = false;
    private final VolumeSlider musicSlider, soundSlider;
    private ResolutionSelector resolutionSelector;
    private final LanguageSwitcher languageSwitcher;
    private final int componentsX = 192;
    public GameSettings() {
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(null);
        createBG(bg, width, height);

        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(new Color(0,0,0,0)));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.WHITE));

        // creazione del tasto indietro
        int buttonsSize = 30;
        JButton backButton = new JButton();
        backButton.setRolloverIcon(settingsIcons[5]);
        drawJButton(backButton, getImageIcon("/MenuSprite/BackButton0.png", buttonsSize +10, buttonsSize +10), 0, 0, buttonsSize, buttonsSize, getImageIcon("/MenuSprite/backButton1.png", buttonsSize +10, buttonsSize +10));

        backButton.addActionListener(e -> {
            checkLoading();
        });

        // Esc per tornare indietro
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                checkLoading();
            }
        });

        // Cambio del volume della musica
        musicSlider = new VolumeSlider(Language.allStrings.get(14),(Sound.decibelPercentage(SaveFileManager.getInstance().getMusicVolume())));
        musicSlider.setBounds(componentsX, 100, width, 50);
        musicSlider.addSliderChangeListener(e -> {
            SoundEngine.getInstance().setMusicVolume((float) (20*Math.log10((double) musicSlider.getSlider().getValue() /100)));
            musicSlider.setVolumeValue(musicSlider.getSlider().getValue());
        });


        // Cambio del volume degli effetti sonori
        soundSlider = new VolumeSlider(Language.allStrings.get(15),(Sound.decibelPercentage(SaveFileManager.getInstance().getVolume())));
        soundSlider.setBounds(componentsX, 250, width, 50);
        soundSlider.addSliderChangeListener(e -> {
            SoundEngine.getInstance().setVolume((float) (20*Math.log10((double) soundSlider.getSlider().getValue() /100)));
            soundSlider.setVolumeValue(soundSlider.getSlider().getValue());
        });

        if(!SceneManager.getInstance().isInGame()){
            // JComboBox per selezionare la risoluzione
            resolutionSelector = new ResolutionSelector(Language.allStrings.get(31));
            resolutionSelector.setBounds(componentsX, 400, 900, 50);
            resolutionSelector.addComboBoxActionListener(e -> {
                String[] res = ((String) Objects.requireNonNull(resolutionSelector.getComboBox().getSelectedItem())).split("x");
                Settings.width = Integer.parseInt(res[0]);
                Settings.height = Integer.parseInt(res[1]);
                SaveFileManager.getInstance().setResolution(Settings.width+"x"+Settings.height);
                SceneManager.getInstance().changeResolution(Settings.width, Settings.height);
                updateResolution();
            });
            this.add(resolutionSelector);
        }

        // Cambio della lingua
        languageSwitcher = new LanguageSwitcher();
        if(!SceneManager.getInstance().isInGame())
            languageSwitcher.setBounds(componentsX, 550, width, 50);
        else
            languageSwitcher.setBounds(componentsX, 400, width, 50);
        languageSwitcher.addLeftButtonActionListener(e -> MenuUtility.setPreviousLanguage(this));
        languageSwitcher.addRightButtonActionListener(e -> MenuUtility.setNextLanguage(this));
        this.add(languageSwitcher);

        this.add(backButton);
        this.add(musicSlider);
        this.add(soundSlider);

        setBackground(new Color(0,0,0,0));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAndUpdateBG(g, bg);
        drawBox(g);
    }

    public void updateStrings(){
        musicSlider.setLabelText(Language.allStrings.get(14));
        musicSlider.reloadFont();
        soundSlider.setLabelText(Language.allStrings.get(15));
        soundSlider.reloadFont();
        resolutionSelector.setResolutionText(Language.allStrings.get(31));
        resolutionSelector.reloadFont();
        languageSwitcher.updateFlag();
        languageSwitcher.updateLanguageLabel();
    }

    public void updateResolution(){
        createBG(bg, width, height);
        musicSlider.setBounds(componentsX, 100, width, 50);
        soundSlider.setBounds(componentsX, 250, width, 50);
    }
    public void update(){
        this.repaint();
    }

    public void setLayered(boolean layered) {
        this.layered = layered;
    }

    private void checkLoading(){
        // Se le impostazioni si trovano in un layer allora siamo certi che sono state caricate dal menu di pausa
        if (layered)
            SceneManager.getInstance().unloadSettings();
        else SceneManager.getInstance().loadMainMenu();
    }
}
