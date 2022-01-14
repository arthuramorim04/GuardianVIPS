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
            if (args.length == 0) {
                List<KeyVip> allKeys = plugin.getKeysService().getAllKeys();
                if (sender.hasPermission("guardianvips.key.list") || sender.hasPermission("guardianvips.admin")) {
                    showListKeys(allKeys, plugin, sender);
                    return false;
                } else {
                    String name = sender.getName();
                    showListByName(sender, allKeys, name);
                }
            }

            if (args.length == 1) {
                if (sender.hasPermission("guardianvips.key.list") || sender.hasPermission("guardianvips.admin")) {
                    List<KeyVip> allKeys = plugin.getKeysService().getAllKeys();
                    List<KeyVip> allKeysByPlayer = allKeys.stream().filter(keyVip -> keyVip != null && keyVip.getPlayer().equals(args[0])).collect(Collectors.toList());
                    String name = args[0];
                    showListByName(sender, allKeysByPlayer, name);
                } else {
                    sender.sendMessage(plugin.getMessageUtils().getMessage("no_permission"));
                }
                return true;
            }

            return false;
    }

    private void showListByName(CommandSender sender, List<KeyVip> allKeys, String name) {
        List<KeyVip> allKeysByPlayer = allKeys.stream().filter(keyVip -> keyVip != null && keyVip.getPlayer().equals(name)).collect(Collectors.toList());
        showListKeys(allKeysByPlayer, plugin, sender);
    }

    private void showListKeys(List<KeyVip> allKeys, GuardianVips plugin, CommandSender sender) {
        allKeys.forEach(keyVip -> {
            try {
                KeySendGenerateCommand.sendKeyVipToSenderMessage(sender, keyVip, plugin);
            } catch (Exception e) {

            }
        });
    }

}
