package world.worldObject.supply;

import helper.Illustrations;

public enum Resource {
    WOOD("wood", Illustrations.getWoodIllustration()),
    LEAF("leaf", Illustrations.getLeafIllustration()),
    DEBRIS("debris", Illustrations.getDebrisIllustration()),
    POTATO("potato", Illustrations.getPotatoIllustration()),
    FISH("fish", Illustrations.getFishIllustration());

    private String name;
    private String illustration;

    Resource(String name, String illustration){
        this.name = name;
        this.illustration = illustration;
    }

    public String getIllustration() {
        return illustration;
    }

    public String getName() {
        return name;
    }

}