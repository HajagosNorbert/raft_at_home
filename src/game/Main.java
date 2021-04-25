package game;

public class Main {

    public static void main(String[] args) {
        boolean isSimplyIllustrated = args.length > 0 && args[0].toLowerCase().equals("simple");
        Game game = new Game(isSimplyIllustrated);
        game.start();
    }
}
