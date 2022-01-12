package guardianVip.commands;

import guardianVip.GuardianVips;
import guardianVip.entity.UserVip;
import guardianVip.entity.VipActive;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


public class VipTimeCommand implements CommandExecutor {

    private final GuardianVips plugin;

    public VipTimeCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof ConsoleCommandSender)) {
            UserVip userVip = plugin.getUserService().getUserVip((Player) commandSender);

            if (userVip == null || userVip.getVipsActivated() == null || userVip.getVipsActivated().isEmpty()) {
                commandSender.sendMessage("Nao possui vip");
                return true;
            }
            userVip.getVipsActivated().forEach(vipActive -> {
                commandSender.sendMessage(vipActive.getVip().getName() + ": " + vipActive.getDays());
            });
        } else {
            commandSender.sendMessage("Comando disponivel apenas para jogadores");
        }
        return false;
    }
}
