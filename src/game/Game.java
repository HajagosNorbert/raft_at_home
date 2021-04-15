package game;

import IOHandling.GameLoader;
import IOHandling.InputHandler;
import world.Map;

import java.io.File;

public class Game {
    //max 1000
    private int actionCounter;
    private final InputHandler inputHandler;
    private Map map;

    public void createGame(int actionCounter, Map map){
        this.actionCounter = actionCounter;
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public Game(){
        inputHandler = new InputHandler();
        File saveFile = new File("raftsave.json");

        boolean needToLoadGame = false;
        if(saveFile.exists()){
            needToLoadGame = inputHandler.askBinaryQuestion("Savefile found! Do you want to load the game or create a new?", "load", "new");
        }
        if(needToLoadGame){
            GameLoader.load(this);
            return;
        }
        createGame(0, new Map());
    }

}
