package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KeyGenerateCommand implements CommandExecutor {
    private GuardianVips plugin;

    public KeyGenerateCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender.hasPermission("guardianvips.removevip")) {

        }

        commandSender.sendMessage(this.getClass().getName());
        return false;
    }
}
