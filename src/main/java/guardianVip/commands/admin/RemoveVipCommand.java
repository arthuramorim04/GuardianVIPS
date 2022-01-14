package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import guardianVip.entity.Vip;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveVipCommand implements CommandExecutor {

    private GuardianVips plugin;

    public RemoveVipCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("guardianvips.removevip") || sender.hasPermission("guardianvips.admin")) {

            if (args.length != 2) {
                sendDefaultCommandExample(sender);
                return true;
            }

            Vip vip = plugin.getVipService().getVipByName(args[1]);
            if (vip == null) {
                sender.sendMessage(plugin.getMessageUtils().getMessage("vip_not_found"));
                return true;
            }

            Player player = Bukkit.getPlayerExact(args[0]);
            if (player == null) {
                if (plugin.getVipActiveService().removeVipOfflinePlayer(vip, args[0])) {
                    plugin.getUserService().saveUserVip(args[0]);
                } else {
                    sender.sendMessage(plugin.getMessageUtils().getMessage("player_not_found"));
                    return true;
                }
            } else {
                plugin.getVipActiveService().removeVip(vip, player);
                plugin.getUserService().saveUserVip(player);
            }

            if (player != null){
                player.sendMessage(plugin.getMessageUtils().getMessage("player_vip_removed").replace("%vip%", vip.getName()));
            }
            sender.sendMessage(plugin.getMessageUtils().getMessage("vip_removed"));

        } else {
            sender.sendMessage(plugin.getMessageUtils().getMessage("player_not_found"));
        }
        return false;
    }

    private void sendDefaultCommandExample(CommandSender sender) {
        sender.sendMessage(plugin.getMessageUtils().replaceColorSimbol("&cCommand error, use: /removevip <Player> <VIP>"));
    }
}
