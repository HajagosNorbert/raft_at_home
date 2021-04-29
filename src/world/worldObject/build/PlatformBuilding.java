package world.worldObject.build;

import game.Game;
import world.worldObject.living.Player;

/**
 * every building that is on a platform
 */
public abstract class PlatformBuilding implements Building {

    protected int makingStartedAt;
    protected int timeToMake;
    protected int refillmentAmount;

    {
        timeToMake = 25;
    }

    /**
     * countdown until the player can use the building
     * @return
     */
    public int actionsUntilConsumption() {
        return Math.max(0, timeToMake - (Game.actionCount - makingStartedAt));
    }

    /**
     * the player consumes it's content
     * @param player
     */
    public abstract void consumeFromIt(Player player);

}
