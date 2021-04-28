package game;

import IOHandling.GameLoader;
import IOHandling.IOHandler;
import helper.Illustrations;
import helper.ImpossibleActionException;
import world.Map;
import world.worldObject.living.Player;
import world.worldObject.living.Shark;

import java.io.File;
import java.util.Scanner;

public class Game {
    public static final int maxNumberOfActions;
    private final int mapWidth;
    private final int mapHeight;

    public static int actionCount;
    private Map map;
    private Player player;
    private Shark shark;

    {
        mapWidth = 35;
        mapHeight = 25;
    }

    static {
        maxNumberOfActions = 1000;
    }

    public Game(boolean isSimplyIllustrated) {
        Illustrations.setIsSimple(isSimplyIllustrated);

        File saveFile = new File("raftsave.json");

        boolean needToLoadGame = false;
        if (saveFile.exists()) {
            needToLoadGame = IOHandler.askBinaryQuestion("Savefile found! Do you want to load the game or create a new?", "load", "new");
        }
        if (needToLoadGame) {
            GameLoader.load(this);
            return;
        }
        createGame(0, new Map(mapWidth, mapHeight), new Player(mapWidth / 2, mapHeight / 2), new Shark(mapWidth / 2 - 2, mapHeight /2 -2));
    }

    public Player getPlayer() {
        return player;
    }

    public Map getMap() {
        return map;
    }

    public void createGame(int actionCounter, Map map, Player player, Shark shark) {
        Game.actionCount = actionCounter;
        this.map = map;
        this.player = player;
        this.shark = shark;
    }

    public void start() {
        IOHandler.displayGameIllustration(getGameIllustration());
        IOHandler.greet();
        while (true) {
            try {
                IOHandler.getAction(this).execute();
            } catch (ImpossibleActionException e) {
                System.out.println(e.getMessage());
                continue;
            }
            actionHappened();
            flowSupplies();
            spawnSupplies();

            IOHandler.displayGameIllustration(getGameIllustration());
            IOHandler.deleteMessage();
            shark.moveRandomly(map);
        }
    }

    public void actionHappened() {
        player.sufferHunger();
        player.sufferThirst();
        if (!player.isAlive()) {
            lose();
        }
        actionCount++;
        if (actionCount >= maxNumberOfActions && player.isAlive()) {
            win();
        }
    }

    private void spawnSupplies() {
        getMap().spawnSupplies();
    }

    private void flowSupplies() {
        getMap().beginFlowSupplies();
    }

    private void lose() {
        System.out.println("You have died due to " + player.getCauseOfDeath() + ". Press [Enter] to exit");

        System.exit(0);
    }

    private void win() {
        System.out.println("Congrats to you! You have won! press [Enter] to exit");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        System.exit(0);
    }

    public String getGameIllustration() {
        String worldIllustration = getWorldIllustration();
        String InventoryIllustration = player.getInventory().getIllustration() + System.lineSeparator();
        String playerStatusIllustration = player.getStatusIllustration() + System.lineSeparator();
        String message = IOHandler.getMessage();
        return worldIllustration + playerStatusIllustration + InventoryIllustration + message;
    }

    private String getWorldIllustration() {
        String[][] tileIllustrations = map.getTileIllustrations();

        tileIllustrations[player.getY()][player.getX()] = player.getIllustration();
        tileIllustrations[shark.getY()][shark.getX()] = shark.getIllustration();
        StringBuilder worldIllustration = new StringBuilder();

        for (String[] tileIllustration : tileIllustrations)
            worldIllustration.append(String.join("", tileIllustration)).append(System.lineSeparator());

        return worldIllustration.toString();
    }

}
