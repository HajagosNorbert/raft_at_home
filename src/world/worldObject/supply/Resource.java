package world.worldObject.supply;

import helper.Illustrations;

/**
 * Everything the player can have in it's inventory
 */
public enum Resource {
    WOOD(Illustrations.getWoodIllustration()),
    LEAF(Illustrations.getLeafIllustration()),
    DEBRIS(Illustrations.getDebrisIllustration()),
    POTATO(Illustrations.getPotatoIllustration()),
    FISH(Illustrations.getFishIllustration());

    private final String illustration;

    Resource(String illustration) {
        this.illustration = illustration;
    }

    public String getIllustration() {
        return illustration;
    }

}