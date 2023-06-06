package com.yaac.view.Utility;

import com.yaac.Settings;
import com.yaac.view.SceneManager;

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
        button.setVisible(true);
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

    /** Disegna un'immagine su un JButton
     * @param button
     * @param imageIcon
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public static void drawJButton(JButton button, ImageIcon imageIcon, int x, int y, int width, int height){
        button.setBackground(new Color(0,0,0,Color.TRANSLUCENT));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setBounds(x+1,y+1,width-1,height-1);
        button.setIcon(imageIcon);
    }

    public static void drawJButton(JButton button, ImageIcon imageIcon, int x, int y, int width, int height, ImageIcon pressedImageIcon){
        //change the color of the button when clicked
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setPressedIcon(pressedImageIcon);
        button.setIcon(imageIcon);
        button.setBackground(new Color(0,0,0, Color.TRANSLUCENT));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setBounds(x+1,y+1,width-1,height-1);
    }

    //TODO: cosa fa?
    public static void drawShopButton(JButton button, ImageIcon imageIcon, int x, int y, int width, int height, Color color, Graphics g){
        if(color != null) {
            g.setColor(color);
            g.drawRect(x, y, width, height);
        }
        button.setBackground(new Color(0,0,0,Color.TRANSLUCENT));
        button.setBorderPainted(false);
        button.setBounds(x+1,y+1,width-1,height-1);
        button.setIcon(imageIcon);
    }

    public static void createBG(ObjectAnimation[] bg, int windowWidth, int windowHeight) {
        bg[0] = new ObjectAnimation("/Background/BackgroundL1.png", 640, 360);
        bg[1] = new ObjectAnimation("/Background/MainBackgroundL2.png", 640, 360);
        bg[2] = new ObjectAnimation("/Background/MainBackgroundL3.png", 640, 360);
        bg[0].scaleImage(windowWidth, windowHeight);
        bg[1].scaleImage(windowWidth, windowHeight);
        bg[2].scaleImage(windowWidth, windowHeight);
    }

    public static void drawAndUpdateBG(Graphics g, ObjectAnimation[] bg) {
        g.drawImage(bg[0].getCurrentFrame(), 0, 0, null);
        g.drawImage(bg[1].getCurrentFrame(), 0, 0, null);
        g.drawImage(bg[2].getCurrentFrame(), 0, 0, null);
        bg[0].update();
        bg[1].update();
        bg[2].update();
    }

    public static String getResolution() {
        return Settings.width + "x" + Settings.height;
    }

    public static void changeResolution() {
        if (Settings.width == Settings.widths[0] && Settings.height == Settings.heights[0]) {
            Settings.width = Settings.widths[1];
            Settings.height = Settings.heights[1];
        }
        else {
            Settings.width = Settings.widths[0];
            Settings.height = Settings.heights[0];
        }

    }


    public static void drawBox(Graphics g) {
        g.setColor(new Color(0,0,0,150));
        int x = Settings.width / 20;
        int y = Settings.height / 20;
        g.fillRoundRect(x, y, Settings.width - (x * 2), Settings.height - (y *2), 20, 20);

    }
}
