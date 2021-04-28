package world.worldObject.build;

import helper.Illustrations;
import world.worldObject.living.Player;
import world.worldObject.supply.Inventory;
import world.worldObject.supply.Resource;
import world.worldObject.supply.Supply;

import java.util.HashMap;
import java.util.Map;

public class Net implements OceanBuilding{
    private Inventory linkedInventory;

    @Override
    public Map<Resource, Integer> getResourceCost() {
        var map = new HashMap<Resource, Integer>();
        map.put(Resource.WOOD, 2);
        map.put(Resource.LEAF, 6);
        return map;
    }

    public Net(Player player){
        this.linkedInventory = player.getInventory();
    }

    public void catchSupply(Supply supply){
        linkedInventory.add(supply, 1);
    }

    @Override
    public String getIllustration() {
        return Illustrations.getNetIllustration();
    }
}
