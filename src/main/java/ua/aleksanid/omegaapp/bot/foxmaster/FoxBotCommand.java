package ua.aleksanid.omegaapp.bot.foxmaster;

public enum FoxBotCommand {
    GIVEAWAYS("/giveaway"),
    SUBSCRIBE("/subscribe"),
    UNSUBSCRIBE("/unsubscribe");

    private final String command;

    FoxBotCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
