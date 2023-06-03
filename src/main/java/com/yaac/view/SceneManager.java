package com.yaac.view;

import com.yaac.Loop;
import com.yaac.Main;
import com.yaac.Settings;
import com.yaac.controller.*;
import com.yaac.model.Game;
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
    private Loop gameLoop;
    private JLayeredPane layeredPane;

    public static SceneManager getInstance() {return instance;}

    private SceneManager() throws IOException, FontFormatException {
        mainFrame = new JFrame(Settings.TITLE);
        mainFrame.setSize(Settings.width, Settings.height);
        mainFrame.setUndecorated(true);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        mainFrame.setContentPane(layeredPane);

        shop = new Shop();
        gameSettings = new GameSettings();
    }

    public void openMainFrame(){
        mainFrame.setVisible(true);
    }

    /** Carica un JPanel nel layer di default (0) <br>
     *  Rimuove tutti i JPanel presenti nel layer
     * @param panel
     */
    private void loadScene(JPanel panel){
        layeredPane.removeAll();
        loadScene(panel, JLayeredPane.DEFAULT_LAYER);
    }

    /** Carica un JPanel nel layer specificato
     * @param panel
     * @param layer
     */
    private void loadScene(JPanel panel, int layer){
        layeredPane.add(panel, layer);
        panel.setBounds(0,0,Settings.width,Settings.height);
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

    /** Carica le impostazioni di gioco
     * @param layered se true, carica le impostazioni in un layer superiore
     */
    public void loadSettings(boolean layered){
        GameSettingsController controller = new GameSettingsController(gameSettings);
        Loop gameSettingsLoop = new Loop(controller);
        //gameSettings.addMouseListener(controller);
        if(layered) { // Le impostazioni sono state chiamate dal menu di pausa
            loadScene(gameSettings, JLayeredPane.POPUP_LAYER);
            gameSettings.setLayered(true);
            pauseMenu.setVisible(false);
            layeredPane.moveToFront(gameSettings);
        }
        else{
            loadScene(gameSettings);
            gameSettings.setLayered(false);
        }
        gameSettings.requestFocus();
        gameSettings.grabFocus();
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
        loadScene(pauseMenu, JLayeredPane.DEFAULT_LAYER);
        pauseMenu.grabFocus();
        MenuUtility.getMusic().pause();
        layeredPane.moveToFront(pauseMenu);
    }

    public void loadGame(){
        gamePanel = new GamePanel();
        GameController controller = new GameController(gamePanel);
        gamePanel.addKeyListener(controller);
        gameLoop = new Loop(controller);
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

    public void unloadPauseMenu(){
        unloadScene(pauseMenu);
        pauseMenu = null;
        MenuUtility.getMusic().play();
        gamePanel.requestFocus();
        gameLoop.start();
    }

    private void unloadScene(JPanel panel){
        layeredPane.remove(panel);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    public void unloadSettings(){
        unloadScene(gameSettings);
        MenuUtility.getMusic().play();
        pauseMenu.setVisible(true);
        pauseMenu.requestFocus();
        pauseMenu.grabFocus();
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
