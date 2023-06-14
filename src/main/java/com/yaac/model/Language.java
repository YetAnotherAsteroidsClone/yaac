package com.yaac.model;
import com.yaac.Settings;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings({"InstantiationOfUtilityClass", "unused"})
public class Language {
    public enum languageList {ITA, ENG, SPA, FRA, JAP ,CAL}
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
                case ITA -> in = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Language.class.getResourceAsStream("/Languages/ITALIANO.txt"))));
                case ENG -> in = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Language.class.getResourceAsStream("/Languages/ENGLISH.txt"))));
                case SPA -> in = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Language.class.getResourceAsStream("/Languages/SPANISH.txt"))));
                case FRA -> in = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Language.class.getResourceAsStream("/Languages/FRENCH.txt"))));
                case JAP -> in = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Language.class.getResourceAsStream("/Languages/JAPANESE.txt"))));
                case CAL -> in = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Language.class.getResourceAsStream("/Languages/CALABRESE.txt"))));
            }
            while(in.ready()){allStrings.add(in.readLine());}
        }
        catch (IOException e) {throw new RuntimeException(e);}
    }

    private static final Language instance = new Language();
    public static Language getInstance(){return instance;}
    public static void setLanguage(languageList newLanguage) {
        Settings.language = newLanguage;
        readLanguage(newLanguage);
        SaveFileManager.getInstance().saveData();
    }
}
