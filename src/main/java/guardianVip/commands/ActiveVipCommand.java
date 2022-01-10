package guardianVip.commands;

import guardianVip.GuardianVips;
import guardianVip.entity.KeyVip;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ActiveVipCommand implements CommandExecutor {

    private GuardianVips plugin;

    public ActiveVipCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Use /ativarvip <Key>");
            return false;
        }

        KeyVip keyVip = plugin.getKeysService().getKeyVip(args[0]);
        if (keyVip != null) {
            plugin.getKeysService().activeKey(keyVip, (Player) sender);
        }
        return false;
    }
}
