package com.yaac.model.Utility;

import com.yaac.model.GameComponent.GameObject;

/**
 * Classe delle utility per il calcolo delle collisioni
 */
@SuppressWarnings("unused")
public class CollisionUtility {
    /**
     * Metodo per la collisione tra due oggetti
     * @param obj1 primo oggetto
     * @param obj2 secondo oggetto
     * @return true se i due oggetti collidono, false altrimenti
     */
    public static boolean checkCollision(GameObject obj1, GameObject obj2){
        double r1 = obj1.getRadius();
        double r2 = obj2.getRadius();

        return distance(obj1, obj2) <= (r1+r2);
    }

    /**
     * Metodo per la collisione tra un oggetto e un array di oggetti
     * @param obj1 primo oggetto
     * @param obj2 array di oggetti
     * @return true se il primo oggetto collide con almeno un elemento del array, false altrimenti
     */
    public static boolean bCheckCollision(GameObject obj1, GameComponentsManager obj2){
        for(GameObject obj : obj2){
            if(checkCollision(obj1, obj) && obj1 != obj)
                return true;
        }
        return false;
    }

    /**
     * Metodo per la collisione tra un oggetto e un array di oggetti
     * @param obj1 primo oggetto
     * @param obj2 GameComponentManager di elementi da controllare
     * @return array di oggetti con cui collide il primo oggetto
     */
    public static GameComponentsManager checkCollisionArrayArray(GameComponentsManager obj1, GameComponentsManager obj2){
        GameComponentsManager collisionList = new GameComponentsManager();
        for(GameObject obj : obj2){
            if(bCheckCollision(obj, obj1))
                collisionList.add(obj);
        }
        return collisionList;
    }

    /**
     * Metodo per la collisione tra un oggetto e un array di oggetti
     * @param obj1 primo oggetto
     * @param obj2 GameComponentManager di elementi da controllare
     * @return array di oggetti con cui collide il primo oggetto
     */
    public static GameComponentsManager checkCollisionElementArray(GameObject obj1, GameComponentsManager obj2){
        GameComponentsManager collisionList = new GameComponentsManager();
        for(GameObject obj : obj2){
            if(checkCollision(obj1, obj))
                collisionList.add(obj);
        }
        return collisionList;
    }

    /**
     * Metodo per la distanza tra due oggetti
     * @param obj1 primo oggetto
     * @param obj2 secondo oggetto
     * @return distanza tra i due oggetti
     */
    public static double distance(GameObject obj1, GameObject obj2){
        double x1 = obj1.getX();
        double y1 = obj1.getY();
        double x2 = obj2.getX();
        double y2 = obj2.getY();

        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    /**
     * Metodo per la distanza tra un oggetto e un array di oggetti
     * @param obj1 primo oggetto
     * @param obj2 array di oggetti
     * @return distanza tra il primo oggetto e l'oggetto più vicino
     */
    public static double distanceArray(GameObject obj1, GameComponentsManager obj2){
        double min = Double.MAX_VALUE;
        for(GameObject obj : obj2){
            double dist = distance(obj1, obj);
            if(dist < min)
                min = dist;
        }
        return min;
    }
}
