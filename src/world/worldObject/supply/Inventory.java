package world.worldObject.supply;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * stores Resource - number pairs as a Map
 */
public class Inventory {
    private final Map<Resource, Integer> resourceAmounts;

    /**
     * intitialize with 0 items
     */
    public Inventory() {
        resourceAmounts = Arrays.stream(Resource.values()).collect(Collectors.toMap(resource -> resource, resource -> 0));
    }

    public Inventory(Map<Resource, Integer> resourceAmounts) {
        this.resourceAmounts = resourceAmounts;
    }

    /**
     * removes a set of resources with their amounts
     * @param costs
     */
    public void remove(Map<Resource, Integer> costs) {
        //átírni a -t és b -t, ha jók.
        costs.forEach((resource, cost) -> resourceAmounts.merge(resource, cost, (a, b) -> a - b));
    }

    /**
     * remove one kind of reource
     * @param resource
     * @param amount
     */
    public void remove(Resource resource, int amount) {
        resourceAmounts.put(resource, resourceAmounts.get(resource) - amount);
    }

    /**
     * Add one kind of resource
     * @param resource
     * @param amount
     */
    public void add(Resource resource, int amount) {
        resourceAmounts.put(resource, resourceAmounts.get(resource) + amount);
    }

    /**
     * convert the supply into resource and add it to the inventory
     * @param supply
     * @param amount
     */
    public void add(Supply supply, int amount) {
        if (supply == Supply.BARREL) add(Supply.lootBarrel());
        else add(Resource.valueOf(supply.name()), amount);
    }
    /**
     * adds a set of resources with their amounts
     *
     */
    public void add(Map<Resource, Integer> amountsToAdd) {
        amountsToAdd.forEach((resource, amount) -> resourceAmounts.merge(resource, amount, Integer::sum));
    }

    public int getResourceAmount(Resource resource) {
        return resourceAmounts.get(resource);
    }

    public String getIllustration() {
        return resourceAmounts.entrySet().stream().map(this::getResourceAmountIllustration).collect(Collectors.joining(" | "));
    }

    private String getResourceAmountIllustration(Map.Entry<Resource, Integer> resourceAmount) {
        return resourceAmount.getKey().name().toLowerCase() + ": " + resourceAmount.getValue().toString();
    }
}
