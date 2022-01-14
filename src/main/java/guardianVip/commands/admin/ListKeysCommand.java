package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import guardianVip.entity.KeyVip;
import guardianVip.entity.Vip;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ListKeysCommand implements CommandExecutor {
    private GuardianVips plugin;

    public ListKeysCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.hasPermission("guardianvips.key.list")) {
            if (args.length != 1) {
                try{
                    List<KeyVip> allKeys = plugin.getKeysService().getAllKeys();
                    allKeys.forEach(keyVip -> {
                        sender.sendMessage("Key:" +
                                "\nVip:" + keyVip.getVipName() +
                                "\nTime" + keyVip.getDays() + "days " + keyVip.getHours() + " hours " + keyVip.getMinutes() + " minutes" +
                                "\nkey: " + keyVip.getKey() +
                                "\nkey alpha: " + keyVip.getKeyString() +
                                "\nremainingUse: " + keyVip.getRemainingUse() +
                                "\nactive: " + keyVip.isEnable() +
                                "\nOwner: " + keyVip.getPlayer());
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return false;
            }

            if (args.length == 1) {
                try{
                    List<KeyVip> allKeys = plugin.getKeysService().getAllKeys();
                    List<KeyVip> allKeysByPlayer = allKeys.stream().filter(keyVip -> keyVip.getPlayer().equals(args[0])).collect(Collectors.toList());
                    allKeysByPlayer.forEach(keyVip -> {
                        plugin.getMessageUtils().getMessage("line_vip_key")
                                .replace("%vip%", String.valueOf(keyVip.getVipName()))
                                .replace("%days%", String.valueOf(keyVip.getDays()))
                                .replace("%hours%", String.valueOf(keyVip.getHours()))
                                .replace("%minutes%", String.valueOf(keyVip.getMinutes()))
                                .replace("%key%", String.valueOf(keyVip.getKey()))
                                .replace("%key_alpha%", String.valueOf(keyVip.getKeyString()))
                                .replace("%status%", String.valueOf(keyVip.isEnable()))
                                .replace("%owner%", String.valueOf(keyVip.getPlayer()));
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }


            return false;

        }  else {
            sender.sendMessage(plugin.getMessageUtils().getMessage("no_permission"));
        }
        return false;
    }
}
