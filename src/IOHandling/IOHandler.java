package IOHandling;

import game.Game;
import helper.Direction;
import helper.UserInputException;
import world.worldObject.living.Action;

import javax.swing.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOHandler {
    private Scanner sc;
    private Game game;
    private Pattern commandPattern;

    {
        sc = new Scanner(System.in);
        commandPattern = Pattern.compile("^(([1-9])|(h(elp)?)|(g(ather)? [1-9])|(b(uild)? (spear|fire|purifier|net|platform) [1-9]))$");
    }

    public IOHandler(Game game) {
        this.game = game;
    }

    private void getCommand() throws UserInputException{
        String input = sc.nextLine();
        Matcher m = commandPattern.matcher(input);
        if(!m.find()){
            throw new UserInputException("Invalid input format. Type [h] or [help] for guidance");
        }

        //number input
        if(m.group(2) != null){
            if(m.group(2) != "5"){
                Direction moveDir = Direction.directionCodeToDirection(Integer.parseInt(m.group(2)));
                //lehetne akár Action class nélkül is.
                return new Action.move(moveDir);
            }
        }
    }

    public void initiateInputCycle(){
        //az 5-össel fish, drink, eat
        while(true){
            try {
                String command = getCommand();
            } catch (UserInputException e){
                System.out.println(e.getMessage());
            }
        }

    }


    public boolean getBinaryAnswer(String positiveAnswer, String negativeAnswer) throws UserInputException {
        Pattern yesOrNoPatter = Pattern.compile("^("+positiveAnswer+"|"+negativeAnswer+")$");
        Matcher m = yesOrNoPatter.matcher(sc.nextLine().trim().toLowerCase());
        if(!m.find()){
            throw new UserInputException("Only ["+positiveAnswer+"] or ["+negativeAnswer+"] accepted");
        }
        //yes group != null
        return m.group(1) != null;
    }

    public boolean askBinaryQuestion(String question, String positiveAnswer, String negativeAnswer){
        System.out.println(String.format("%s [%s] or [%s]" ,question, positiveAnswer, negativeAnswer));
        try{
            return getBinaryAnswer(positiveAnswer, negativeAnswer);
        } catch (UserInputException e){
            System.out.println(e.getMessage());
            return askBinaryQuestion("Again... "+question, positiveAnswer, negativeAnswer);
        }
    }
}
