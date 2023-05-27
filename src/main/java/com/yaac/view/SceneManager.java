package com.yaac.view;

import com.yaac.GameLoop;
import com.yaac.Settings;
import com.yaac.controller.GameController;
import com.yaac.controller.ShopController;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FontFormatException;
import java.io.IOException;

/*
    *  Classe dedicata alla gestione delle scene
 */
public class SceneManager {

    private static final SceneManager instance;

    static {
        try {
            instance = new SceneManager();
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean inGame=false;

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
    public void loadShop(){
        ShopController controller = new ShopController(shop);
        shop.addMouseListener(controller);
        loadScene(shop);
        shop.requestFocus();
    }

    public boolean isInGame() {return inGame;}
    public void setInGame(boolean inGame) {this.inGame = inGame;}
}
