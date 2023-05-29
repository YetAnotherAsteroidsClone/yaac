package com.yaac.view;

import com.yaac.Settings;
import com.yaac.view.Utility.CompositeSprite;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.ObjectAnimation;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/** Classe signleton per la gestione della grafica della navicella
 */
public class SpaceShipView {
    private Image body1,body2,body3,body4;
    private Image baseEngine;
    private CompositeSprite spaceship;
    private ObjectAnimation baseEnginePowering;

    public SpaceShipView(int width, int height) {
        body1 = ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/Body1.png"), width, height);
        body2 = ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/Body2.png"), width, height);
        body3 = ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/Body3.png"), width, height);
        body4 = ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/Body4.png"), width, height);
        baseEngine = ImageUtility.scaleImage(ImageUtility.loadImage("/GameSprite/BaseEngine.png"), width, height);
        baseEnginePowering = new ObjectAnimation("/GameSprite/BaseEngine-Powering.png");
        baseEnginePowering.scaleImage(width, height);

        spaceship = new CompositeSprite(
                new ArrayList<>(List.of(baseEnginePowering)),
                new ArrayList<>(List.of(baseEngine)),
                new ArrayList<>(List.of(body1)));
    }

    /** Ritorna la navicella
     * @return CompositeSprite navicella
     */
    public CompositeSprite getSpaceship() {
        return spaceship;
    }
}
