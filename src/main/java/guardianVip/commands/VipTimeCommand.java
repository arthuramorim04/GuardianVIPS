package guardianVip.commands;

import guardianVip.GuardianVips;
import guardianVip.entity.UserVip;
import guardianVip.entity.VipActive;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.List;


public class VipTimeCommand implements CommandExecutor {

    private final GuardianVips plugin;

    public VipTimeCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (args.length == 0 && commandSender instanceof Player) {
            UserVip userVip = plugin.getUserService().getUserVip(commandSender.getName());
            if (userVip == null || userVip.getVipsActivated() == null || userVip.getVipsActivated().isEmpty()) {
                commandSender.sendMessage(plugin.getMessageUtils().getMessage("player_no_have_vips"));
                return true;
            }
            plugin.getVipActiveService().removeVipExpired(userVip, commandSender.getName());
            printVipActiveList(userVip.getVipsActivated(), commandSender);
            return true;
        }
        if (args.length == 1) {
            if (commandSender.hasPermission("guardianvips.showvips") || commandSender.hasPermission("guardianvips.admin")) {
                Player playerExact = Bukkit.getPlayerExact(args[0]);
                if (playerExact != null) {
                    UserVip playerUserVip = plugin.getUserService().getUserVip(playerExact);
                    if (playerUserVip == null || playerUserVip.getVipsActivated() == null || playerUserVip.getVipsActivated().isEmpty()) {
                        commandSender.sendMessage(plugin.getMessageUtils().getMessage("player_no_have_vips"));
                        return true;
                    }
                    plugin.getVipActiveService().removeVipExpired(playerUserVip, commandSender.getName());
                    commandSender.sendMessage(plugin.getMessageUtils().replaceColorSimbol("\n&aPlayer: " + playerExact.getName() + "\n"));
                    printVipActiveList(playerUserVip.getVipsActivated(), commandSender);

                } else {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                    if (offlinePlayer.getName() != null && offlinePlayer.getUniqueId() != null) {
                        UserVip playerUserVip = plugin.getUserService().getUserVip(offlinePlayer.getName());
                        if (playerUserVip == null || playerUserVip.getVipsActivated() == null || playerUserVip.getVipsActivated().isEmpty()) {
                            commandSender.sendMessage(plugin.getMessageUtils().getMessage("player_no_have_vips"));
                            return true;
                        }
                        commandSender.sendMessage(plugin.getMessageUtils().replaceColorSimbol("\n&aPlayer: " + offlinePlayer.getName() + "\n"));
                        printVipActiveList(playerUserVip.getVipsActivated(), commandSender);
                        plugin.getUserService().removeUserVipOnMap(offlinePlayer.getName());
                    } else {
                        commandSender.sendMessage(plugin.getMessageUtils().getMessage("player_not_found"));
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void printVipActiveList(List<VipActive> vipActives, CommandSender sender) {
        vipActives.forEach(vipActive -> {
            if (vipActive.getEternal()) {
                sender.sendMessage(plugin.getMessageUtils().getMessage("viptime_eternal_line")
                        .replace("%vip%", vipActive.getVip().getName()));
            } else {
                if(vipActive.getExpiredAt().isAfter(LocalDateTime.now())) {
                    sender.sendMessage(plugin.getMessageUtils().getMessage("viptime_line")
                            .replace("%vip%", vipActive.getVip().getName())
                            .replace("%days%", String.valueOf(vipActive.getDays()))
                            .replace("%hours%", String.valueOf(vipActive.getHours()))
                            .replace("%minutes%", String.valueOf(vipActive.getMinutes())));
                }
            }
        });
    }
}
