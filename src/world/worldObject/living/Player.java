package world.worldObject.living;

import helper.Direction;
import helper.Illustrations;
import helper.ImpossibleActionException;
import world.*;
import world.worldObject.craft.Craftable;
import world.worldObject.craft.Platform;
import world.worldObject.craft.Spear;
import world.worldObject.supply.Inventory;
import world.worldObject.supply.Resource;
import world.worldObject.supply.Supply;

import java.util.HashMap;
import java.util.Iterator;

public class Player extends Character implements Illustratable {

    private int foodFullnes;
    private int drinkFullnes;
    private int defendableSharkAttackCount;
    private Inventory inventory;


    public void sufferHunger() {
        foodFullnes--;
    }

    public String getCauseOfDeath() {
        if (foodFullnes < 0) {
            return "starvation";
        }
        if (drinkFullnes < 0) {
            return "dehydration";
        }
        if (defendableSharkAttackCount < 0) {
            return "being shark food";
        }
        return null;
    }

    public void sufferThirst() {
        drinkFullnes--;
    }

    public boolean isAlive() {
        return foodFullnes > 0 && drinkFullnes > 0;
    }

    public Player(int x, int y) {
        super(x, y);
        this.foodFullnes = 100;
        this.drinkFullnes = 100;
        this.defendableSharkAttackCount = 0;
//        this.inventory = new Inventory();
// for TESTING
        var map = new HashMap<Resource, Integer>();
        map.put(Resource.WOOD, 100);
        map.put(Resource.LEAF, 100);
        map.put(Resource.DEBRIS, 100);
        map.put(Resource.POTATO, 100);
        map.put(Resource.FISH, 100);
        this.inventory = new Inventory(map);
    }

    public Player(int x, int y, int foodFullnes, int drinkFullnes, int defendableSharkAttackCount, Inventory inventory) {
        super(x, y);
        this.foodFullnes = foodFullnes;
        this.drinkFullnes = drinkFullnes;
        this.defendableSharkAttackCount = defendableSharkAttackCount;
        this.inventory = inventory;
    }

    public void move(Map map, Direction direction) throws ImpossibleActionException {
        int newX = getNewX(direction);
        int newY = getNewY(direction);

        if (outOfWorld(newX, newY, map.height, map.width)) throw new ImpossibleActionException("Out of the world");

        setX(newX);
        setY(newY);
    }

    private int getNewX(Direction direction) {
        return getX() + direction.x;
    }

    private int getNewY(Direction direction) {
        return getY() + direction.y;
    }

    private boolean outOfWorld(int x, int y, int height, int width) {
        return y < 0 || y >= height || x < 0 || x >= width;
    }

    public void fish() {
        if (Math.random() <= 0.25) {
            inventory.add(Resource.FISH, 1);
        }
    }

    private void throwExceptionIfGatheringImpossible(int supplyPosX, int supplyPosY, Map map) throws ImpossibleActionException {

        if (outOfWorld(supplyPosX, supplyPosY, map.height, map.width))
            throw new ImpossibleActionException("Out of the world");
        Tile tile = map.getTile(supplyPosX, supplyPosY);
        if (!(tile instanceof Ocean)) throw new ImpossibleActionException("Only the ocean has supplies flowing");

        Supply supply = ((Ocean) tile).getSupply();
        if (supply == null) throw new ImpossibleActionException("There is no supply here");
    }

    public void gatherSupply(Map map, Direction direction) throws ImpossibleActionException {
        int supplyPosX = getX() + direction.x;
        int supplyPosY = getY() + direction.y;

        throwExceptionIfGatheringImpossible(supplyPosX, supplyPosY, map);

        Ocean ocean = (Ocean) map.getTile(supplyPosX, supplyPosY);
        Supply supply = ocean.getSupply();

        if (supply == Supply.BARREL) {
            inventory.add(Supply.lootBarrel());
        } else {
            inventory.add(supply, 1);
        }
        ocean.setSupply(null);
    }

    private boolean canCraft(Craftable craftable){
        java.util.Map<Resource, Integer> costs =  craftable.getResourceCost();
        Iterator i = costs.keySet().iterator();
        while (i.hasNext()){
            Resource resource = (Resource) i.next();
            if (inventory.getResourceAmounts().get(resource) - costs.get(resource) < 0)
                return false;
        }
        return true;
    }

    public void craft(Craftable craftable, Map map, Direction direction) throws ImpossibleActionException{
        if(!canCraft(craftable))
            throw new ImpossibleActionException("You need more resources!");

        if(craftable instanceof Spear){
            craftSpear((Spear) craftable);
            return;
        }

        int craftablePosX = getNewX(direction);
        int craftablePosY = getNewY(direction);

        if(outOfWorld(craftablePosX, craftablePosY, map.height, map.width))
            throw new ImpossibleActionException("Out of the world");


        if (craftable instanceof Platform) {
             placePlatform(craftablePosX, craftablePosY, map);
            return;
        }
    }

    private void craftSpear(Spear spear){

    }

    private void payCraftingCosts(Craftable craftable){
        getInventory().remove(craftable.getResourceCost());
    }

    private void placePlatform(int x, int y, Map map) throws ImpossibleActionException{
        Tile tile = map.getTile(x, y);
        if(!(tile instanceof Ocean))
            throw new ImpossibleActionException("Build on the ocean");

        payCraftingCosts(new Platform());
        map.placePlatform(x, y);
    }


    public Inventory getInventory() {
        return inventory;
    }

    public String getStatusIllustration() {
        return "Food: " + foodFullnes + " | Water: " + drinkFullnes + " | SharkAttacksToDie: " + defendableSharkAttackCount + 1;
    }

    public String getIllustration() {
        return Illustrations.getPlayerIllustration();
    }
}
