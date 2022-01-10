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

        sender.sendMessage("Guardian Vips\n/ativarvip <key> - Ativar uma chave de vip\n/tempovip - Mostrar sua lista de vips ativas");
        return false;
    }




}
