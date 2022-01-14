package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import guardianVip.entity.KeyVip;
import guardianVip.entity.Vip;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KeyGenerateCommand implements CommandExecutor {
    private GuardianVips plugin;

    public KeyGenerateCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.hasPermission("guardianvips.key.generate")  || sender.hasPermission("guardianvips.admin")) {
            if (args.length != 5) {
                sendDefaultCommandExample(sender);
                return false;
            }
            try {

                Vip vipByName = plugin.getVipService().getVipByName(args[0]);
                if (vipByName != null) {
                    Long daysVip = Long.valueOf(args[1]);
                    Long hours = Long.valueOf(args[2]);
                    Long minutes = Long.valueOf(args[3]);
                    Long remainingUse = Long.valueOf(args[4]);

                    KeyVip keyVip = plugin.getKeysService().generate(vipByName, daysVip, hours, minutes, remainingUse, "*");
                    sender.sendMessage("Key:" +
                            "\nVip:" + keyVip.getVipName() +
                            "\nTime" + keyVip.getDays() + "days " + keyVip.getHours() + " hours " + keyVip.getMinutes() + " minutes" +
                            "\nkey: " + keyVip.getKey() +
                            "\nkey alpha: " + keyVip.getKeyString() +
                            "\nremainingUse: " + keyVip.getRemainingUse() +
                            "\nactive: " + keyVip.isEnable());
                } else {
                    sender.sendMessage(plugin.getMessageUtils().getMessage("vip_not_found"));
                }
            } catch (Exception e) {
                sendDefaultCommandExample(sender);
                return false;
            }

            return false;

        }  else {
            sender.sendMessage(plugin.getMessageUtils().getMessage("no_permission"));
        }
        return false;
    }

    private void sendDefaultCommandExample(CommandSender sender) {
        sender.sendMessage(plugin.getMessageUtils().replaceColorSimbol("&cCommand error, use: Use /keygenerate <Vip> <Days> <Hours> <Minutes> <NumUsage>"));
    }
}
