package com.yaac.view.Utility;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Classe che contiene metodi utili per la creazione di componenti grafici.
 */
public class MenuUtility {

    /**
     * Metodo per la creazione di un bottone.
     * @param text testo del bottone
     * @param x coordinata x del bottone
     * @param y coordinata y del bottone
     * @param width larghezza del bottone
     * @param height altezza del bottone
     * @param font font del bottone
     * @return il bottone creato
     */
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

    /**
     * Metodo per la creazione di una label
     * @param text testo della label
     * @param x coordinata x della label
     * @param y coordinata y della label
     * @param width larghezza della label
     * @param height altezza della label
     * @param font font della label
     * @param color colore della label
     * @return la label creata
     */
    public static JLabel createLabel(String text, int x, int y, int width, int height, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setForeground(color);
        label.setFont(font);
        label.setVisible(true);
        return label;
    }

    /**
     * Metodo per la creazione di un textArea
     * @param text testo del textArea
     * @param x coordinata x del textArea
     * @param y coordinata y del textArea
     * @param width larghezza del textArea
     * @param height altezza del textArea
     * @param font font del textArea
     * @param color colore del textArea
     * @return il textArea creato
     */
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

    //TODO: cosa fa?
    public static void drawShopButton(JButton button, ImageIcon imageIcon, int x, int y, int width, int height, Color color, Graphics g){
        g.setColor(color);
        g.drawRect(x,y,width,height);
        button.setBounds(x+1,y+1,width-1,height-1);
        button.setIcon(imageIcon);
    }

    //TODO: da spostare in ImageUtility
    public static ImageIcon getImageIcon(String path, int width,int height){
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(MenuUtility.class.getResource(path)));
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(width,height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(resizedImage);
        return imageIcon;
    }

}
