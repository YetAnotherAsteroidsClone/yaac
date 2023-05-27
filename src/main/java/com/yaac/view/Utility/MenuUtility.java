package com.yaac.view.Utility;

import javax.swing.*;
import java.awt.*;

public class MenuUtility {
    public static JButton createButton(String text, int x, int y, int width, int height, Font font) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(font);
        button.setFocusable(false);
        button.setBorderPainted(false);
        return button;
    }

    public static JLabel createLabel(String text, int x, int y, int width, int height, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setForeground(color);
        label.setFont(font);
        label.setVisible(true);
        return label;
    }

}
