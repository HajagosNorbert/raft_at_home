package world.worldObject.build;

import helper.Illustrations;
import world.worldObject.living.Player;
import world.worldObject.supply.Inventory;
import world.worldObject.supply.Resource;
import world.worldObject.supply.Supply;

import java.util.HashMap;
import java.util.Map;

/**
 * catches supplies on the flow
 */
public class Net implements OceanBuilding {
    private final Inventory linkedInventory;

    /**
     *
     * @return the cost of the net
     */
    @Override
    public Map<Resource, Integer> getResourceCost() {
        var map = new HashMap<Resource, Integer>();
        map.put(Resource.WOOD, 2);
        map.put(Resource.LEAF, 6);
        return map;
    }

    /**
     * Links the player's invetóntory to know where to put the caught supply
     * @param player
     */
    public Net(Player player) {
        this.linkedInventory = player.getInventory();
    }

    /**
     * catches the supply and ads it to the player
     * @param supply
     */
    public void catchSupply(Supply supply) {
        linkedInventory.add(supply, 1);
    }

    @Override
    public String getIllustration() {
        return Illustrations.getNetIllustration();
    }
}
