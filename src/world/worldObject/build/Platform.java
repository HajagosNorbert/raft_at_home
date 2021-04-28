package world.worldObject.build;

import helper.Illustrations;
import world.Tile;
import world.worldObject.supply.Resource;

import java.util.HashMap;
import java.util.Map;

public class Platform extends Tile implements Building {

    public Map<Resource, Integer> getResourceCost() {
        var map = new HashMap<Resource, Integer>();
        map.put(Resource.WOOD, 2);
        map.put(Resource.LEAF, 2);
        return map;
    }

    @Override
    public String getIllustration() {
        if (getBuilding() != null)
            return getBuilding().getIllustration();
        return Illustrations.getPlatformIllustration();
    }
}
