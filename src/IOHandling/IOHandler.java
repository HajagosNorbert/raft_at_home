package IOHandling;

import game.Game;
import helper.Direction;
import helper.UserInputException;
import world.Map;
import world.Ocean;
import world.worldObject.build.Building;
import world.worldObject.living.Action;
import world.worldObject.living.Player;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Almost all communication between the user and the game happen here. Input and output through the command line
 */
public class IOHandler {
    private static final Scanner sc;
    private static final Pattern commandPattern;
    private static final String clearScreenText;
    private static String helpText;
    private static String message;


    static {
        message = "";
        Path helpTextPath = Paths.get("resources/helptext.txt");
        try {
            helpText = Files.readString(helpTextPath);
        } catch (IOException e) {
            System.out.println("Sorry, Help text couldn't be loaded. Gotta figure it out all by yourself.");
        }

        clearScreenText = "\033[H\033[2J";
        sc = new Scanner(System.in);
        commandPattern = Pattern.compile("^(([1-9])|(h(elp)?)|(g(ather)? [1-9])|(b(uild)? (fireplace|purifier|net|platform) [1-9]))$");
    }

    /**
     * At the end of turn the message will be shown.
     * @param message
     */

    public static void addMessage(String message){
        IOHandler.message += message + System.lineSeparator();
    }

    /**
     * delete the message
     */
    public static void deleteMessage(){
        message = "";
    }

    public static String getMessage(){
        return message;
    }

    /**
     * Gets an imput and compares it to the valid commands which is a regex
     * @return
     */
    static private Matcher getCommand() {
        System.out.print("Type in command: ");
        Matcher m;
        String input;
        do {
            input = sc.nextLine().trim().toLowerCase(Locale.ROOT);
            m = commandPattern.matcher(input);
            if (!m.matches()) {
                System.out.println("Invalid input format. Type [h] or [help] for guidance");
            }
        } while (!m.matches());

        return m;
    }

    /**
     * If the matched pattern was of this format, return the players's useBuilding method
     * @param m
     * @param player
     * @param map
     * @return
     */

    private static Action tryInteractHereActionFormat(Matcher m, Player player, Map map) {
        if (m.group(2) == null || !m.group(2).equals("5")) return null;
        int x = player.getX();
        int y = player.getY();
        if (map.getTile(x, y) instanceof Ocean) {
            return player::fish;
        }
        return () -> player.useBuilding(map);
    }

    /**
     *  * If the matched pattern was of this format, return the players's move method
     * @param m
     * @param player
     * @param map
     * @return
     */
    private static Action tryMoveActionFormat(Matcher m, Player player, Map map) {
        if (m.group(2) == null) return null;
        Direction moveDir = Direction.directionCodeToDirection(Integer.parseInt(m.group(2)));
        return () -> player.move(map, moveDir);
    }

    /**
     *  * If the matched pattern was of this format, print the helptext
     * @param m
     * @param game
     * @return
     */
    private static boolean tryHelpFormat(Matcher m, Game game) {
        if (m.group(3) == null) return false;
        help(game.getGameIllustration());
        return true;
    }

    /**
     *  * If the matched pattern was of this format, return the players's gatherSupply method
     * @param m
     * @param player
     * @param map
     * @return
     */
    private static Action tryGatherActionFormat(Matcher m, Player player, Map map) {
        if (m.group(5) == null) return null;
        Direction direction = Direction.directionCodeToDirection(Integer.parseInt(m.group(5).split(" ")[1]));
        return () -> player.gatherSupply(map, direction);
    }

    /**
     * makes the classname from "fireplace" -to-> "world.worldObject.nuild.Fireplace"
     * @param buildingName
     * @return
     */
    private static String getBuildingClassName(String buildingName){
        return Building.class.getPackageName()+"."+Character.toUpperCase(buildingName.charAt(0)) + buildingName.substring(1);
    }

    /**
     *  * If the matched pattern was of this format, return the players's build method, with the correct building inside
     * @param m
     * @param player
     * @param map
     * @return
     */
    private static Action tryBuildActionFormat(Matcher m, Player player, Map map) {
        if (m.group(7) == null) return null;
        Direction direction = Direction.directionCodeToDirection(Integer.parseInt(m.group(7).split(" ")[2]));
        String buildingName = m.group(7).split(" ")[1];
        String buildingClassName = getBuildingClassName(buildingName);
        Class buildingClass;
        Building building;
        try {
            buildingClass = Class.forName(buildingClassName);
            Constructor buildingConstructor = buildingClass.getConstructors()[0];
            if(buildingConstructor.getParameterTypes().length > 0){
                building = (Building) buildingConstructor.newInstance(player);
            } else {
                building = (Building) buildingConstructor.newInstance();
            }

            return () -> player.build(building, map, direction);
        } catch (Exception e) {
            System.out.println("Internal error occurred, while locating a the specified item to craft. Please try again");
        }
        return null;
    }

    /**
     * get a method on the player with it's arguments
     * @param game
     * @return
     */
    public static Action getAction(Game game) {
        Action action;
        Player player = game.getPlayer();
        Map map = game.getMap();
        Matcher m = getCommand();

        boolean helped = tryHelpFormat(m, game);
        if (helped) return getAction(game);

        action = tryInteractHereActionFormat(m, player, map);
        if (action != null) return action;

        action = tryMoveActionFormat(m, player, map);
        if (action != null) return action;

        action = tryGatherActionFormat(m, player, map);
        if (action != null) return action;

        action = tryBuildActionFormat(m, player, map);
        return action;
    }

    /**
     * Says hi
     */
    public static void greet() {
        System.out.print("Game started. Press [h] for help on the controls. ");
    }

    /**
     * displays helptext and the game afterwards
     * @param gameIllustration
     */
    public static void help(String gameIllustration) {
        System.out.print(helpText);
        sc.next();
        displayGameIllustration(gameIllustration);
    }

    /**
     * gets a true of false based on what counts as such, denoted by positiveAnswer and negativeAnswer
     * @param positiveAnswer
     * @param negativeAnswer
     * @return
     * @throws UserInputException
     */
    public static boolean getBinaryAnswer(String positiveAnswer, String negativeAnswer) throws UserInputException {
        Pattern yesOrNoPatter = Pattern.compile("^(" + positiveAnswer + "|" + negativeAnswer + ")$");
        Matcher m = yesOrNoPatter.matcher(sc.nextLine().trim().toLowerCase());
        if (!m.matches()) {
            throw new UserInputException("Only [" + positiveAnswer + "] or [" + negativeAnswer + "] accepted");
        }
        //yes group != null
        return m.group(1) != null;
    }

    /**
     * Asks a question with only ture or false type of response
     * @param question
     * @param positiveAnswer
     * @param negativeAnswer
     * @return
     */
    public static boolean askBinaryQuestion(String question, String positiveAnswer, String negativeAnswer) {
        System.out.printf("%s [%s] or [%s]%n", question, positiveAnswer, negativeAnswer);
        try {
            return getBinaryAnswer(positiveAnswer, negativeAnswer);
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
            return askBinaryQuestion("Again... " + question, positiveAnswer, negativeAnswer);
        }
    }

    /**
     * Prints the illustrations on the screen
     * @param gameIllustration
     */
    public static void displayGameIllustration(String gameIllustration) {
        System.out.print(clearScreenText);
        System.out.flush();
        System.out.print(gameIllustration);
    }
}
