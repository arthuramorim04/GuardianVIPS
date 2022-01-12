package guardianVip.commands;

import guardianVip.GuardianVips;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;


public class PlayerCommands implements CommandExecutor {

    private GuardianVips plugin;

    public PlayerCommands(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            if (args[0].equals("reload") && sender.hasPermission("guardianvips.reload")) {
                plugin.getYamlVipConfig().reload();
                plugin.getMessageConfig().reload();
                plugin.getKeysVips().reload();
                plugin.getYamlConfig().reload();
                plugin.getVipService().loadVipListOnFile();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "GuardianVips reload config success!"));
                return false;
            }
            return false;
        }
        if (args.length == 0) {
        sender.sendMessage(plugin.getMessageUtils().getMessage("player_help"));
        if (sender.hasPermission("guardianvips.admin.help")) {
            sender.sendMessage(plugin.getMessageUtils().getMessage("admin_help"));
        }

        }
        return false;
    }




}
