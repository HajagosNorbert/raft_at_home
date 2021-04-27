package world.worldObject.supply;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Inventory {
    private Map<Resource, Integer> resourceAmounts;
    public Inventory() {
        resourceAmounts = Arrays.stream(Resource.values()).collect(Collectors.toMap(resource -> resource, resource -> 0));
    }

    public Inventory(Map<Resource, Integer> resourceAmounts) {
        this.resourceAmounts = resourceAmounts;
    }

    public void remove(Map<Resource, Integer> costs){
        //átírni a -t és b -t, ha jók.
        costs.forEach((resource, cost )-> resourceAmounts.merge(resource, cost, (a, b) -> a - b));
    }

    public Map<Resource, Integer> getResourceAmounts() {
        return resourceAmounts;
    }

    public void add(Resource resource, int amount){
        resourceAmounts.put(resource, resourceAmounts.get(resource) + amount);
    }

    public void add(Supply supply, int amount){
        add( Resource.valueOf(supply.name()), 1);
    }

    public void add(Map<Resource, Integer> amountsToAdd){
        amountsToAdd.forEach((resource, amount )-> resourceAmounts.merge(resource, amount, Integer::sum));
    }


    public String getIllustration(){
        return resourceAmounts.entrySet().stream().map(this::getResourceAmountIllustration).collect(Collectors.joining(" | "));
    }

    private String getResourceAmountIllustration(Map.Entry<Resource, Integer> resourceAmount){
        return resourceAmount.getKey().name().toLowerCase()+": "+resourceAmount.getValue().toString();
    }
}
