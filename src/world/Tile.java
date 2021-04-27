package world;

import world.worldObject.craft.Craftable;

public interface Tile extends Illustratable {
    public Craftable getBuilding();
}
