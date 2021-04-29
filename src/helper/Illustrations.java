package helper;

/**
 * contains the getters for all objects that hava an illustration
 */
public class Illustrations {
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    private static boolean isSimple;

    static {
        isSimple = false;
    }

    /**
     *
     * @param simple if true, all output will be of simple characters
     */
    public static void setIsSimple(boolean simple) {
        isSimple = simple;
    }

    public static String getOceanIllustration() {
        // "\u2591\u2591" = ░░
        String oceanIllustration = "\u2591\u2591";
        if (!isSimple) return CYAN + oceanIllustration + RESET;
        return oceanIllustration;
    }

    public static String getPlatformIllustration() {
        // "\u2588\u2588" = ██
        String platformIllustration = "\u2588\u2588";
        if (!isSimple) return WHITE + platformIllustration + RESET;
        return platformIllustration;
    }

    public static String getPlayerIllustration() {
        // "\uD83D\uDE00" = 😀
        return (!isSimple) ? "\uD83D\uDE00" : "Pl";
    }

    public static String getSharkIllustration() {
        // "\uD83E\uDD88" = 🦈
        return (!isSimple) ? "\uD83E\uDD88" : "Sh";
    }

    public static String getWoodIllustration() {
        // "\uD83E\uDDF1" = 🧱
        return (!isSimple) ? "\uD83E\uDDF1" : "Wd";
    }

    public static String getLeafIllustration() {
        // "\uD83C\uDF41" = 🍁
        return (!isSimple) ? "\uD83C\uDF41" : "Lf";
    }

    public static String getDebrisIllustration() {
        // "\u2699" = ⚙
        return (!isSimple) ? "\u2699" : "De";
    }

    public static String getPotatoIllustration() {
        //"\uD83E\uDD54" = 🥔
        return (!isSimple) ? "\uD83E\uDD54" : "Po";
    }

    public static String getFishIllustration() {
        //"\uD83D\uDC1F" = 🐟
        return (!isSimple) ? "\uD83D\uDC1F" : "Fs";
    }

    public static String getFireplaceIllustration() {
        //"\uD83D\uDD25" = 🔥
        return (!isSimple) ? "\uD83D\uDD25" : "Fp";
    }

    public static String getPurifierIllustration() {
        //"\uD83E\uDD5B" = 🥛
        return (!isSimple) ? "\uD83E\uDD5B" : "Pu";
    }

    public static String getNetIllustration() {
        // "\uD83D\uDD78"  = 🕸️
        return (!isSimple) ? "\uD83D\uDD78" : "Nt";
    }

    public static String getBarrelIllustration() {
        // "\uD83D\uDEE2"  = 🛢️
        return (!isSimple) ? "\uD83D\uDEE2" : "Br";
    }


}
