package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RemoveKeyCommand implements CommandExecutor {
    private GuardianVips plugin;

    public RemoveKeyCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender.hasPermission("guardianvips.removevip") || commandSender.hasPermission("guardianvips.admin")){
            plugin.getKeysService().deleteKey(args[0]);
            commandSender.sendMessage(plugin.getMessageUtils().getMessage("removed_key"));
        }  else {
            commandSender.sendMessage(plugin.getMessageUtils().getMessage("no_permission"));
        }
        commandSender.sendMessage(this.getClass().getName());
        return false;
    }
}
