package com.yaac.view;

import com.yaac.Loop;
import com.yaac.Settings;
import com.yaac.controller.GameController;
import com.yaac.controller.MainMenuController;
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

    private final JFrame mainFrame;
    private MainMenu mainMenu;
    private Shop shop;
    private Credits credits;

    private Settings settings;

    private GamePanel gamePanel;

    public static SceneManager getInstance() {return instance;}

    private SceneManager() throws IOException, FontFormatException {
        mainFrame = new JFrame(Settings.TITLE);
        mainFrame.setSize(Settings.width, Settings.height);
        mainFrame.setUndecorated(true);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        shop = new Shop();
    }

    public void openMainFrame(){
        mainFrame.setVisible(true);
    }

    private void loadScene(JPanel panel){
        mainFrame.setContentPane(panel);
        mainFrame.revalidate();
    }

    public void loadMainMenu() throws IOException, FontFormatException {
        mainMenu = new MainMenu();
        MainMenuController controller = new MainMenuController(mainMenu);
        Loop mainMenuLoop = new Loop(controller);
        mainMenu.addMouseListener(controller);
        loadScene(mainMenu);
        mainMenu.requestFocus();
        mainMenuLoop.run();
    }

    public void loadSettings(){
        //TODO
    }

    public void loadCredits(){
        //TODO
        /*credits = new Credits();
        loadScene(credits);*/
    }

    public void loadGame(){
        gamePanel = new GamePanel();
        GameController controller = new GameController(gamePanel);
        gamePanel.addKeyListener(controller);
        Loop gameLoop = new Loop(controller);
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


    /**
     * Controlla se il pannello è caricato
     * @param panel
     * @return true se il pannello è caricato, false altrimenti
     */
    public static boolean isLoaded(JPanel panel){
        return panel.getParent() != null; // Controlla se il JPanel non si trova più all'interno del JFrame principale
    }

    public boolean isInGame() {return inGame;}
    public void setInGame(boolean inGame) {this.inGame = inGame;}
}
