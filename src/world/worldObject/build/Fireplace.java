package world.worldObject.build;

import helper.Illustrations;
import world.worldObject.supply.Resource;

import java.util.HashMap;
import java.util.Map;

public class Fireplace implements PlatformBuilding{

    @Override
    public Map<Resource, Integer> getResourceCost() {
        var map = new HashMap<Resource, Integer>();
        map.put(Resource.WOOD, 2);
        map.put(Resource.LEAF, 4);
        map.put(Resource.DEBRIS, 3);
        return map;
    }

    @Override
    public String getIllustration() {
        return Illustrations.getFireplaceIllustration();
    }
}
