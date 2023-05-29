package com.yaac.view;

import com.yaac.view.Utility.CompositeSprite;
import com.yaac.view.Utility.ImageUtility;
import com.yaac.view.Utility.ObjectAnimation;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/** Classe signleton per la gestione della grafica della navicella
 */
public class SpaceShipView {

    private CompositeSprite spaceship;
    private final Image body1 = ImageUtility.loadImage("/GameSprite/Body1.png");
    private final Image body2 = ImageUtility.loadImage("/GameSprite/Body2.png");
    private final Image body3 = ImageUtility.loadImage("/GameSprite/Body3.png");
    private final Image body4 = ImageUtility.loadImage("/GameSprite/Body4.png");
    private final Image baseEngine = ImageUtility.loadImage("/GameSprite/BaseEngine.png");
    private ObjectAnimation baseEnginePowering = new ObjectAnimation("/GameSprite/BaseEngine-Powering.png");

    private static SpaceShipView instance = new SpaceShipView();

    public static SpaceShipView getInstance() {
        return instance;
    }

    private SpaceShipView() {
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
