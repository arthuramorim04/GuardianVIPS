package guardianVip.commands;

import guardianVip.GuardianVips;
import guardianVip.entity.UserVip;
import guardianVip.entity.VipActive;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class VipTimeCommand implements CommandExecutor {

    private final GuardianVips plugin;

    public VipTimeCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof ConsoleCommandSender)) {
            UserVip userVip = plugin.getUserService().getUserVip((Player) commandSender);

            if (args.length == 0) {
                if (userVip == null || userVip.getVipsActivated() == null || userVip.getVipsActivated().isEmpty()) {
                    commandSender.sendMessage(plugin.getMessageUtils().getMessage("player_no_have_vips"));
                    return true;
                }
                printVipActiveList(userVip.getVipsActivated(), commandSender);
                return true;
            } else {
                if (commandSender.hasPermission("guardianvips.showvips")) {
                    Player playerExact = Bukkit.getPlayerExact(args[0]);
                    if (playerExact != null) {
                        UserVip playerUserVip = plugin.getUserService().getUserVip(playerExact);
                        if (playerUserVip == null || playerUserVip.getVipsActivated() == null || playerUserVip.getVipsActivated().isEmpty()) {
                            commandSender.sendMessage(plugin.getMessageUtils().getMessage("player_no_have_vips"));
                            return true;
                        }
                        commandSender.sendMessage(plugin.getMessageUtils().replaceColorSimbol("\n&aPlayer: " + playerExact.getName() + "\n"));
                        printVipActiveList(playerUserVip.getVipsActivated(), commandSender);

                    } else {
                        commandSender.sendMessage(plugin.getMessageUtils().getMessage("player_not_found"));
                        return true;
                    }
                }
            }

        } else {
            commandSender.sendMessage("Command avaliable only players");
        }
        return false;
    }

    private void printVipActiveList(List<VipActive> vipActives, CommandSender sender) {
        vipActives.forEach(vipActive -> {
            sender.sendMessage(plugin.getMessageUtils().getMessage("viptime_line")
                    .replace("%vip%", vipActive.getVip().getName())
                    .replace("%days%",String.valueOf(vipActive.getDays()))
                    .replace("%hours%", String.valueOf(vipActive.getHours()))
                    .replace("%minutes%", String.valueOf(vipActive.getMinutes())));
    });
    }
}
