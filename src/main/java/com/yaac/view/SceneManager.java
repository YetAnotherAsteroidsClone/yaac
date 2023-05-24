package com.yaac.view;

import com.yaac.Settings;
import com.yaac.controller.GameController;

import javax.swing.*;

/*
    *  Classe dedicata alla gestione delle scene
 */
public class SceneManager {

    private static SceneManager instance = new SceneManager();

    private JFrame mainFrame;
    private MainMenu mainMenu;

    private GamePanel gamePanel;

    public static SceneManager getInstance() {return instance;}

    private SceneManager() {
        mainFrame = new JFrame(Settings.TITLE);
        mainFrame.setSize(Settings.width, Settings.height);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainMenu = new MainMenu();
    }

    public void openMainFrame(){
        mainFrame.setVisible(true);
    }

    private void loadScene(JPanel panel){
        mainFrame.setContentPane(panel);
        mainFrame.revalidate();
    }

    public void loadMainMenu(){
        loadScene(mainMenu);
    }

    public void loadSettings(){
        //TODO
    }

    public void loadCredits(){
        //TODO
    }

    public void loadGame(){
        gamePanel = new GamePanel();
        gamePanel.addKeyListener(new GameController(gamePanel));
        loadScene(gamePanel);
    }

    public void loadShop(){
        //TODO
    }
}
