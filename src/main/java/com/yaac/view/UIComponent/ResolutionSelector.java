package com.yaac.view.UIComponent;

import com.yaac.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static com.yaac.view.Utility.MenuUtility.loadFont;

/**
 * Classe che rappresenta un componente grafico per la selezione della risoluzione
 */
public class ResolutionSelector extends JPanel {
    private final JComboBox<String> resolution;
    private final JLabel resolutionText;

    public ResolutionSelector(String text) {
        // setOpaque(false) rende il pannello trasparente
        setOpaque(false);
        setLayout(new GridLayout());

        resolutionText = new JLabel(text);
        resolutionText.setFont(loadFont(Settings.FONT_SIZE));
        resolutionText.setForeground(Color.WHITE);
        add(resolutionText);

        resolution = new JComboBox<>(Settings.resolutions);
        resolution.setEditable(false);
        resolution.setFont(loadFont(Settings.FONT_SIZE));
        resolution.setBackground(new Color(0,0,0,0));
        resolution.setForeground(Color.WHITE);
        resolution.setSelectedItem(Settings.width + "x" + Settings.height);
        resolution.setRenderer(new Renderer());

        add(resolution);
    }

    public void addComboBoxActionListener(ActionListener listener) {
        resolution.addActionListener(listener);
    }

    public void setResolutionText(String text){
        resolutionText.setText(text);
    }

    public JComboBox<String> getComboBox() {
        return resolution;
    }

    public void reloadFont() {
        resolutionText.setFont(loadFont(Settings.FONT_SIZE));
        resolution.setFont(loadFont(Settings.FONT_SIZE));
    }
}
