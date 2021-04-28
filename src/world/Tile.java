package world;

import world.worldObject.build.Building;

public abstract class Tile implements Illustratable {
    private Building building;
    public Building getBuilding(){
        return building;
    }

    public void setBuilding(Building building){
        this.building = building;
    }
}
