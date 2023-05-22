package com.yaac.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP -> System.out.println("UP");
            case KeyEvent.VK_DOWN -> System.out.println("DOWN");
            case KeyEvent.VK_LEFT -> System.out.println("LEFT");
            case KeyEvent.VK_RIGHT -> System.out.println("RIGHT");
            case KeyEvent.VK_SPACE -> System.out.println("SPACE");
        }
    }
}
