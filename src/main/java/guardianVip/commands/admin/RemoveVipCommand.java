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

        if (sender.hasPermission("guardianvips.removevip")) {

            if (args.length != 2) {
                sender.sendMessage("Command error, use: /removevip <Player> <VIP>");
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

            plugin.getVipActiveService().removeVip(vip, player);

            player.sendMessage("Sua vip " + vip.getName() + "foi removida");

        } else {
            sender.sendMessage("Você não possui permissão");
        }
        return false;
    }
}
