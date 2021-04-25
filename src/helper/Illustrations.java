package helper;

public class Illustrations {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    private static boolean isSimple;

    static {
        isSimple = false;
    }

    public static void setIsSimple(boolean simple) {
        isSimple = simple;
    }

    public static final String getOceanIllustration() {
        // "\u2591\u2591" = ░░
        String oceanIllustration = "\u2591\u2591";
        if (!isSimple) return CYAN + oceanIllustration + RESET;
        return oceanIllustration;
    }

    public static final String getPlatformIllustration() {
        // "\u2588\u2588" = ██
        String platformIllustration = "\u2588\u2588";
        if (!isSimple) return WHITE + platformIllustration + RESET;
        return platformIllustration;
    }

    public static final String getPlayerIllustration() {
        // "\uD83D\uDE00" = 😀
        return (!isSimple) ? "\uD83D\uDE00" : "Pl";
    }

    public static final String getSharkIllustration() {
        // "\uD83E\uDD88" = 🦈
        return (!isSimple) ? "\uD83E\uDD88" : "Sh";
    }

    public static final String getWoodIllustration() {
        // "\uD83E\uDDF1" = 🧱
        return  (!isSimple) ? "\uD83E\uDDF1" : "Wd";
    }

    public static final String getLeafIllustration() {
        // "\uD83C\uDF41" = 🍁
        return  (!isSimple) ? "\uD83C\uDF41" : "Lf";
    }

    public static final String getDebrisIllustration() {
        // "\u2699" = ⚙
        return  (!isSimple) ? "\u2699" : "De";
    }

    public static final String getPotatoIllustration() {
        //"\uD83E\uDD54" = 🥔
        return  (!isSimple) ? "\uD83E\uDD54" : "Po";
    }

    public static final String getFishIllustration() {
        //"\uD83D\uDC1F" = 🐟
        return (!isSimple) ? "\uD83D\uDC1F" : "Fs";
    }

    public static final String getSpearIllustration() {
        //"\uD83D\uDDE1" = 🗡️
        return (!isSimple) ? "\uD83D\uDDE1" : "Sp";
    }

    public static final String getFireplaceIllustration() {
        //"\uD83D\uDD25" = 🔥
        return (!isSimple) ? "\uD83D\uDD25" : "Fp";
    }

    public static final String getPurifierIllustration() {
        //"\uD83E\uDD5B" = 🥛
        return (!isSimple) ? "\uD83E\uDD5B" : "Pu";
    }

    public static final String getNetIllustration() {
        // "\uD83D\uDD78"  = 🕸️
        return (!isSimple) ? "\uD83D\uDD78" : "Nt";
    }

    public static final String getBarrelIllustration() {
        // "\uD83D\uDEE2"  = 🛢️
        return (!isSimple) ? "\uD83D\uDEE2" : "Br";
    }


}
