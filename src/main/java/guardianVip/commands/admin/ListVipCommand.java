package guardianVip.commands.admin;

import guardianVip.GuardianVips;
import guardianVip.entity.UserVip;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListVipCommand implements CommandExecutor {

    private GuardianVips plugin;

    public ListVipCommand(GuardianVips plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender.hasPermission("guardianvips.listvips") || commandSender.hasPermission("guardianvips.admin")){
            Map<String, UserVip> vipList = plugin.getUserService().loadAllUserVip();
            vipList.values().stream().filter(userVip -> userVip.getVipsActivated().size() > 0).forEach(userVip -> {
                commandSender.sendMessage(plugin.getMessageUtils().getMessage("vip_player_title_line").replace("%player%", userVip.getName()));
                userVip.getVipsActivated().forEach(vipActive -> {
                    if(vipActive.getExpiredAt().isAfter(LocalDateTime.now())) {
                        commandSender.sendMessage(plugin.getMessageUtils().getMessage("vip_player_info_line")
                                .replace("%vip%", String.valueOf(vipActive.getVip().getName()))
                                .replace("%days%", String.valueOf(vipActive.getDays()))
                                .replace("%hours%", String.valueOf(vipActive.getHours()))
                                .replace("%minutes%", String.valueOf(vipActive.getMinutes()))
                                .replace("%data_activation%", vipActive.getActivationDate().toString()));
                    }
                });
            });
        return true;
        }  else {
            commandSender.sendMessage(plugin.getMessageUtils().getMessage("no_permission"));
            return false;
        }
    }
}
