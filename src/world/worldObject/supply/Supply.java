package world.worldObject.supply;

import helper.Illustrations;
import world.Illustratable;

import java.util.Map;

public enum Supply implements Illustratable {
    WOOD( Illustrations.getWoodIllustration()),
    LEAF( Illustrations.getLeafIllustration()),
    DEBRIS( Illustrations.getDebrisIllustration()),
    BARREL( Illustrations.getBarrelIllustration());

    private String illustration;

    Supply( String illustration){
        this.illustration = illustration;
    }


    public String getIllustration(){
        return illustration;
    }
}
