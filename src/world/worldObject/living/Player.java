package world.worldObject.living;

import IOHandling.IOHandler;
import game.Game;
import helper.Direction;
import helper.Illustrations;
import helper.ImpossibleActionException;
import world.*;
import world.worldObject.build.*;
import world.worldObject.supply.Inventory;
import world.worldObject.supply.Resource;
import world.worldObject.supply.Supply;

import java.util.HashMap;
import java.util.Iterator;

public class Player extends Character implements Illustratable {

    private int foodFullnes;
    private final int maxFoodFullnes;
    private int drinkFullnes;
    private final int maxDrinkFullnes;
    private int defendableSharkAttackCount;
    private Inventory inventory;

    {
        maxDrinkFullnes = 100;
        maxFoodFullnes = 100;
    }

    public void sufferHunger() {
        foodFullnes--;
    }

    public String getCauseOfDeath() {
        if (foodFullnes <= 0) {
            return "starvation";
        }
        if (drinkFullnes <= 0) {
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
        this.foodFullnes = maxFoodFullnes;
        this.drinkFullnes = maxDrinkFullnes;
        this.defendableSharkAttackCount = 0;
//        this.inventory = new Inventory();
// for TESTING
        var map = new HashMap<Resource, Integer>();
        map.put(Resource.WOOD, 100);
        map.put(Resource.LEAF, 100);
        map.put(Resource.DEBRIS, 100);
        map.put(Resource.POTATO, 0);
        map.put(Resource.FISH, 0);
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

        throwExceptionIfOutOfWorld(newX, newY, map.height, map.width);

        setX(newX);
        setY(newY);
    }

    public void fish() {
        if (Math.random() <= 0.25) {
            inventory.add(Resource.FISH, 1);
        }
    }

    private void throwExceptionIfGatheringImpossible(int supplyPosX, int supplyPosY, Map map) throws ImpossibleActionException {

        throwExceptionIfOutOfWorld(supplyPosX, supplyPosY, map.height, map.width);

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

        inventory.add(supply, 1);

        ocean.setSupply(null);
    }

    private boolean canBuild(Building building) {
        java.util.Map<Resource, Integer> costs = building.getResourceCost();
        Iterator i = costs.keySet().iterator();
        while (i.hasNext()) {
            Resource resource = (Resource) i.next();
            if (inventory.getResourceAmount(resource) - costs.get(resource) < 0) return false;
        }
        return true;
    }

    public void build(Building building, Map map, Direction direction) throws ImpossibleActionException {
        if (!(map.getTile(getX(), getY()) instanceof Platform))
            throw new ImpossibleActionException("Can only build while on a platform");
        if (!canBuild(building)) throw new ImpossibleActionException("You need more resources!");

        int buildingPosX = getNewX(direction);
        int buildingPosY = getNewY(direction);

        throwExceptionIfOutOfWorld(buildingPosX, buildingPosY, map.height, map.width);


        if (building instanceof Platform) {
            placePlatform(buildingPosX, buildingPosY, map);
        }
        if (building instanceof PlatformBuilding platformBuilding) {
            placePlatfomBuilding(platformBuilding, buildingPosX, buildingPosY, map);
        }
        if (building instanceof OceanBuilding oceanBuilding) {
            placeOceanBuilding(oceanBuilding, buildingPosX, buildingPosY, map);
        }
        payBuildingCosts(building);
        return;
    }

    public void useBuilding(Map map) throws ImpossibleActionException {
        Building building = map.getTile(getX(), getY()).getBuilding();
        if (!(building instanceof PlatformBuilding))
            throw new ImpossibleActionException("No building here, you could use");

        PlatformBuilding platformBuilding = (PlatformBuilding) building;


        if (platformBuilding instanceof Fireplace fireplace && !fireplace.hasFoodInside) {
            placeFoodOnFireplace(fireplace);
            return;
        }
        consumeFromBuilding(platformBuilding);
    }
    private void consumeFromBuilding(PlatformBuilding platformBuilding) throws ImpossibleActionException{
        if (platformBuilding.actionsUntilConsumption() != 0)
            throw new ImpossibleActionException("Needs " + platformBuilding.actionsUntilConsumption() + " more actions until ready to consume");
        platformBuilding.consumeFromIt(this);
    }

    private void placeFoodOnFireplace(Fireplace fireplace) throws ImpossibleActionException {
        Resource food;
        try {
            food = chooseRawFood();
        } catch (ImpossibleActionException e){
            throw new ImpossibleActionException(e.getMessage()+" to place it on the fire.");
        }
        inventory.remove(food, 1);
        fireplace.startMakingConsumable();
        IOHandler.addMessage("Started cooking");
    }

    private Resource chooseRawFood() throws ImpossibleActionException {
        if (inventory.getResourceAmount(Resource.POTATO) > 0) return Resource.POTATO;
        if (inventory.getResourceAmount(Resource.FISH) > 0) return Resource.FISH;
        throw new ImpossibleActionException("You don't have any potato or fish");
    }


    public void drink(int amount) {
        drinkFullnes += amount;
        if (drinkFullnes > maxDrinkFullnes) drinkFullnes = maxDrinkFullnes;
    }

    public void eat(int amount) {
        foodFullnes += amount;
        if (foodFullnes > maxFoodFullnes) foodFullnes = maxFoodFullnes;
    }

    private void placeOceanBuilding(OceanBuilding building, int x, int y, Map map) throws ImpossibleActionException {
        if (map.getTile(x, y) instanceof Platform) throw new ImpossibleActionException("Place it on Ocean!");

        map.placeBuilding(building, x, y);
    }

    private void placePlatfomBuilding(PlatformBuilding building, int x, int y, Map map) throws ImpossibleActionException {
        if (map.getTile(x, y) instanceof Ocean) throw new ImpossibleActionException("Place it on a platform!");
        map.placeBuilding(building, x, y);
    }


    private void placePlatform(int x, int y, Map map) throws ImpossibleActionException {
        Tile tile = map.getTile(x, y);
        if (!(tile instanceof Ocean)) throw new ImpossibleActionException("Build on the ocean");

        map.placePlatform(x, y);
    }


    private void payBuildingCosts(Building building) {
        getInventory().remove(building.getResourceCost());
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
