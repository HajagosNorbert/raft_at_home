package world;

import helper.Illustrations;
import world.worldObject.supply.Supply;

/**
 * an ocean tile
 */
public class Ocean extends Tile {
    private Supply supply;

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public Supply getSupply() {
        return supply;
    }

    @Override
    public String getIllustration() {
        if (getBuilding() != null) return getBuilding().getIllustration();
        if (supply != null) return supply.getIllustration();
        return Illustrations.getOceanIllustration();
    }

}
