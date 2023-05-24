package com.yaac;

import com.yaac.controller.GameController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameLoop {
    private GameController gameController;
    private ScheduledExecutorService executor;

    public GameLoop(GameController gameController){
        this.gameController = gameController;
    }

    void run() {
        if(executor != null)
            return;
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> gameController.update(), 0, 60, TimeUnit.MILLISECONDS);
    }


}
