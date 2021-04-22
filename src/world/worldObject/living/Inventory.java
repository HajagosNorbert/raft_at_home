package world.worldObject.living;

import helper.Illustrations;

public class Inventory {
    private int woodAmount;
    private int leafAmount;
    private int debrisAmount;
    private int potatoAmount;
    private int fishAmount;


    public Inventory() {
        woodAmount = 0;
        leafAmount = 0;
        debrisAmount = 0;
        potatoAmount = 0;
        fishAmount = 0;
    }

    public Inventory(int woodAmount, int leafAmount, int debrisAmount, int potatoAmount, int fishAmount) {
        this.woodAmount = woodAmount;
        this.leafAmount = leafAmount;
        this.debrisAmount = debrisAmount;
        this.potatoAmount = potatoAmount;
        this.fishAmount = fishAmount;
    }

    public String getIllustration(){
        String inventroyIllustration = Illustrations.getWoodIllustration() +": "+getWoodAmount()+ " | ";
        inventroyIllustration += Illustrations.getLeafIllustration() +": "+getLeafAmount()+ " | ";
        inventroyIllustration += Illustrations.getDebrisIllustration() +": "+getDebrisAmount()+ " | ";
        inventroyIllustration += Illustrations.getPotatoIllustration() +": "+getPotatoAmount()+ " | ";
        inventroyIllustration += Illustrations.getFishIllustration() +": "+getFishAmount();
        return inventroyIllustration;
    }

    public int getWoodAmount() {
        return woodAmount;
    }

    public int getLeafAmount() {
        return leafAmount;
    }

    public int getDebrisAmount() {
        return debrisAmount;
    }

    public int getPotatoAmount() {
        return potatoAmount;
    }

    public int getFishAmount() {
        return fishAmount;
    }
}
