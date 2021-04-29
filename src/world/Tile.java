package world;

import world.worldObject.build.Building;

/**
 * a generic tile which will be inherited
 */
public abstract class Tile implements Illustratable {
    private Building building;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
