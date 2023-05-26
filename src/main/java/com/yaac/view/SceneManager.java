package com.yaac.view;

import com.yaac.GameLoop;
import com.yaac.Settings;
import com.yaac.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/*
    *  Classe dedicata alla gestione delle scene
 */
public class SceneManager {

    private static SceneManager instance;

    static {
        try {
            instance = new SceneManager();
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private JFrame mainFrame;
    private MainMenu mainMenu;
    private Shop shop;

    private GamePanel gamePanel;

    public static SceneManager getInstance() {return instance;}

    private SceneManager() throws IOException, FontFormatException {
        mainFrame = new JFrame(Settings.TITLE);
        mainFrame.setSize(Settings.width, Settings.height);
        mainFrame.setUndecorated(true);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainMenu = new MainMenu();
        shop = new Shop();
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
        shop = new Shop();
        loadScene(shop);
    }

    public void loadGame(){
        gamePanel = new GamePanel();
        GameController controller = new GameController(gamePanel);
        gamePanel.addKeyListener(controller);
        GameLoop gameLoop = new GameLoop(controller);
        loadScene(gamePanel);
        gamePanel.requestFocus();
        gameLoop.run();
    }
    public void loadShop(){loadScene(shop);}
}
