package in.avc.csvsql.io;

/*package-private*/ class ColoredTextGenerator {
    public static String colorStringWith(final String string, final ConsoleTextColor consoleTextColor) {
        return String.format("%s%s%s",
                consoleTextColor.code.value,
                string,
                ColorCode.ANSI_RESET.value);
    }

    static enum ColorCode {
        ANSI_RESET("\u001B[0m"),
        ANSI_BLACK ("\u001B[30m"),
        ANSI_RED ("\u001B[31m"),
        ANSI_GREEN ("\u001B[32m"),
        ANSI_YELLOW ("\u001B[33m"),
        ANSI_BLUE ("\u001B[34m"),
        ANSI_PURPLE ("\u001B[35m"),
        ANSI_CYAN ("\u001B[36m"),
        ANSI_WHITE ("\u001B[37m");

        private final String value;

        private ColorCode(final String value) {
            this.value = value;
        }
    }

    static enum ConsoleTextColor {
        RED(ColorCode.ANSI_RED),
        BLACK(ColorCode.ANSI_BLACK),
        CYAN(ColorCode.ANSI_CYAN),
        YELLOW(ColorCode.ANSI_YELLOW);

        private final ColorCode code;

        private ConsoleTextColor(final ColorCode colorCode) {
            this.code = colorCode;
        }
    }

}