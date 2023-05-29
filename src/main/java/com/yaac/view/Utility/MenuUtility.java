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

    public static JTextArea createTextArea(String text, int x, int y, int width, int height, Font font, Color color) {
        JTextArea textArea = new JTextArea(text);
        textArea.setBounds(x, y, width, height);
        textArea.setForeground(color);
        textArea.setFont(font);
        textArea.setVisible(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        return textArea;
    }

    public static void drawShopButton(JButton button, ImageIcon imageIcon, int x, int y, int width, int height, Color color, Graphics g){
        g.setColor(color);
        g.drawRect(x,y,width,height);
        button.setBounds(x+1,y+1,width-1,height-1);
        button.setIcon(imageIcon);
    }

    public static ImageIcon getImageIcon(String path, int width,int height){
        ImageIcon imageIcon = new ImageIcon(MenuUtility.class.getResource(path));
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(width,height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedImage);
        return imageIcon;
    }

}
