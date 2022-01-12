package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import guardianVip.entity.KeyVip;
import guardianVip.entity.Vip;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KeySendGenerateCommand implements CommandExecutor {
    private GuardianVips plugin;

    public KeySendGenerateCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.hasPermission("guardianvips.key.generate")) {
            if (args.length != 6) {
                sender.sendMessage("Use /keygenerate <VIP> <Days> <Hours> <Minutes> <Player>");
                return false;
            }
            try{
                Player playerExact = Bukkit.getPlayerExact(args[5]);
                if (playerExact != null && playerExact.isOnline()) {
                    Vip vipByName = plugin.getVipService().getVipByName(args[0]);
                    if (vipByName != null) {
                        Long daysVip = Long.valueOf(args[1]);
                        Long hours = Long.valueOf(args[2]);
                        Long minutes = Long.valueOf(args[3]);
                        Long remainingUse = Long.valueOf(args[4]);

                        KeyVip keyVip = plugin.getKeysService().generate(vipByName, daysVip, hours, minutes, remainingUse, playerExact.getName());
                        sender.sendMessage("Key:" +
                                "\nVip:" + keyVip.getVipName() +
                                "\nTime" + keyVip.getDays() + "days " + keyVip.getHours() + " hours " + keyVip.getMinutes() + " minutes" +
                                "\nkey: " + keyVip.getKey() +
                                "\nkey alpha: " + keyVip.getKeyString() +
                                "\nremainingUse: " + keyVip.getRemainingUse() +
                                "\nactive: " + keyVip.isEnable());
                    } else {
                        sender.sendMessage(plugin.getMessageUtils().getMessage("vip_not_found"));
                        return false;
                    }
                } else {
                    sender.sendMessage(plugin.getMessageUtils().getMessage("player_not_found"));
                    return true;
                }

            } catch (Exception e) {
                sender.sendMessage("Use /keygenerate <Vip> <Days> <NumUsage>");
                return false;
            }

            return false;

        }  else {
            sender.sendMessage(plugin.getMessageUtils().getMessage("no_permission"));
        }
        return false;
    }
}
