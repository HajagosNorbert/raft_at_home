package IOHandling;

import game.Game;
import helper.Direction;
import helper.ImpossibleActionException;
import helper.UserInputException;
import world.Ocean;
import world.worldObject.living.Action;
import world.worldObject.living.MoveAction;

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

    public static Action getAction(Game game) {
        //talán ha átadnék egy currentIllustration String-et.
        Matcher m = getCommand();
        //number input
        if (m.group(2) != null) {
            if (m.group(2) == "5") {
                //fish
                int x = game.getPlayer().getX();
                int y = game.getPlayer().getY();
                if(game.getMap().getTile(x, y) instanceof Ocean){
                    //do fishing
                }

                //drink
                //eat

            }
            Direction moveDir = Direction.directionCodeToDirection(Integer.parseInt(m.group(2)));
            try {
                return new MoveAction(game.getMap(), game.getPlayer(), moveDir);
            } catch (ImpossibleActionException e) {
                System.out.println(e.getMessage());
                return getAction(game);
            }
        }
        if (m.group(3) != null) {
            help(game.getGameIllustration());
            return getAction(game);
        }
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
