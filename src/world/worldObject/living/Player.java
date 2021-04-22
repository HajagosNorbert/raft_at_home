package world.worldObject.living;

import helper.Direction;
import helper.Illustrations;
import world.Illustratable;

public class Player extends Character implements Illustratable {

    private int foodFullnes;
    private int drinkFullnes;
    private int defendableSharkAttackCount;

    public Inventory inventory;

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
        this.inventory = new Inventory();
        this.foodFullnes = 100;
        this.drinkFullnes = 100;
        this.defendableSharkAttackCount = 0;
    }

    public Player(int x, int y, int foodFullnes, int drinkFullnes, int defendableSharkAttackCount, Inventory inventory){
        super(x, y);
        this.foodFullnes = foodFullnes;
        this.drinkFullnes = drinkFullnes;
        this.defendableSharkAttackCount = defendableSharkAttackCount;
        this.inventory = inventory;
    }

    public void move(Direction dir){
        setX(getX()+dir.x);
        setY(getY()+dir.y);
    }

    public boolean fish(){
        return true;
    }

    public String getStatusIllustration(){
        return "Food: "+foodFullnes+" | Water: "+drinkFullnes+" | SharkAttacksToDie: "+defendableSharkAttackCount+1;
    }

    public String getIllustration() {
        return Illustrations.getPlayerIllustration();
    }
}
