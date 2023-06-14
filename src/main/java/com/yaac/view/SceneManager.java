package com.yaac.view;

import com.yaac.Loop;
import com.yaac.Settings;
import com.yaac.controller.*;
import com.yaac.model.SaveFileManager;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;

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
    private boolean inGame;

    private final JFrame mainFrame;
    private MainMenu mainMenu;
    private Shop shop;
    private Credits credits;
    private GameSettings gameSettings;
    private PauseMenu pauseMenu;
    private GamePanel gamePanel;
    private Loop gameLoop;
    private GameOver gameOver;
    private final JLayeredPane layeredPane;

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
        inGame = false;
        SaveFileManager.getInstance().loadSaveFile();
        mainMenu = new MainMenu();
        MainMenuController controller = new MainMenuController(mainMenu);
        Loop mainMenuLoop = new Loop(controller);
        mainMenu.addMouseListener(controller);
        loadScene(mainMenu);
        SoundEngine.getInstance().start();
        SoundEngine.getInstance().loopMusic();
        mainMenu.requestFocus();
        mainMenuLoop.start();
        Settings.LOGGER.log(Level.INFO, "Main menu loaded");
    }

    /** Carica le impostazioni di gioco
     * @param layered se true, carica le impostazioni in un layer superiore
     */
    public void loadSettings(boolean layered){
        gameSettings = new GameSettings();
        GameSettingsController controller = new GameSettingsController(gameSettings);
        Loop gameSettingsLoop = new Loop(controller);
        if(layered) { // Le impostazioni sono state chiamate dal menu di pausa
            loadScene(gameSettings, JLayeredPane.DEFAULT_LAYER);
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
        Settings.LOGGER.log(Level.INFO, "Game settings loaded");
    }

    public void loadCredits(){
        credits = new Credits();
        CreditsController controller = new CreditsController(credits);
        Loop creditsLoop = new Loop(controller);
        credits.addKeyListener(controller);
        loadScene(credits);
        credits.requestFocus();
        creditsLoop.start();
        Settings.LOGGER.log(Level.INFO, "Credits loaded");
        Settings.superSecretCounter = 0;
    }

    public void loadPauseMenu() {
        pauseMenu = new PauseMenu();
        loadScene(pauseMenu, JLayeredPane.DEFAULT_LAYER);
        pauseMenu.grabFocus();
        SoundEngine.getInstance().stopMusic();
        layeredPane.moveToFront(pauseMenu);
        Settings.LOGGER.log(Level.INFO, "Game paused");
    }

    public void loadGameOver() {
        gameOver = new GameOver();
        loadScene(gameOver, JLayeredPane.DEFAULT_LAYER);
        gameOver.grabFocus();
        layeredPane.moveToFront(gameOver);
        Settings.LOGGER.log(Level.INFO, "Game over screen loaded");
    }

    public void unloadGameOver(){
        unloadScene(gameOver);
        gameOver = null;
        SoundEngine.getInstance().loopMusic();
        gamePanel.requestFocus();
        gameLoop.start();
        Settings.LOGGER.log(Level.INFO, "Game restarted");
    }

    public void loadGame(){
        inGame = true;
        gamePanel = new GamePanel();
        GameController controller = new GameController(gamePanel);
        gamePanel.addKeyListener(controller);
        gameLoop = new Loop(controller);
        loadScene(gamePanel);
        controller.setLoop(gameLoop);
        gamePanel.requestFocus();
        gamePanel.addLoop(gameLoop);
        gameLoop.start();
        Settings.LOGGER.log(Level.INFO, "Game started");
    }

    public void loadShop(){
        try {shop = new Shop();}
        catch (IOException | FontFormatException e) {throw new RuntimeException(e);}
        shop.setCurrentGadgets();
        ShopController controller = new ShopController(shop);
        shop.addMouseListener(controller);
        Loop shopLoop = new Loop(controller);
        loadScene(shop);
        shop.requestFocus();
        shopLoop.start();
        Settings.LOGGER.log(Level.INFO, "Shop opened");
    }

    public void unloadPauseMenu(){
        unloadScene(pauseMenu);
        pauseMenu = null;
        SoundEngine.getInstance().loopMusic();
        gamePanel.requestFocus();
        gameLoop.start();
        Settings.LOGGER.log(Level.INFO, "Game resumed");
    }

    private void unloadScene(JPanel panel){
        layeredPane.remove(panel);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    public void unloadSettings(){
        unloadScene(gameSettings);
        pauseMenu.setVisible(true);
        pauseMenu.requestFocus();
        pauseMenu.grabFocus();
        Settings.LOGGER.log(Level.INFO, "Settings closed");
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

    /**
     * Cambia la risoluzione del gioco
     * @param width
     * @param height
     */
    public void changeResolution(int width, int height){
        Settings.width = width;
        Settings.height = height;
        mainFrame.setSize(width, height);
        mainFrame.setLocationRelativeTo(null);
        if(gamePanel != null) gamePanel.setBounds(0,0,width,height);
        if(mainMenu != null) mainMenu.setBounds(0,0,width,height);
        if(gameOver != null) gameOver.setBounds(0,0,width,height);
        if(pauseMenu != null) pauseMenu.setBounds(0,0,width,height);
        if(gameSettings != null) gameSettings.setBounds(0,0,width,height);
        if(credits != null) credits.setBounds(0,0,width,height);
        if(shop != null) shop.setBounds(0,0,width,height);
    }
}