package com.yaac;

import com.yaac.controller.Updatable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Classe che gestisce il loop di scena
 */
public class Loop {
    private final Updatable controller;
    private ScheduledExecutorService executor;

    public Loop(Updatable controller){
        this.controller = controller;
    }

    public void run() {
        if(executor != null)
            return;
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if(!controller.update())
                executor.shutdown();
        }, 0, 60, TimeUnit.MILLISECONDS);
    }


}
