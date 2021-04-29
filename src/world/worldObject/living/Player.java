package world.worldObject.living;

import IOHandling.IOHandler;
import helper.Direction;
import helper.Illustrations;
import helper.ImpossibleActionException;
import world.*;
import world.worldObject.build.*;
import world.worldObject.supply.Inventory;
import world.worldObject.supply.Resource;
import world.worldObject.supply.Supply;

/**
 * the player controls this class' object
 */
public class Player extends Character implements Illustratable {

    private final int defendableSharkAttackCount;
    private final int maxFoodFullness;
    private final int maxDrinkFullness;

    private int foodFullness;
    private int drinkFullness;
    private final Inventory inventory;

    {
        maxDrinkFullness = 100;
        maxFoodFullness = 100;
    }

    /**
     * gets more hungry
     */
    public void sufferHunger() {
        foodFullness--;
    }

    /**
     * gets why the lpayer died based on the players parameters
     * @return
     */
    public String getCauseOfDeath() {
        if (foodFullness <= 0) {
            return "starvation";
        }
        if (drinkFullness <= 0) {
            return "dehydration";
        }
        if (defendableSharkAttackCount < 0) {
            return "being shark food";
        }
        return null;
    }

    /**
     * gets more thirsty
     */
    public void sufferThirst() {
        drinkFullness--;
    }

    /**
     *
     * @return true of hasn't died yet from starving or dehídration
     */
    public boolean isAlive() {
        return foodFullness > 0 && drinkFullness > 0;
    }

    /**
     * creates a player with base params
     * @param x
     * @param y
     */
    public Player(int x, int y) {
        super(x, y);
        this.foodFullness = maxFoodFullness;
        this.drinkFullness = maxDrinkFullness;
        this.defendableSharkAttackCount = 0;
        this.inventory = new Inventory();
    }

    /**
     * creaes a specific player object
     * @param x
     * @param y
     * @param foodFullness
     * @param drinkFullness
     * @param defendableSharkAttackCount
     * @param inventory
     */
    public Player(int x, int y, int foodFullness, int drinkFullness, int defendableSharkAttackCount, Inventory inventory) {
        super(x, y);
        this.foodFullness = foodFullness;
        this.drinkFullness = drinkFullness;
        this.defendableSharkAttackCount = defendableSharkAttackCount;
        this.inventory = inventory;
    }

    /**
     * moves the player to the specified location
     * @param map
     * @param direction
     * @throws ImpossibleActionException if player tries to go out of map
     */
    public void move(Map map, Direction direction) throws ImpossibleActionException {
        int newX = getNewX(direction);
        int newY = getNewY(direction);

        throwExceptionIfOutOfWorld(newX, newY, map.height, map.width);

        setX(newX);
        setY(newY);
    }

    /**
     * 25 % to catch a fish
     */
    public void fish() {
        if (Math.random() <= 0.25) {
            inventory.add(Resource.FISH, 1);
        }
    }

    /**
     *
     * @param supplyPosX
     * @param supplyPosY
     * @param map
     * @throws ImpossibleActionException if you trie to gather from outOfThe world or from a platform
     */
    private void throwExceptionIfGatheringImpossible(int supplyPosX, int supplyPosY, Map map) throws ImpossibleActionException {

        throwExceptionIfOutOfWorld(supplyPosX, supplyPosY, map.height, map.width);

        Tile tile = map.getTile(supplyPosX, supplyPosY);
        if (!(tile instanceof Ocean)) throw new ImpossibleActionException("Only the ocean has supplies flowing");

        Supply supply = ((Ocean) tile).getSupply();
        if (supply == null) throw new ImpossibleActionException("There is no supply here");
    }

    /**
     * gets the supply from directions
     * @param map
     * @param direction
     * @throws ImpossibleActionException
     */
    public void gatherSupply(Map map, Direction direction) throws ImpossibleActionException {
        int supplyPosX = getX() + direction.x;
        int supplyPosY = getY() + direction.y;

        throwExceptionIfGatheringImpossible(supplyPosX, supplyPosY, map);

        Ocean ocean = (Ocean) map.getTile(supplyPosX, supplyPosY);
        Supply supply = ocean.getSupply();

        inventory.add(supply, 1);

        ocean.setSupply(null);
    }

    /**
     * checks if you have the right amount of resources to build
     * @param building
     * @return
     */
    private boolean hasEnaughResources(Building building) {
        java.util.Map<Resource, Integer> costs = building.getResourceCost();
        for (Resource resource : costs.keySet()) {
            if (inventory.getResourceAmount(resource) - costs.get(resource) < 0) return false;
        }
        return true;
    }

    /**
     * builds any kind of building, based on it's type
     * @param building
     * @param map
     * @param direction
     * @throws ImpossibleActionException
     */
    public void build(Building building, Map map, Direction direction) throws ImpossibleActionException {
        if (!(map.getTile(getX(), getY()) instanceof Platform))
            throw new ImpossibleActionException("Can only build while on a platform");
        if (!hasEnaughResources(building)) throw new ImpossibleActionException("You need more resources!");

        int buildingPosX = getNewX(direction);
        int buildingPosY = getNewY(direction);

        throwExceptionIfOutOfWorld(buildingPosX, buildingPosY, map.height, map.width);


        if (building instanceof Platform) {
            placePlatform(buildingPosX, buildingPosY, map);
        }
        if (building instanceof PlatformBuilding platformBuilding) {
            placePlatformBuilding(platformBuilding, buildingPosX, buildingPosY, map);
        }
        if (building instanceof OceanBuilding oceanBuilding) {
            placeOceanBuilding(oceanBuilding, buildingPosX, buildingPosY, map);
        }
        payBuildingCosts(building);
    }

    /**
     * uses the building
     * @param map
     * @throws ImpossibleActionException
     */
    public void useBuilding(Map map) throws ImpossibleActionException {
        Building building = map.getTile(getX(), getY()).getBuilding();
        if (!(building instanceof PlatformBuilding platformBuilding))
            throw new ImpossibleActionException("No building here, you could use");


        if (platformBuilding instanceof Fireplace fireplace && !fireplace.hasFoodInside) {
            placeFoodOnFireplace(fireplace);
            return;
        }
        consumeFromBuilding(platformBuilding);
    }

    /**
     * eat / drink from the building
     * @param platformBuilding
     * @throws ImpossibleActionException
     */
    private void consumeFromBuilding(PlatformBuilding platformBuilding) throws ImpossibleActionException {
        if (platformBuilding.actionsUntilConsumption() != 0)
            throw new ImpossibleActionException("Needs " + platformBuilding.actionsUntilConsumption() + " more actions until ready to consume");
        platformBuilding.consumeFromIt(this);
    }

    /**
     * places potato or fish on fireplace
     * @param fireplace
     * @throws ImpossibleActionException
     */
    private void placeFoodOnFireplace(Fireplace fireplace) throws ImpossibleActionException {
        Resource food;
        try {
            food = chooseRawFood();
        } catch (ImpossibleActionException e) {
            throw new ImpossibleActionException(e.getMessage() + " to place it on the fire.");
        }
        inventory.remove(food, 1);
        fireplace.startMakingConsumable();
        IOHandler.addMessage("Started cooking");
    }

    /**
     *
     * @return potato or if player doesn't have that ,fish. Otherwhise exception
     * @throws ImpossibleActionException
     */
    private Resource chooseRawFood() throws ImpossibleActionException {
        if (inventory.getResourceAmount(Resource.POTATO) > 0) return Resource.POTATO;
        if (inventory.getResourceAmount(Resource.FISH) > 0) return Resource.FISH;
        throw new ImpossibleActionException("You don't have any potato or fish");
    }

    /**
     * replenished drinkFullness
     * @param amount
     */
    public void drink(int amount) {
        drinkFullness += amount;
        if (drinkFullness > maxDrinkFullness) drinkFullness = maxDrinkFullness;
    }
    /**
     * replenished foodFullness
     * @param amount
     */
    public void eat(int amount) {
        foodFullness += amount;
        if (foodFullness > maxFoodFullness) foodFullness = maxFoodFullness;
    }

    /**
     * places an ocean building
     * @param building
     * @param x
     * @param y
     * @param map
     * @throws ImpossibleActionException
     */
    private void placeOceanBuilding(OceanBuilding building, int x, int y, Map map) throws ImpossibleActionException {
        if (map.getTile(x, y) instanceof Platform) throw new ImpossibleActionException("Place it on Ocean!");

        map.placeBuilding(building, x, y);
    }

    /**
     * places a platform building
     * @param building
     * @param x
     * @param y
     * @param map
     * @throws ImpossibleActionException
     */
    private void placePlatformBuilding(PlatformBuilding building, int x, int y, Map map) throws ImpossibleActionException {
        if (map.getTile(x, y) instanceof Ocean) throw new ImpossibleActionException("Place it on a platform!");
        map.placeBuilding(building, x, y);
    }

    /**
     * places a platform on the ocean
     * @param x
     * @param y
     * @param map
     * @throws ImpossibleActionException
     */
    private void placePlatform(int x, int y, Map map) throws ImpossibleActionException {
        Tile tile = map.getTile(x, y);
        if (!(tile instanceof Ocean)) throw new ImpossibleActionException("Build on the ocean");

        map.placePlatform(x, y);
    }

    /**
     * removes the resources from the inventory
     * @param building
     */
    private void payBuildingCosts(Building building) {
        getInventory().remove(building.getResourceCost());
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getStatusIllustration() {
        return "Food: " + foodFullness + " | Water: " + drinkFullness + " | SharkAttacksToDie: " + defendableSharkAttackCount + 1;
    }

    public String getIllustration() {
        return Illustrations.getPlayerIllustration();
    }
}
