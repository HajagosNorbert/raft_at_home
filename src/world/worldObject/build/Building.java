package world.worldObject.build;

import world.Illustratable;
import world.worldObject.living.Player;
import world.worldObject.supply.Resource;

import java.util.Map;

public interface Building extends Illustratable {
    Map<Resource, Integer> getResourceCost();

}
