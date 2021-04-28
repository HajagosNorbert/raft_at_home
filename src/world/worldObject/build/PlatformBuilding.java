package world.worldObject.build;

import game.Game;
import world.worldObject.living.Player;

public abstract class PlatformBuilding implements Building{

    protected int makingStartedAt;
    protected int timeToMake;
    protected int refilmentAmount;

    {
        timeToMake = 5;
    }

    public int actionsUntilConsumption (){
        return Math.max(0, timeToMake- (Game.actionCount - makingStartedAt));
    }

    public abstract void consumeFromIt(Player player);

}
