package world.worldObject.living;

import helper.Direction;
import helper.Illustrations;
import helper.ImpossibleActionException;
import world.Illustratable;
import world.Map;
import world.worldObject.supply.Inventory;
import world.worldObject.supply.Resource;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.stream.Collectors;

public class Player extends Character implements Illustratable {

    private int foodFullnes;
    private int drinkFullnes;
    private int defendableSharkAttackCount;
    private Inventory inventory;


    public void sufferHunger(){
        foodFullnes--;
    }

    public String getCauseOfDeath(){
        if(foodFullnes < 0){
            return "starvation";
        }
        if(drinkFullnes < 0){
            return "dehydration";
        }
        if(defendableSharkAttackCount < 0){
            return "being shark food";
        }
        return null;
    }

    public void sufferThirst(){
        drinkFullnes--;
    }

    public boolean isAlive(){
        return foodFullnes > 0 && drinkFullnes > 0;
    }

    public Player(int x, int y){
        super(x, y);
        this.foodFullnes = 100;
        this.drinkFullnes = 100;
        this.defendableSharkAttackCount = 0;
        this.inventory = new Inventory();
        //meg kell tölteni resource - 0 párokkal?
    }

    public Player(int x, int y, int foodFullnes, int drinkFullnes, int defendableSharkAttackCount, Inventory inventory){
        super(x, y);
        this.foodFullnes = foodFullnes;
        this.drinkFullnes = drinkFullnes;
        this.defendableSharkAttackCount = defendableSharkAttackCount;
        this.inventory = inventory;
    }

    public void move(Map map, Direction direction) throws ImpossibleActionException{
        int newX = getX() + direction.x;
        int newY = getY() + direction.y;

        if(newY < 0 || newY >= map.height || newX < 0 || newX >= map.width){
            throw new ImpossibleActionException("Out of the world");
        }
        setX(newX);
        setY(newY);
    }

    public boolean fish(){
        if(Math.random() <= 0.25){
            inventory.add(Resource.FISH, 1);
        }
        return true;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public String getStatusIllustration(){
        return "Food: "+foodFullnes+" | Water: "+drinkFullnes+" | SharkAttacksToDie: "+defendableSharkAttackCount+1;
    }

    public String getIllustration() {
        return Illustrations.getPlayerIllustration();
    }
}
