package guardianVip.commands;

import guardianVip.GuardianVips;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class PlayerCommands implements CommandExecutor {

    private GuardianVips plugin;

    public PlayerCommands(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage("sistema vip");
        return false;
    }
}
