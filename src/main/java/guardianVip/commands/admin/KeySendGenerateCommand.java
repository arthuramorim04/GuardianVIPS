package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import guardianVip.entity.KeyVip;
import guardianVip.entity.Vip;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
        if (sender.hasPermission("guardianvips.key.generate") || sender.hasPermission("guardianvips.admin")) {
            if (args.length != 6) {
                sendDefaultCommandExample(sender);
                return false;
            }
            try {
                String playerName = null;
                Player playerExact = Bukkit.getPlayerExact(args[5]);
                if (playerExact == null) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[5]);
                    if (offlinePlayer.getName() != null && !offlinePlayer.getName().isEmpty()) {
                        playerName = offlinePlayer.getName();
                    }
                } else {
                    playerName = playerExact.getName();
                }
                if (playerName != null && !playerName.isEmpty()) {
                    Vip vipByName = plugin.getVipService().getVipByName(args[0]);
                    if (vipByName != null) {
                        Long daysVip = Long.valueOf(args[1]);
                        Long hours = Long.valueOf(args[2]);
                        Long minutes = Long.valueOf(args[3]);
                        Long remainingUse = Long.valueOf(args[4]);

                        KeyVip keyVip = plugin.getKeysService().generate(vipByName, daysVip, hours, minutes, remainingUse, playerName);

                        if (playerExact != null && playerExact.isOnline()) {
                            sendKeyVipToSenderMessage(playerExact, keyVip, plugin);
                        }
                        sendKeyVipToSenderMessage(sender, keyVip, plugin);
                    } else {
                        sender.sendMessage(plugin.getMessageUtils().getMessage("vip_not_found"));
                    }
                    return true;
                } else {
                    sender.sendMessage(plugin.getMessageUtils().getMessage("player_not_found"));
                    return true;
                }

            } catch (Exception e) {
                sendDefaultCommandExample(sender);
                return false;
            }
        } else {
            sender.sendMessage(plugin.getMessageUtils().getMessage("no_permission"));
            return false;
        }
    }

    static void sendKeyVipToSenderMessage(CommandSender sender, KeyVip keyVip, GuardianVips plugin) {
        String line_vip_key = plugin.getMessageUtils().getMessage("line_vip_key")
                .replace("%vip%", String.valueOf(keyVip.getVipName()))
                .replace("%days%", String.valueOf(keyVip.getDays()))
                .replace("%hours%", String.valueOf(keyVip.getHours()))
                .replace("%minutes%", String.valueOf(keyVip.getMinutes()))
                .replace("%key%", String.valueOf(keyVip.getKey()))
                .replace("%key_alpha%", String.valueOf(keyVip.getKeyString()))
                .replace("%status%", String.valueOf(keyVip.isEnable()))
                .replace("%owner%", String.valueOf(keyVip.getPlayer()));
        sender.sendMessage(line_vip_key);
    }

    private void sendDefaultCommandExample(CommandSender sender) {
        sender.sendMessage(plugin.getMessageUtils().replaceColorSimbol("&cCommand error, use: /gerarkeyvip <VIP> <Days> <Hours> <Minutes> <Usage> <Player>"));
    }
}
