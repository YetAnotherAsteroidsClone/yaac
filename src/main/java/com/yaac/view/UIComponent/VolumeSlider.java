package com.yaac.view.UIComponent;

import com.yaac.Settings;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

import static com.yaac.view.Utility.MenuUtility.loadFont;

/**
 * Classe che rappresenta un componente grafico per la regolazione del volume
 */
public class VolumeSlider extends JPanel{
    private final JSlider slider;
    private final JLabel volumeLabel,volumeText;

    public VolumeSlider(String label, int sliderValue) {
        super();
        // setOpaque(false) rende il pannello trasparente
        setOpaque(false);
        setLayout(new GridLayout());

        volumeText = new JLabel(label);
        volumeText.setFont(loadFont(Settings.FONT_SIZE));
        volumeText.setForeground(Color.WHITE);
        add(volumeText);

        slider = new JSlider(0, 100, sliderValue);
        slider.setUI(new SliderUI(slider));
        slider.setOpaque(false);
        slider.setPaintTicks(false);
        slider.setPaintLabels(false);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);

        volumeLabel = new JLabel(String.valueOf(slider.getValue()), SwingConstants.LEFT);
        volumeLabel.setFont(loadFont(Settings.FONT_SIZE));
        volumeLabel.setForeground(Color.WHITE);
        add(slider);
        volumeLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        add(volumeLabel);
    }

    public void addSliderChangeListener(ChangeListener listener) {
        slider.addChangeListener(listener);
    }

    public JSlider getSlider() {
        return slider;
    }

    public void setVolumeValue(int value) {
        this.volumeLabel.setText(String.valueOf(value));
    }

    public void setLabelText(String text) {
        this.volumeText.setText(text);
    }
}
