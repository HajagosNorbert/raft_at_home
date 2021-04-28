package world.worldObject.build;

import helper.Illustrations;
import world.Illustratable;
import world.worldObject.supply.Resource;

import java.util.HashMap;
import java.util.Map;

public class Purifier implements PlatformBuilding, Illustratable {

    private int roundsUntilWaterPurified;

    {
        roundsUntilWaterPurified = 25;
    }

    public boolean isPurificationComplete(){
         return roundsUntilWaterPurified <= 0;
    }

    public Map<Resource, Integer> getResourceCost() {
        var map = new HashMap<Resource, Integer>();
        map.put(Resource.LEAF, 2);
        map.put(Resource.DEBRIS, 4);
        return map;
    }

    public void purify(){
        roundsUntilWaterPurified--;
    }

    @Override
    public String getIllustration() {
        return Illustrations.getPurifierIllustration();
    }
}
