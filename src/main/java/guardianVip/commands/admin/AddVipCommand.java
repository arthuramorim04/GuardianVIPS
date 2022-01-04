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

        if (args.length != 3) {
            sender.sendMessage("Command error, use: /addvip <Player> <VIP> <Days>");
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

        plugin.getVipActiveService().activeVip(vip, player, Long.valueOf(args[2]));

        player.sendMessage(vip.getBroadcastActivation().replace("%player%", player.getName()));
        return false;
    }
}
