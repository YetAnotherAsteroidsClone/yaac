package com.yaac.view.UIComponent;

import com.yaac.Settings;
import com.yaac.model.Language;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.ActionListener;

import static com.yaac.view.Utility.MenuUtility.loadFont;

/**
 * Classe che rappresenta un componente grafico per la selezione della risoluzione
 */
public class ResolutionSelector extends JPanel {
    private final JComboBox<String> resolution;
    private final JLabel resolutionText;

    public ResolutionSelector() {
        // setOpaque(false) rende il pannello trasparente
        setOpaque(false);
        setLayout(new GridLayout());

        resolutionText = new JLabel(Language.allStrings.get(31));
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
}
