package guardianVip.utils;

import guardianVip.GuardianVips;

public class MessageUtils {

    private GuardianVips plugin;

    public MessageUtils(GuardianVips plugin) {
        this.plugin = plugin;
    }

    public String getMessage(String messageType) {
        return plugin.getMessageConfig().getConfigFile().getString(messageType);
    }

    public String replacePlayerName(String message, String playerName) {
        return message.replace("%player%", playerName);
    }
}
