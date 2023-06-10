package com.yaac.model;

import com.yaac.model.Utility.SaveFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
            in.close();
            file.close();
        } catch (IOException e) {
            this.saveFile = new SaveFile(0, 0, 1, 1, 1, 1, false,false, 0, 0,1,0,0,4, new boolean[]{true, false, false, false}, new boolean[]{true, false, false, false});
            save();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSaveFile() {
        if (this.saveFile == null)
            this.open();
    }

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

    public void saveData(){
        this.saveFile.setGems(this.saveFile.getGems() + Game.getInstance().getGemCount());
        this.saveFile.setScore(this.saveFile.getScore()+ Game.getInstance().getScore());
        this.saveFile.setCheckpoint(Game.getInstance().getStage());
        this.saveFile.setCurrentScore(Game.getInstance().getScore());
        if(this.saveFile.getCurrentScore() > this.saveFile.getHighScore()) {
            this.saveFile.setHighScore(this.saveFile.getCurrentScore());
            GameConstraints.getInstance().setHighScore(this.saveFile.getHighScore());
        }
        this.saveFile.setLives(Game.getInstance().getLives());
        GameConstraints.getInstance().setLife(this.saveFile.getLives());
        GameConstraints.getInstance().setGems(this.saveFile.getGems());
        GameConstraints.getInstance().setScore(this.saveFile.getScore());
        GameConstraints.getInstance().setCheckpoint(this.saveFile.getCheckpoint());
        save();
    }

    public int getGems(){return this.saveFile.getGems();}
    public int getScore() {return this.saveFile.getScore();}
    public int getCheckPoint() {return this.saveFile.getCheckpoint();}
    public int getCurrentScore() {return this.saveFile.getCurrentScore();}

    public void setEngine(int engine) {this.saveFile.setEngine(engine);}
    public int getEngine() {return this.saveFile.getEngine();}
    public int getWeapon() {return this.saveFile.getWeapon();}
    public void setWeapon(int weapon) {this.saveFile.setWeapon(weapon);}
    public void setCurrentScore(int currentScore) {this.saveFile.setCurrentScore(currentScore);}
    public int getHighScore() {return this.saveFile.getHighScore();}
    public int getLives() {return this.saveFile.getLives();}
    public void resetLives() {this.saveFile.setLives(GameConstraints.lives); save();}
    public boolean[] getUnlockedEngines() {return this.saveFile.getUnlockedEngines();}
    public boolean[] getUnlockedWeapons() {return this.saveFile.getUnlockedWeapons();}
    private boolean isEngineUnlocked(int index) {return getUnlockedEngines()[index];}
    private boolean isWeaponUnlocked(int index) {return getUnlockedWeapons()[index];}

    /** Metodo per salvare il motore selezionato <br>
     * Se il motore non è sbloccato, non viene salvato
     * @param index
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
     * @param index
     */
    public boolean saveWeapon(int index){
        if(isWeaponUnlocked(index)) {
            setWeapon(index);
            save();
        }
        return false;
    }

    public void unlockEngine(int index) {
        this.saveFile.setUnlockedEngine(index);
        save();
    }

    public void unlockWeapon(int index) {
        this.saveFile.setUnlockedWeapon(index);
        save();
    }
}
