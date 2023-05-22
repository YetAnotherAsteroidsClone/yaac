package com.yaac.view;

import com.yaac.Settings;

import javax.swing.*;

/*
    *  Classe dedicata alla gestione delle scene
 */
public class SceneManager {

    private static SceneManager instance = new SceneManager();

    private JFrame mainFrame;

    public static SceneManager getInstance() {return instance;}

    private SceneManager() {
        Settings.getInstance();
        mainFrame = new JFrame(Settings.TITLE);
        mainFrame.setSize(Settings.getInstance().width, Settings.getInstance().height);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void openMainFrame(){
        mainFrame.setVisible(true);
    }

    private void loadScene(JPanel panel){
        mainFrame.setContentPane(panel);
        mainFrame.revalidate();
    }

    public void loadMainMenu(){
        //TODO
    }

    public void loadSettings(){
        //TODO
    }

    public void loadCredits(){
        //TODO
    }

    public void loadGame(){
        //TODO
    }

    public void loadShop(){
        //TODO
    }
}
