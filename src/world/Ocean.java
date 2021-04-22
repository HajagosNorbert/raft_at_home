package world;

import helper.Illustrations;
import world.worldObject.Building;

public class Ocean implements Tile {
    @Override
    public String getIllustration() {
        return Illustrations.getOceanIllustration();
    }

    @Override
    public Building getBuilding() {
        return null;
    }
}
