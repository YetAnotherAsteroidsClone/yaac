package com.yaac.view;

import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.controller.GameController;
import com.yaac.controller.GameSettingsController;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.ObjectAnimation;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class PauseMenu extends JPanel {
    private int windowWidth = Settings.width/15;
    private int windowHeight = Settings.height/15;

    public PauseMenu() {
        this.setLayout(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0,0,0,150));
        g.fillRoundRect(windowWidth, windowHeight, Settings.width-(windowWidth*2),Settings.height-(windowHeight*2), 20, 20);
    }

    public void update(){
        this.repaint();
    }

}
