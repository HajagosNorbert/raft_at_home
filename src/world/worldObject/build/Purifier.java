package world.worldObject.build;

import game.Game;
import helper.Illustrations;
import world.Illustratable;
import world.worldObject.living.Player;
import world.worldObject.supply.Resource;

import java.util.HashMap;
import java.util.Map;

public class Purifier extends PlatformBuilding {

    {
        refilmentAmount = 40;
        startMakingConsumable();
    }

    public void consumeFromIt(Player player){
        player.drink(refilmentAmount);
        startMakingConsumable();
    }

    public void startMakingConsumable(){
        makingStartedAt = Game.actionCount+1;
    }

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
