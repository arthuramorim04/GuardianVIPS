package guardianVip.commands;

import guardianVip.GuardianVips;
import guardianVip.entity.UserVip;
import guardianVip.entity.VipActive;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class VipTimeCommand implements CommandExecutor {
    private GuardianVips plugin;

    public VipTimeCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        UserVip userVip = plugin.getUserService().getUserVip(commandSender.getName());

        if (userVip == null || userVip.getVipsActivated() == null || userVip.getVipsActivated().isEmpty()) {
            commandSender.sendMessage("Nao possui vip");
            return true;
        }
        userVip.getVipsActivated().forEach(vipActive -> {
            commandSender.sendMessage(vipActive.getVip().getName() + ": " + vipActive.getDays());
        });
        return false;
    }
}
