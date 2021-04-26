package world.worldObject.supply;

import helper.Illustrations;
import world.Illustratable;

import java.util.HashMap;
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


    public static Map<Resource, Integer> lootBarrel(){
        Map<Resource, Integer> resourceAmounts = new HashMap<>();
        //deszka, levél, hulladék, burgonya
        for (int i = 0; i < 4; i++){
            double r = Math.random();
            if(r < 0.25){
                resourceAmounts.merge(Resource.WOOD, 1, Integer::sum);
            } else if(r < 0.5){
                resourceAmounts.merge(Resource.LEAF, 1, Integer::sum);
            }
            else if(r < 0.75){
                resourceAmounts.merge(Resource.DEBRIS, 1, Integer::sum);
            }else if(r <= 1){
                resourceAmounts.merge(Resource.POTATO, 1, Integer::sum);
            }
        }
        return resourceAmounts;
    }

    public String getIllustration(){
        return illustration;
    }
}
