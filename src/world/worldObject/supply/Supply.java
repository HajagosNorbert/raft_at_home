package world.worldObject.supply;

import helper.Illustrations;
import world.Illustratable;

import java.util.Map;

public enum Supply implements Illustratable {
    WOOD("wood", Illustrations.getWoodIllustration()),
    LEAF("leaf", Illustrations.getLeafIllustration()),
    DEBRIS("debris", Illustrations.getDebrisIllustration()),
    BARREL("barrel", Illustrations.getBarrelIllustration());



    private String name;
    private String illustration;

    Supply(String name, String illustration){
        this.name = name;
        this.illustration = illustration;
    }

    public String getName() {
        return name;
    }

    public String getIllustration(){
        return illustration;
    }
}
