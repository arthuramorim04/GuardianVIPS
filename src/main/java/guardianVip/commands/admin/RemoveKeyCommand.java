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
            boolean isRemoved = plugin.getKeysService().deleteKey(args[0]);
            if (isRemoved) {
                commandSender.sendMessage(plugin.getMessageUtils().getMessage("removed_key"));
                return true;
            } else {
                commandSender.sendMessage(plugin.getMessageUtils().getMessage("key_not_found"));
                return true;
            }
        }  else {
            commandSender.sendMessage(plugin.getMessageUtils().getMessage("no_permission"));
        }
        return false;
    }
}
