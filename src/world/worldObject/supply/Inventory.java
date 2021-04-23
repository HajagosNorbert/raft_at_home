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

    public void add(Resource resource, int amount){
        resourceAmounts.put(resource, resourceAmounts.get(resource) + amount);
    }

    public String getIllustration(){
        return resourceAmounts.entrySet().stream().map(this::getResourceAmountIllustration).collect(Collectors.joining(" | "));
    }

    private String getResourceAmountIllustration(Map.Entry<Resource, Integer> resourceAmount){
        return resourceAmount.getKey().getName()+": "+resourceAmount.getValue().toString();
    }
}