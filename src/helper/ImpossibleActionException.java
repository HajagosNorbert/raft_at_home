package helper;

/**
 * Custom exception for more understandable code
 */
public class ImpossibleActionException extends Exception {

    public ImpossibleActionException(String msg) {
        super(msg);
    }
}
