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
        if (sender.hasPermission("guardianvips.key.generate")) {
//            /keygenerate <Vip> <Days> <Usage>
            if (args.length != 3) {
                sender.sendMessage("Use /keygenerate <Vip> <Days> <NumUsage>");
            }

            Vip vipByName = plugin.getVipService().getVipByName(args[0]);
            Long daysVip = Long.valueOf(args[1]);
            Long usage = Long.valueOf(args[2]);

            KeyVip keyVip = plugin.getKeysService().generate(vipByName, daysVip, usage);
            sender.sendMessage(keyVip.toString());
        }
        return false;
    }
}
