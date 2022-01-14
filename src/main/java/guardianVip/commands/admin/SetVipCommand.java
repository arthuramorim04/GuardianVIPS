package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import guardianVip.dtos.ActiveVipDTO;
import guardianVip.entity.Vip;
import guardianVip.utils.ActiveVipType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetVipCommand implements CommandExecutor {

    private GuardianVips plugin;

    public SetVipCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("guardianvips.setvip") || sender.hasPermission("guardianvips.admin")) {

            if(args.length == 3) {
                String time = args[2];
                if (time.equals("eterno")) {
                    processAddRequest(sender, args, ActiveVipType.SET, true);
                    return true;
                }
            }

            if (args.length != 5) {
                sendDefaultCommandExample(sender);
                return true;
            } else {
                return processAddRequest(sender, args, ActiveVipType.SET, false);
            }


        } else {
            sender.sendMessage(plugin.getMessageUtils().getMessage("no_permission"));
        }
        return false;
    }

    private boolean processAddRequest(CommandSender sender, String[] args, ActiveVipType activeVipType, Boolean isEternal) {
        Vip vip = getVip(sender, args);
        if (vip == null) return true;

        return addVip(sender, args, vip, activeVipType, isEternal);
    }

    private boolean addVip(CommandSender sender, String[] args, Vip vip, ActiveVipType activeVipType, Boolean isEternal) {
        try {
            Player playerExact = Bukkit.getPlayerExact(args[0]);
            ActiveVipDTO activeVipDTO = null;
            if (playerExact != null) {
                if (isEternal) {
                    activeVipDTO = new ActiveVipDTO(vip, playerExact, 0L, 0L, 0L, activeVipType);
                    activeVipDTO.setEterna(true);
                } else {
                    activeVipDTO = new ActiveVipDTO(vip, playerExact, Long.valueOf(args[2]), Long.valueOf(args[3]), Long.valueOf(args[4]), activeVipType);
                    activeVipDTO.setEterna(false);
                }
                activeVipDTO.setPlayerName(playerExact.getName());
                activeVipDTO.setUuid(playerExact.getUniqueId());
            } else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                if (offlinePlayer.getUniqueId() != null) {
                    if (isEternal) {
                        activeVipDTO = new ActiveVipDTO(vip, offlinePlayer.getName(), offlinePlayer.getUniqueId(), 0L, 0L, 0L, activeVipType);
                        activeVipDTO.setEterna(true);
                    } else {
                        activeVipDTO = new ActiveVipDTO(vip, offlinePlayer.getName(), offlinePlayer.getUniqueId(), Long.valueOf(args[2]), Long.valueOf(args[3]), Long.valueOf(args[4]), activeVipType);
                        activeVipDTO.setEterna(false);
                    }
                } else {
                    sender.sendMessage(plugin.getMessageUtils().getMessage("player_not_found"));
                    return false;
                }
            }
            plugin.getVipActiveService().activeVip(activeVipDTO);
            return true;
        } catch (Exception e) {
            sender.sendMessage(plugin.getMessageUtils().getMessage("error_add_vip"));
            sendDefaultCommandExample(sender);
            return false;
        }
    }

    private Vip getVip(CommandSender sender, String[] args) {
        Vip vip = plugin.getVipService().getVipByName(args[1]);
        if (vip == null) {
            sender.sendMessage(plugin.getMessageUtils().getMessage("vip_not_found"));
            return null;
        }
        return vip;
    }

    private void sendDefaultCommandExample(CommandSender sender) {
        sender.sendMessage(plugin.getMessageUtils().replaceColorSimbol("&cCommand error, use: /setvip <Player> <VIP> <Days> <Hours> <Minutes> "));
    }
}
