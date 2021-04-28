package world.worldObject.build;

import IOHandling.IOHandler;
import game.Game;
import helper.Illustrations;
import world.worldObject.living.Player;
import world.worldObject.supply.Resource;

import java.util.HashMap;
import java.util.Map;

public class Fireplace extends PlatformBuilding {
    public boolean hasFoodInside;


    {
        hasFoodInside = false;
        refilmentAmount = 60;
    }

    //HEEEEEEEEEEEEEEE
    public void startMakingConsumable() {
        makingStartedAt = Game.actionCount + 1;
        hasFoodInside = true;

    }


    @Override
    public void consumeFromIt(Player player) {
        player.eat(refilmentAmount);
        makingStartedAt = Game.actionCount + Game.maxNumberOfActions;
        hasFoodInside = false;

    }

    public Fireplace(Player player) {
        super();
        IOHandler.addMessage("You need to place food inside to start cooking. press [5] to do so.");
    }

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
