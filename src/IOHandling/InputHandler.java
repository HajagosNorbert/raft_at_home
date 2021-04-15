package IOHandling;

import helper.UserInputException;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {
    Scanner in;

    {
        in = new Scanner(System.in);
    }

    public boolean getBinaryAnswer(String positiveAnswer, String negativeAnswer) throws UserInputException {
        Pattern yesOrNoPatter = Pattern.compile("^("+positiveAnswer+"|"+negativeAnswer+")$");
        Matcher m = yesOrNoPatter.matcher(in.nextLine().trim().toLowerCase());
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
