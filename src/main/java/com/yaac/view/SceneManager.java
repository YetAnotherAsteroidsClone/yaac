package com.yaac.view;

import com.yaac.Loop;
import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.controller.*;
import com.yaac.model.SaveFileManager;
import com.yaac.view.Utility.MenuUtility;
import com.yaac.view.Utility.Sound;

import javax.swing.*;
import java.awt.*;
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
    private final GameSettings gameSettings;
    private PauseMenu pauseMenu;
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
        gameSettings = new GameSettings();
    }

    public void openMainFrame(){
        mainFrame.setVisible(true);
    }

    private void loadScene(JPanel panel){
        mainFrame.setContentPane(panel);
        mainFrame.revalidate();
    }

    public void loadMainMenu() {
        SaveFileManager.getInstance().loadSaveFile();
        mainMenu = new MainMenu();
        MainMenuController controller = new MainMenuController(mainMenu);
        Loop mainMenuLoop = new Loop(controller);
        mainMenu.addMouseListener(controller);
        loadScene(mainMenu);
        MenuUtility.getMusic().loop();
        mainMenu.requestFocus();
        mainMenuLoop.start();
    }

    public void loadSettings(){
        GameSettingsController controller = new GameSettingsController(gameSettings);
        Loop gameSettingsLoop = new Loop(controller);
        //gameSettings.addMouseListener(controller);
        loadScene(gameSettings);
        gameSettings.requestFocus();
        gameSettingsLoop.start();
        MenuUtility.getMusic().play();
    }

    public void loadCredits(){
        credits = new Credits();
        CreditsController controller = new CreditsController(credits);
        Loop creditsLoop = new Loop(controller);
        credits.addKeyListener(controller);
        loadScene(credits);
        credits.requestFocus();
        creditsLoop.start();
    }

    public void loadPauseMenu() {
        pauseMenu = new PauseMenu();
        loadScene(pauseMenu);
        pauseMenu.requestFocus();
        MenuUtility.getMusic().pause();
    }

    public void loadGame(){
        gamePanel = new GamePanel();
        GameController controller = new GameController(gamePanel);
        gamePanel.addKeyListener(controller);
        Loop gameLoop = new Loop(controller);
        loadScene(gamePanel);
        controller.setLoop(gameLoop);
        gamePanel.requestFocus();
        gameLoop.start();
    }
    public void loadShop(){
        ShopController controller = new ShopController(shop);
        shop.addMouseListener(controller);
        Loop shopLoop = new Loop(controller);
        loadScene(shop);
        shop.requestFocus();
        shopLoop.start();
    }

    public void unloadScene(JPanel panel){
        // TODO: serve per togliere la schermata di pausa e tornare al gioco. Magari anche per le impostazioni?
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
