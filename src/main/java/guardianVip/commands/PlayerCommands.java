package guardianVip.commands;

import guardianVip.GuardianVips;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;


public class PlayerCommands implements CommandExecutor {

    private GuardianVips plugin;

    public PlayerCommands(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(plugin.getMessageUtils().getMessage("player_help"));
        return false;
    }




}
