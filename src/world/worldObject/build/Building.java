package world.worldObject.build;

import world.Illustratable;
import world.worldObject.supply.Resource;

import java.util.Map;

public interface Building extends Illustratable {
    public Map<Resource, Integer> getResourceCost();
}
