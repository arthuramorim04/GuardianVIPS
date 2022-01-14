package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class RemoveKeyCommand implements CommandExecutor {
    private GuardianVips plugin;

    public RemoveKeyCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender.hasPermission("guardianvips.removekeyvip") || commandSender.hasPermission("guardianvips.admin")){
            if (args.length == 1) {
                boolean isRemoved = false;
                try {
                    isRemoved = plugin.getKeysService().deleteKey(args[0]);
                } catch (IllegalAccessException e) {
                    commandSender.sendMessage(plugin.getMessageUtils().getMessage("key_not_found"));
                    return true;
                } catch (IOException e) {
                    commandSender.sendMessage(plugin.getMessageUtils().getMessage("&cHave a problem on key.yml"));
                    return true;
                }
                if (isRemoved) {
                    commandSender.sendMessage(plugin.getMessageUtils().getMessage("removed_key"));
                    return true;
                } else {
                    commandSender.sendMessage(plugin.getMessageUtils().getMessage("key_not_found"));
                    return true;
                }
            }
        }  else {
            commandSender.sendMessage(plugin.getMessageUtils().getMessage("no_permission"));
        }
        return false;
    }
}
