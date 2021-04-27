package world.worldObject.craft;

import helper.Illustrations;
import world.Tile;
import world.worldObject.craft.Craftable;
import world.worldObject.supply.Resource;

import java.util.HashMap;
import java.util.Map;

public class Platform implements Tile, Craftable{
    @Override
    public String getIllustration() {
        return Illustrations.getPlatformIllustration();
    }

    @Override
    public Craftable getBuilding() {
        return null;
    }

    public Map<Resource, Integer> getResourceCost() {
        var map = new HashMap<Resource, Integer>();
        map.put(Resource.WOOD, 2);
        map.put(Resource.LEAF, 2);
        return map;
    }
}
