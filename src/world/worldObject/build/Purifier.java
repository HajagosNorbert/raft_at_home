package world.worldObject.build;

import game.Game;
import helper.Illustrations;
import world.worldObject.living.Player;
import world.worldObject.supply.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * makes fresh water
 */
public class Purifier extends PlatformBuilding {

    {
        refillmentAmount = 40;
        startMakingConsumable();
    }

    /**
     * makes the player drink from it
     * @param player
     */
    public void consumeFromIt(Player player) {
        player.drink(refillmentAmount);
        startMakingConsumable();
    }

    /**
     * starts the countdown
     */
    public void startMakingConsumable() {
        makingStartedAt = Game.actionCount + 1;
    }

    /**
     *
     * @return the cost of building it
     */
    public Map<Resource, Integer> getResourceCost() {
        var map = new HashMap<Resource, Integer>();
        map.put(Resource.LEAF, 2);
        map.put(Resource.DEBRIS, 4);
        return map;
    }

    @Override
    public String getIllustration() {
        return Illustrations.getPurifierIllustration();
    }
}
