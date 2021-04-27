package world.worldObject.craft;

import world.worldObject.supply.Resource;

import java.util.Map;

public interface Craftable {
    public Map<Resource, Integer> getResourceCost();

}
