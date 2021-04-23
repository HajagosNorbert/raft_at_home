package world.worldObject.supply;

import world.Illustratable;

import java.util.Map;

public class Supply extends Illustratable {
    //LEEGYSZERŰSÍTENI A SOK KÜLÖNBÖZ SUPPLY-T

    Map<Resource, Integer> resourceAmounts;
    static String illustration;
    public Supply(Map<Resource, Integer> resourceAmounts, String illustration){
        this.resourceAmounts = resourceAmounts;
        this.illustration = getIllustration();
    }

    public Map<Resource, Integer> getResourceAmounts(){

    }
    public String getIllustration(){
        return "a";
    }
}
