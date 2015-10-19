/*
 * TCSS 305 � Autumn 2014 
 * Assignment 3 - EasyStreet
 */

package model;

import java.util.Map;

/**
 * This program stores information about a Human object to be used in EasyStreetGUI.
 * 
 * @author Trung Dang
 * @version OCtober 16, 2014
 */
public class Human extends AbstractVehicle {

    /** The default death time before a Human revives.*/
    private static final int HUMAN_DEATH_TIME = 50;

    /** A instance field to hold the initial terrain a Human is in.*/
    private final Terrain myInitialTerrain;

    /**
     * This constructor passes information about a Truck object to the superclass Abstract-
     * Vehicle.
     * 
     * @param theX The initial X coordinate of a Human
     * @param theY The initial Y coordinate of a Human
     * @param theDir The initial direction of a Human
     * @param theInitialTerrain The starting terrain a Human is in
     */
    public Human(final int theX, 
                 final int theY, 
                 final Direction theDir, 
                 final Terrain theInitialTerrain) {
        super(theX, theY, theDir, HUMAN_DEATH_TIME);
        myInitialTerrain = theInitialTerrain;
    }
    /** 
     * This method decides whether a Human can pass through the given terrain. A Human travels 
     * on terrains that match his/her initial terrain. Human ignores traffic lights.
     * 
     * @param theTerrain One of the terrain neighbors this Human
     * @param theLight A light neighbors this Human
     * @return true if a Human can pass through theTerrain.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        if ((myInitialTerrain == Terrain.STREET || myInitialTerrain == Terrain.LIGHT) 
                        && (theTerrain == Terrain.STREET || theTerrain == Terrain.LIGHT)) {
            result = true;
        } else if (myInitialTerrain == theTerrain) {
            result = true;
        } 
        return result;
    }
    /**
     * This method checks all the neighboring terrain to determine the preferred direction of
     * a Human object.
     * 
     * @param theNeighbors A map that contains information about neighboring terrain with 
     * <br> the following form: <Direction, Terrain>
     * @return A preferred direction of this Human object.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction result = null;
        boolean error = true;
        while (error) {
            result = Direction.random();
            if (theNeighbors.get(result).equals(theNeighbors.get(Direction.CENTER))) {
                error = false;
            } else {
                error = true;
            }
        }
        return result;
    }
}
