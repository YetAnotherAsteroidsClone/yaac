package com.yaac.view.Utility;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.model.GameConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
     **/
    public static JButton createButton(String text, int x, int y, int width, int height, Font font) {
        JButton button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setBounds(x, y, width, height);
        button.setForeground(Color.WHITE);
        button.setFont(font);
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setIcon(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/MenuSprite/buttonColor0.png"))));
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setRolloverIcon(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/MenuSprite/buttonColor1.png"))));


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
     **/
    public static JLabel createLabel(String text, int x, int y, int width, int height, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setForeground(color);
        label.setFont(font);
        label.setVisible(true);
        return label;
    }

    /**
     * Metodo per la creazione di una textArea
     * @param text testo della textArea
     * @param x coordinata x della textArea
     * @param y coordinata y della textArea
     * @param width larghezza della textArea
     * @param height altezza della textArea
     * @param font font della textArea
     * @param color colore della textArea
     * @return la textArea creata
     **/
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
     * @param button bottone da disegnare
     * @param imageIcon immagine da mostrare sul bottone
     * @param x coordinata x del bottone
     * @param y coordinata y del bottone
     * @param width larghezza del bottone
     * @param height altezza del bottone
     **/
    public static void drawJButton(JButton button, ImageIcon imageIcon, int x, int y, int width, int height){
        button.setBackground(new Color(0,0,0,Color.TRANSLUCENT));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setBounds(x+1,y+1,width-1,height-1);
        button.setIcon(imageIcon);
    }

    /** Disegna un'immagine su un JButton
     * @param button bottone da disegnare
     * @param imageIcon immagine da mostrare sul bottone
     * @param x coordinata x del bottone
     * @param y coordinata y del bottone
     * @param width larghezza del bottone
     * @param height altezza del bottone
     * @param pressedImageIcon immagine da mostrare quando il bottone viene premuto
     **/
    public static void drawJButton(JButton button, ImageIcon imageIcon, int x, int y, int width, int height, ImageIcon pressedImageIcon){
        button.setContentAreaFilled(false);
        button.setIcon(imageIcon);
        button.setPressedIcon(pressedImageIcon);
        button.setBackground(new Color(0,0,0, Color.TRANSLUCENT));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setBounds(x+1,y+1,width-1,height-1);
    }

    /**
     * Metodo per disegnare i vari pulsanti nello shop
     * @param button button
     * @param imageIcon button icon
     * @param x x
     * @param y y
     * @param width width
     * @param height height
     * @param color border color
     * @param g Graphics
     **/
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

    /**
     * Metodo per disegnare le barre di stato dei PowerUp nel negozio
     * @param  panel panel
     * @param button button
     * @param gemsColor gem color
     * @param powerUpColor powerUp color
     * @param pwUpImage powerUp image
     * @param lockerImage locker icon
     * @param plusIcon plus icon
     * @param gemCurrentFrame gem icon animation
     * @param x x
     * @param y y
     * @param font font
     * @param pwUp powerUp name
     * @param levels levels
     * @param g Graphics
     **/
    public static void drawShopPwUpBar(JPanel panel, JButton button, Color gemsColor, Color powerUpColor, BufferedImage pwUpImage, BufferedImage lockerImage, ImageIcon plusIcon, Image gemCurrentFrame, int x, int y, Font font, String pwUp, int levels, Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawRect(x-55,y-10,40,40);
        g.drawImage(pwUpImage,x-54,y-9,null);
        g.drawRect(x,y,301,30);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x+1,y+1,299,28);
        g.setColor(Color.YELLOW);
        int drawPwUpX = x+1;
        for(int i=0; i<levels; i++){
            g.fillRect(drawPwUpX,y+1,30,28);
            drawPwUpX+=30;
        }
        if(levels<10){
            g.drawImage(gemCurrentFrame,x+280,y-45,null);
            g.setColor(gemsColor);
            if(GameConstraints.getInstance().getGems()<GameConstraints.getInstance().getCost(levels-1)){
                panel.remove(button);
                g.setColor(Color.RED);
                g.drawImage(lockerImage,x+309,y-5,null);
            }
            else{
                MenuUtility.drawShopButton(button,plusIcon,x+314,y,30,30,Color.LIGHT_GRAY,g);
                panel.add(button);
            }
            g.drawString(""+GameConstraints.getInstance().getCost(levels-1), x+314,y-15);

            g.setColor(Color.WHITE);
            g.drawString("LVL "+levels,x,y-10);
        }
        else{
            panel.remove(button);
            g.setColor(Color.YELLOW);
            g.drawString("MAX!", x,y-10);
        }
        g.setColor(powerUpColor);
        g.drawString(pwUp, x+8,y+22);
    }

    /**
     * Metodo per la creazione di pulsanti per l'acquisto di PwUp da usare in gioco
     * @param panel panel
     * @param button button
     * @param gemsColor gem color
     * @param currentFrame icon animation
     * @param gemCurrentFrame gem icon animation
     * @param lockerImage locker icon
     * @param cost cost
     * @param  purchased true if purchased
     * @param x x
     * @param y y
     * @param width width
     * @param height height
     * @param font font
     * @param g Graphics
     **/
    public static void drawPurchasableShopPwUp(JPanel panel, JButton button, Color gemsColor, Image currentFrame, Image gemCurrentFrame, BufferedImage lockerImage, int cost, boolean purchased, int x, int y, int width, int height, Font font, Graphics g){
        g.setFont(font);
        if(purchased){
            panel.remove(button);
            g.setColor(Color.GREEN);
            g.drawRect(x,y,width,height);
            g.drawImage(currentFrame,x+1,y+1,null);
        }
        else{
            g.drawImage(gemCurrentFrame,x-10,y+75,null);
            ImageIcon pwUpIcon = new ImageIcon(currentFrame);
            if(GameConstraints.getInstance().getGems()<cost){
                panel.remove(button);
                g.setColor(Color.RED);
                g.drawRect(x,y,width,height);
                g.drawImage(currentFrame,x+1,y+1,null);
                g.drawImage(lockerImage,x+15,y+15,null);
            }
            else{
                drawShopButton(button,pwUpIcon,x,y,width,height,gemsColor,g);
                panel.add(button);
            }
            g.drawString(""+cost,x+25,y+105);
        }
    }

    /**
     * Metodo per caricare e scalare le immagini di sfondo.
     * @param bg array delle immagini di sfondo
     * @param windowWidth larghezza della finestra di gioco
     * @param windowHeight altezza della finestra di gioco
     **/
    public static void createBG(ObjectAnimation[] bg, int windowWidth, int windowHeight) {
        bg[0] = new ObjectAnimation("/Background/BackgroundL1.png", 640, 360);
        bg[1] = new ObjectAnimation("/Background/MainBackgroundL2.png", 640, 360);
        bg[2] = new ObjectAnimation("/Background/MainBackgroundL3.png", 640, 360);
        bg[0].scaleImage(windowWidth, windowHeight);
        bg[1].scaleImage(windowWidth, windowHeight);
        bg[2].scaleImage(windowWidth, windowHeight);
    }

    /**
     * Metodo per disegnare e aggiornare le immagini di sfondo.
     * @param g Graphics su cui disegnare
     * @param bg array delle immagini di sfondo
     **/
    public static void drawAndUpdateBG(Graphics g, ObjectAnimation[] bg) {
        for (ObjectAnimation o : bg) {
            g.drawImage(o.getCurrentFrame(), 0, 0, null);
            o.update();
        }
    }

    /**
     * Metodo per disegnare un rettangolo con bordi arrotondati.
     * @param g Graphics su cui disegnare
     **/
    public static void drawBox(Graphics g) {
        g.setColor(new Color(0,0,0,150));
        int x = Settings.width / 20;
        int y = Settings.height / 20;
        g.fillRoundRect(x, y, Settings.width - (x * 2), Settings.height - (y *2), 20, 20);
    }

    /**
     * Metodo per il caricamento del font di gioco.
     * @param size dimensione del font
     * @return il font di gioco con dimensione size
     **/
    public static Font loadFont(float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(Settings.FONT))).deriveFont(size);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static ImageIcon changeLanguageFlag(ImageIcon[] flags) {
        if (Settings.language.equals("it"))
            return flags[0];
        else
            return flags[1];
    }

    public static void changeLanguage() {
        if (Settings.language.equals("it"))
            Settings.language = "en";
        else
            Settings.language = "it";
    }
}
