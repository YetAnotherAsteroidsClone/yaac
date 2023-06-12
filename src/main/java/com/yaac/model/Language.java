package com.yaac.model;
import com.yaac.Main;
import com.yaac.Settings;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Language {
    public enum languageList {ITA, ENG}
    public static ArrayList<String> allStrings;

    private Language(){
        allStrings = new ArrayList<>();
        readLanguage(Settings.language);
    }

    public static void readLanguage(languageList l){
        if(!allStrings.isEmpty()){allStrings.clear();}
        try {
            BufferedReader in = null;
            switch (l){
                case ITA -> {in = new BufferedReader(new FileReader(new File(Main.class.getResource("/Languages/ITALIANO.txt").toURI())));}
                case ENG -> {in = new BufferedReader(new FileReader(new File(Main.class.getResource("/Languages/ENGLISH.txt").toURI())));}
            }
            while(in.ready()){allStrings.add(in.readLine());}
        }
        catch (IOException e) {throw new RuntimeException(e);} catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static Language instance = new Language();
    public static Language getInstance(){return instance;}
    public static void setLanguage(languageList newLanguage) {
        Settings.language = newLanguage;
        readLanguage(newLanguage);
    }
}
