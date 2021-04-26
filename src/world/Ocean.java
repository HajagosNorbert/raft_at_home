package world;

import helper.Illustrations;
import world.worldObject.Building;
import world.worldObject.supply.Supply;

public class Ocean implements Tile {
    private Supply supply;

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public Supply getSupply() {
        return supply;
    }

    @Override
    public String getIllustration() {
        return (supply != null)? supply.getIllustration(): Illustrations.getOceanIllustration();
    }

    @Override
    public Building getBuilding() {
        return null;
    }
}
