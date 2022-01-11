package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import guardianVip.entity.Vip;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddVipCommand implements CommandExecutor {

    private GuardianVips plugin;

    public AddVipCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("guardianvips.addvip") || sender.hasPermission("guardianvips.admin")) {
            if (args.length != 5) {
                sender.sendMessage("Command error, use: /addvip <Player> <VIP> <Days> <Hours> <Minutes>");
                return true;
            }

            Vip vip = plugin.getVipService().getVipByName(args[1]);
            if (vip == null) {
                sender.sendMessage("Vip not found");
                return true;
            }

            Player player = Bukkit.getPlayerExact(args[0]);
            if (player == null) {
                sender.sendMessage("Player not found");
                return true;
            }

            try {
                plugin.getVipActiveService().activeVip(vip, player, Long.valueOf(args[2]), Long.valueOf(args[3]), Long.valueOf(args[4]));
                String activateMessage = vip.getBroadcastActivation().replace("%player%", player.getName());
                player.sendMessage(activateMessage);
                return true;
            } catch (Exception e) {
                sender.sendMessage(plugin.getMessageUtils().getMessage("error_add_vip"));
                return false;
            }

        } else {
            sender.sendMessage(plugin.getMessageUtils().getMessage("no_permission"));
        }
        return false;
    }
}
