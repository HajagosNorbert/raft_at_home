package world.worldObject.build;

import world.Illustratable;
import world.worldObject.supply.Resource;

import java.util.Map;

/**
 * interface for buildings with a cost
 */
public interface Building extends Illustratable {
    Map<Resource, Integer> getResourceCost();
}
