package guardianVip.commands;

import guardianVip.GuardianVips;
import guardianVip.entity.KeyVip;
import guardianVip.utils.KeysUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ActiveVipCommand implements CommandExecutor {

    private GuardianVips plugin;

    public ActiveVipCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            Bukkit.getConsoleSender().sendMessage("Command only avaliable to user");
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage(plugin.getMessageUtils().getMessage("active_key"));
            return false;
        }

        KeyVip keyVip = plugin.getKeysService().getKeyVip(args[0]);
        if (keyVip != null) {
            plugin.getKeysService().activeKey(keyVip, (Player) sender);
            return true;
        }  else {
            try {
                Long convert = KeysUtils.convert(args[0]);
                keyVip = plugin.getKeysService().getKeyVip(String.valueOf(convert));
                if (keyVip != null) {
                    plugin.getKeysService().activeKey(keyVip, (Player) sender);
                    return true;
                } else {
                    sender.sendMessage(plugin.getMessageUtils().getMessage("key_not_found"));
                    return false;
                }
            } catch (IllegalAccessException e) {
                sender.sendMessage(plugin.getMessageUtils().getMessage("key_not_found"));
                return false;
            }
        }
    }
}
