package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class ListVipCommand implements CommandExecutor {

    private GuardianVips plugin;

    public ListVipCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> vipsName = plugin.getVipService().getVips().stream().map(vip -> vip.getName()).collect(Collectors.toList());
        commandSender.sendMessage(String.valueOf(vipsName));
        return false;
    }
}
