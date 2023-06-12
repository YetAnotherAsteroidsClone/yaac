package com.yaac.model;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Language {
    public enum languageList {ITA, ENG}
    private static languageList language=languageList.ITA;
    public static ArrayList<String> allStrings;

    private Language(){readLanguage(language);}

    public static void readLanguage(languageList l){
        if(!allStrings.isEmpty()){allStrings.clear();}
        try {
            BufferedReader in = null;
            switch (l){
                case ITA -> {in = new BufferedReader(new FileReader("/Languages/ITALIANO"));}
                case ENG -> {in = new BufferedReader(new FileReader("/Languages/ENGLISH"));}
            }
            while(in.ready()){allStrings.add(in.readLine());}
        }
        catch (IOException e) {throw new RuntimeException(e);}
    }

    private static Language instance = new Language();
    public static Language getInstance(){return instance;}

    public static languageList getLanguage() {return language;}
    public static void setLanguage(languageList languageList) {language = languageList; readLanguage(language);}
}
