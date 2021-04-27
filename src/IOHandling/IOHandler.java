package IOHandling;

import game.Game;
import helper.Direction;
import helper.UserInputException;
import world.Map;
import world.Ocean;
import world.worldObject.craft.Craftable;
import world.worldObject.living.Action;
import world.worldObject.living.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOHandler {
    private static Scanner sc;
    private static Pattern commandPattern;
    private static final String clearScreanText;
    private static String helpText;

    static {
        //A working directory módosításával működik csak
        Path helpTextPath = Paths.get("resources/helptext.txt");
        try {
            helpText = Files.readString(helpTextPath);
        } catch (IOException e) {
            System.out.println("Sorry, Help text couldn't be loaded. Gotta figure it out all by yourself.");
        }

        clearScreanText = "\033[H\033[2J";
        sc = new Scanner(System.in);
        commandPattern = Pattern.compile("^(([1-9])|(h(elp)?)|(g(ather)? [1-9])|(b(uild)? (spear|fireplace|purifier|net|platform) [1-9]))$");
    }

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

    private static Action tryInteractHereAction(Matcher m, Player player, Map map) {
        if (m.group(2) == null || !m.group(2).equals("5")) return null;
        int x = player.getX();
        int y = player.getY();
        if (map.getTile(x, y) instanceof Ocean) {
            return () -> player.fish();
        }
        //drink
        //eat
        return null;
    }

    private static Action tryMoveActionFormat(Matcher m, Player player, Map map) {
        if (m.group(2) == null) return null;
        Direction moveDir = Direction.directionCodeToDirection(Integer.parseInt(m.group(2)));
        return () -> player.move(map, moveDir);
    }

    private static boolean tryHelpFormat(Matcher m, Game game) {
        if (m.group(3) == null) return false;
        help(game.getGameIllustration());
        return true;
    }

    private static Action tryGatherAction(Matcher m, Player player, Map map) {
        if (m.group(5) == null) return null;
        Direction direction = Direction.directionCodeToDirection(Integer.parseInt(m.group(5).split(" ")[1]));
        return () -> player.gatherSupply(map, direction);
    }
    private static String getCraftableClassName(String craftableName){
        return Craftable.class.getPackageName()+"."+Character.toUpperCase(craftableName.charAt(0)) + craftableName.substring(1);
    }

    private static Action tryCraftAction(Matcher m, Player player, Map map) {
        if (m.group(7) == null) return null;
        Craftable.class.getPackageName();
        Direction direction = Direction.directionCodeToDirection(Integer.parseInt(m.group(7).split(" ")[2]));
        String craftableName = m.group(7).split(" ")[1];
        String craftableClassName = getCraftableClassName(craftableName);
        Class craftableClass;
        Craftable craftable;
        try {
            craftableClass = Class.forName(craftableClassName);
            //class.getConstructor(int.class, int.class, double.class).newInstance(_xval1,_xval2,_pval);
            craftable = (Craftable) craftableClass.getConstructor().newInstance();
            return () -> player.craft(craftable, map, direction);
        } catch (Exception e) {
            System.out.println("Internal error occured, while locating a the specified item to craft. Please try again");
        }
        return null;
    }

    public static Action getAction(Game game) {
        Action action;
        Player player = game.getPlayer();
        Map map = game.getMap();
        Matcher m = getCommand();

        boolean helped = tryHelpFormat(m, game);
        if (helped) return getAction(game);

        action = tryInteractHereAction(m, player, map);
        if (action != null) return action;

        action = tryMoveActionFormat(m, player, map);
        if (action != null) return action;

        action = tryGatherAction(m, player, map);
        if (action != null) return action;

        action = tryCraftAction(m, player, map);
        if (action != null) return action;

        return null;
    }

    public static void greet() {
        System.out.print("Game started. Press [h] for help on the controlls. ");
    }

    public static void help(String gameIllustration) {
        System.out.print(helpText);
        sc.nextLine();
        displayGameIllustration(gameIllustration);
    }

    public static boolean getBinaryAnswer(String positiveAnswer, String negativeAnswer) throws UserInputException {
        Pattern yesOrNoPatter = Pattern.compile("^(" + positiveAnswer + "|" + negativeAnswer + ")$");
        Matcher m = yesOrNoPatter.matcher(sc.nextLine().trim().toLowerCase());
        if (!m.matches()) {
            throw new UserInputException("Only [" + positiveAnswer + "] or [" + negativeAnswer + "] accepted");
        }
        //yes group != null
        return m.group(1) != null;
    }

    public static boolean askBinaryQuestion(String question, String positiveAnswer, String negativeAnswer) {
        System.out.println(String.format("%s [%s] or [%s]", question, positiveAnswer, negativeAnswer));
        try {
            return getBinaryAnswer(positiveAnswer, negativeAnswer);
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
            return askBinaryQuestion("Again... " + question, positiveAnswer, negativeAnswer);
        }
    }

    public static void displayGameIllustration(String gameIllustration) {
        System.out.print(clearScreanText);
        System.out.flush();
        System.out.print(gameIllustration);
    }
}
