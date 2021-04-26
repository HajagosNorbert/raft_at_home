package world.worldObject.supply;

import helper.Illustrations;

public enum Resource {
    WOOD(Illustrations.getWoodIllustration()),
    LEAF(Illustrations.getLeafIllustration()),
    DEBRIS(Illustrations.getDebrisIllustration()),
    BARREL(Illustrations.getBarrelIllustration()),
    POTATO(Illustrations.getPotatoIllustration()),
    FISH(Illustrations.getFishIllustration());

    private String illustration;

    Resource(String illustration){
        this.illustration = illustration;
    }

    public String getIllustration() {
        return illustration;
    }

}