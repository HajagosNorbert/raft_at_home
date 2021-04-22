package world;

import helper.Illustrations;
import world.worldObject.Building;

public class Platform implements Tile{
    @Override
    public String getIllustration() {
        return Illustrations.getPlatformIllustration();
    }

    @Override
    public Building getBuilding() {
        return null;
    }
}
