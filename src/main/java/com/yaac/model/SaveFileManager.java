package com.yaac.model;

import com.yaac.model.Utility.SaveFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveFileManager {

    private static SaveFileManager instance = new SaveFileManager();

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
            this.saveFile = new SaveFile(0, 0, 1, 1, 1, 1, false, 0, 0,1);
            save();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSaveFile() {
        if (this.saveFile == null)
            this.open();
    }

    private void save(){
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
        this.saveFile.setGems(Game.getInstance().getGemCount());
        this.saveFile.setScore(this.saveFile.getScore()+ Game.getInstance().getScore());
        this.saveFile.setCheckpoint(GameConstraints.getInstance().getCheckpoint());
        GameConstraints.getInstance().setGems(this.saveFile.getGems());
        GameConstraints.getInstance().setScore(this.saveFile.getScore());
        save();

    }

    public int getGems(){return this.saveFile.getGems();}

    public int getScore() {return this.saveFile.getScore();}
    public int getCheckPoint() {return this.saveFile.getCheckpoint();}
}
