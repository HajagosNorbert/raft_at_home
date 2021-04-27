package world.worldObject.craft;

import world.worldObject.supply.Resource;

import java.util.HashMap;
import java.util.Map;

public class Spear implements Craftable{

    public Map<Resource, Integer> getResourceCost() {
        var map = new HashMap<Resource, Integer>();
        map.put(Resource.WOOD, 4);
        map.put(Resource.LEAF, 4);
        map.put(Resource.DEBRIS, 4);
        return map;
    }
}
