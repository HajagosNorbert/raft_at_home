package world.worldObject.build;

import IOHandling.IOHandler;
import game.Game;
import helper.Illustrations;
import world.worldObject.living.Player;
import world.worldObject.supply.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * can cook food on it
 */
public class Fireplace extends PlatformBuilding {
    public boolean hasFoodInside;

    {
        hasFoodInside = false;
        refillmentAmount = 60;
    }

    /**
     * marks the time when cooking started
     */
    public void startMakingConsumable() {
        makingStartedAt = Game.actionCount + 1;
        hasFoodInside = true;
    }

    /**
     * makes the player consume it's production
     * @param player
     */
    @Override
    public void consumeFromIt(Player player) {
        player.eat(refillmentAmount);
        makingStartedAt = Game.actionCount + Game.maxNumberOfActions;
        hasFoodInside = false;
    }

    /**
     * initializes the object with a message to use it later
     */
    public Fireplace() {
        super();
        IOHandler.addMessage("You need to place food inside to start cooking. press [5] to do so.");
    }

    /**
     *
     * @return the cost of the item
     */
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
