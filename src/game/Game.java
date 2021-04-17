package game;

import IOHandling.GameLoader;
import IOHandling.IOHandler;
import world.Map;
import world.worldObject.living.Player;
import world.worldObject.living.Shark;

import java.io.File;

public class Game {
    //max 1000
    private int maxNumberOfActions;
    private int actionCounter;
    private final IOHandler ioHandler;
    private Map map;
    private Player player;
    private int mapWidth;
    private int mapHeight;
    private Shark shark;


    {
        mapWidth = 35;
        mapHeight = 25;
    }

    public void createGame(int actionCounter, Map map, Player player, Shark shark){
        this.actionCounter = actionCounter;
        this.map = map;
        this.player = player;
        this.shark = shark;
    }

    public void incrementActionCounter(){
        actionCounter++;
        if(actionCounter >= maxNumberOfActions){
            win();
        }
    }

    public int getActionCounter() {
        return actionCounter;
    }

    private void win(){
        System.out.println("Congrats to you! You have won! press [Enter] to exit");
        System.console().readLine();
        System.exit(0);
    }

    public Game(){
        ioHandler = new IOHandler(this);

        File saveFile = new File("raftsave.json");

        boolean needToLoadGame = false;
        if(saveFile.exists()){
            needToLoadGame = ioHandler.askBinaryQuestion("Savefile found! Do you want to load the game or create a new?", "load", "new");
        }
        if(needToLoadGame){
            GameLoader.load(this);
            return;
        }
        createGame(
                0,
                new Map(mapWidth, mapHeight),
                new Player(mapWidth/2, mapHeight/2),
                new Shark(mapWidth-1, mapHeight-1)
        );
    }

    public void start(){
        ioHandler.initiateInputCycle();
    }

    public String getGameIllustration(){
        //ha egymáson van 2 world objektum, akkor a player és shark -ot kell displayelni azon a mező, mást nem. Az nem jó, ha előbb displayelem a purifiert, utána a playert.
        String[][] tileIllustrations = map.getTileIllustrations();

        tileIllustrations[player.getY()][player.getX()] = player.getIllustration();
        tileIllustrations[shark.getY()][shark.getX()] = shark.getIllustration();
        String gameIllustration = "";

        for (int rowIndex = 0; rowIndex < tileIllustrations.length; rowIndex++) {
            gameIllustration += String.join("", tileIllustrations[rowIndex])+System.lineSeparator();
        }

        return gameIllustration;
    }

}
