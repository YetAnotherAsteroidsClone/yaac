package com.yaac.model;

import com.yaac.Settings;
import com.yaac.model.Utility.SaveFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class SaveFileManager {

    private static final SaveFileManager instance = new SaveFileManager();

    public static SaveFileManager getInstance() {return instance;}

    private SaveFile saveFile;

    private SaveFileManager() {
        this.saveFile = null;
        open();
    }

    /** Metodo per aprire il file di salvataggio
     *  Se il file non esiste, ne crea uno nuovo
     */
    private void open(){
        try {
            FileInputStream file = new FileInputStream("data");
            ObjectInputStream in = new ObjectInputStream(file);
            this.saveFile = (SaveFile) in.readObject();
            if(this.saveFile.getLives() == 0) {
                Settings.LOGGER.log(Level.INFO, "Save file found, but lives are 0, resetting");
                this.saveFile.setLives(GameConstraints.lives);
            }
            in.close();
            file.close();
        } catch (IOException e) {
            Settings.LOGGER.log(Level.INFO, "Save file not found, creating new one");
            this.saveFile = new SaveFile(
                    0, 0,
                    1, 1, 1, 1,
                    false,false,
                    0, 0,1,0,0,
                    0,4,
                    new boolean[]{true, false, false, false},
                    new boolean[]{true, false, false, false}, 0,0,
                    Language.languageList.ITA, Settings.resolutions[1]);
            save();
            Settings.LOGGER.log(Level.INFO, "New save file created");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSaveFile() {
        if (this.saveFile == null)
            this.open();
    }

    /** Metodo per salvare il file di salvataggio
     */
    public void save(){
        try {
            FileOutputStream file = new FileOutputStream("data");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this.saveFile);
            out.close();
            file.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setSaveFile(SaveFile saveFile) {
        this.saveFile = saveFile;
    }

    /** Metodo per salvare i dati di gioco
     */
    public void saveData(){
        this.saveFile.setGems(GameConstraints.getInstance().getGems());
        this.saveFile.setScore(GameConstraints.getInstance().getScore());
        this.saveFile.setCheckpoint(Game.getInstance().getStage());
        this.saveFile.setCurrentScore(Game.getInstance().getScore());
        this.saveFile.setCurrentGems(Game.getInstance().getGemCount());
        this.saveFile.setLanguage(Settings.language);
        if(Game.getInstance().getScore() > this.saveFile.getHighScore()) {
            this.saveFile.setHighScore(this.saveFile.getCurrentScore());
            GameConstraints.getInstance().setHighScore(this.saveFile.getHighScore());
        }
        this.saveFile.setLives(Game.getInstance().getLives());
        GameConstraints.getInstance().setCheckpoint(this.saveFile.getCheckpoint());
        for(int i = 0; i < 3; i++) {
            if (this.saveFile.getScore() >= GameConstraints.getInstance().getUnlockWeaponsScore(i))
                this.saveFile.setUnlockedWeapon(i + 1);
            if (this.saveFile.getScore() >= GameConstraints.getInstance().getUnlockEnginesScore(i))
                this.saveFile.setUnlockedEngine(i + 1);
        }
        save();
    }

    public int getSpeedLvl(){return this.saveFile.getSpeedLvl();}
    public int getBulletSpeedLvl(){return this.saveFile.getBulletSpeedLvl();}
    public int getBulletDmgLvl(){return this.saveFile.getBulletDmgLvl();}
    public int getBulletRatioLvl(){return this.saveFile.getBulletRatioLvl();}
    public void saveSpeedLvl(){this.saveFile.setSpeedLvl(GameConstraints.getInstance().getLvlMaxSpeed()); save();}
    public void saveBulletSpeedLvl(){this.saveFile.setBulletSpeedLvl(GameConstraints.getInstance().getLvlBulletSpeed()); save();}
    public void saveBulletDmgLvl(){this.saveFile.setBulletDmgLvl(GameConstraints.getInstance().getLvlBulletDamage()); save();}
    public void saveBulletRatioLvl(){this.saveFile.setBulletRatioLvl(GameConstraints.getInstance().getLvlBulletRatio()); save();}

    public int getGems(){return this.saveFile.getGems();}
    public int getScore() {return this.saveFile.getScore();}
    public int getCheckPoint() {return this.saveFile.getCheckpoint();}
    public int getCurrentScore() {return this.saveFile.getCurrentScore();}
    public int getCurrentGems() {return this.saveFile.getCurrentGems();}
    public void setEngine(int engine) {this.saveFile.setEngine(engine);}
    public int getEngine() {return this.saveFile.getEngine();}
    public int getWeapon() {return this.saveFile.getWeapon();}
    public void setWeapon(int weapon) {this.saveFile.setWeapon(weapon);}
    public void resetCurrent() {this.saveFile.setCurrentScore(0); this.saveFile.setCurrentGems(0); this.saveFile.setCheckpoint(1); save();}
    public int getHighScore() {return this.saveFile.getHighScore();}
    public int getLives() {return this.saveFile.getLives();}
    public void resetLives() {this.saveFile.setLives(GameConstraints.lives); save();}
    public boolean[] getUnlockedEngines() {return this.saveFile.getUnlockedEngines();}
    public boolean[] getUnlockedWeapons() {return this.saveFile.getUnlockedWeapons();}
    private boolean isEngineUnlocked(int index) {return getUnlockedEngines()[index];}
    private boolean isWeaponUnlocked(int index) {return getUnlockedWeapons()[index];}
    public void updateGems(){this.saveFile.setGems(GameConstraints.getInstance().getGems()); save();}
    public void setVolume(float volume) {this.saveFile.setVolume(volume); save();}
    public float getVolume() {return this.saveFile.getVolume();}
    public float getMusicVolume() {return this.saveFile.getMusicVolume();}
    public void setMusicVolume(float musicVolume) {this.saveFile.setMusicVolume(musicVolume); save();}
    public boolean shield() {return this.saveFile.shield();}
    public void setShield(boolean shield) {this.saveFile.setShield(shield); save();}
    public boolean speed() {return this.saveFile.speed();}
    public void setSpeed(boolean speed) {this.saveFile.setSpeed(speed); save();}
    public Language.languageList getLanguage(){return this.saveFile.getLanguage();}

    /** Metodo per salvare il motore selezionato <br>
     * Se il motore non è sbloccato, non viene salvato
     * @param index indice del motore
     */
    public boolean saveEngine(int index){
        if(isEngineUnlocked(index)) {
            setEngine(index);
            save();
            return true;
        }
        return false;
    }

    /** Metodo per salvare l'arma selezionata <br>
     * Se l'arma non è sbloccata, non viene salvata
     * @param index indice dell'arma
     */
    public boolean saveWeapon(int index){
        if(isWeaponUnlocked(index)) {
            setWeapon(index);
            save();
            return true;
        }
        return false;
    }

    /** Sblocca: motori, armi e fornisce molte gemme
     */
    public void cheatCode(){
        this.saveFile.setUnlockedWeapons(new boolean[]{true,true,true,true});
        this.saveFile.setUnlockedEngines(new boolean[]{true,true,true,true});
        GameConstraints.getInstance().setGems(999999);
        this.saveFile.setGems(GameConstraints.getInstance().getGems());
        save();
    }

    public String getResolution(){return this.saveFile.getResolution();}
    public void setResolution(String resolution){this.saveFile.setResolution(resolution); save();}
}
