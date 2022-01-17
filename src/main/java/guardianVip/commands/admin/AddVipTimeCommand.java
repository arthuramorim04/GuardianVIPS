package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import guardianVip.dtos.ActiveVipDTO;
import guardianVip.entity.UserVip;
import guardianVip.entity.Vip;
import guardianVip.entity.VipActive;
import guardianVip.utils.ActiveVipType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;

public class AddVipTimeCommand implements CommandExecutor {

    private GuardianVips plugin;

    public AddVipTimeCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender.hasPermission("guardianvips.addtimevip") || sender.hasPermission("guardianvips.admin")) {
            if (args.length != 5) {
                sendDefaultCommandExample(sender);
                return true;
            }

            Vip vip = plugin.getVipService().getVipByName(args[1]);
            if (vip == null) {
                sender.sendMessage(plugin.getMessageUtils().getMessage("vip_not_found"));
                return true;
            }

            if (!args[0].equals("*")) {
                try {
                    Player player = Bukkit.getPlayerExact(args[0]);
                    ActiveVipDTO activeVipDTO = null;
                    if (player == null) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                        if (offlinePlayer == null) {
                            sender.sendMessage(plugin.getMessageUtils().getMessage("player_not_found"));
                            return true;
                        } else {
                            activeVipDTO = new ActiveVipDTO(vip, offlinePlayer.getName(), offlinePlayer.getUniqueId(), Long.valueOf(args[2]), Long.valueOf(args[3]), Long.valueOf(args[4]), ActiveVipType.PLUS);
                            activeVipDTO.setEterna(false);
                        }

                    } else {
                        activeVipDTO = new ActiveVipDTO(vip, player, Long.valueOf(args[2]), Long.valueOf(args[3]), Long.valueOf(args[4]), ActiveVipType.PLUS);
                        activeVipDTO.setEterna(false);
                    }
                    plugin.getVipActiveService().activeVip(activeVipDTO);
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(plugin.getMessageUtils().getMessage("error_add_vip"));
                    sendDefaultCommandExample(sender);
                    return false;
                }
            } else {
                try {
                    plugin.getVipActiveService().addVipToAllUserVip(vip, Long.valueOf(args[2]), Long.valueOf(args[3]), Long.valueOf(args[4]));
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(plugin.getMessageUtils().getMessage("error_add_vip"));
                    sendDefaultCommandExample(sender);
                    return false;
                }
            }

        } else {
            sender.sendMessage(plugin.getMessageUtils().getMessage("no_permission"));
        }

        return false;
    }

    private void sendDefaultCommandExample(CommandSender sender) {
        sender.sendMessage(plugin.getMessageUtils().replaceColorSimbol("&cCommand error, use: /addtempovip <Player or *> <Days> <Hours> <Minutes>"));
    }
}
