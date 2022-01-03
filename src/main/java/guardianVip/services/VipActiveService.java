package guardianVip.services;

import guardianVip.GuardianVips;
import guardianVip.entity.UserVip;
import guardianVip.entity.Vip;
import guardianVip.entity.VipActive;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class VipActiveService {

    private GuardianVips plugin;
    private UserService userService;

    public VipActiveService(GuardianVips plugin) {
        this.plugin = plugin;
        this.userService = plugin.getUserService();
    }

    public VipActive activeVip(Vip vip, Player player, Long days) {
        VipActive vipActive = new VipActive(vip);
        boolean isActive = vipActive.activeVip(days);
        if (!isActive) return null;
        UserVip userVip = userService.getUserVip(player.getName());
        userVip.getVipsActivated().add(vipActive);
        executeActivationCommands(vip, player);

        return vipActive;
    }

    public void removeVipExpired(UserVip userVip, Player player) {
        userVip.getVipsActivated().forEach(vipActive -> {
            if (vipActive.getExpiredAt().isBefore(LocalDateTime.now())) {
                removeVip(vipActive.getVip(), player);
            }
        });
    }

    public void removeVip(Vip vip, Player player) {
        UserVip userVip = userService.getUserVip(player.getName());
        List<VipActive> vipsToRemove = userVip.getVipsActivated().stream()
                .filter(vipActive -> vipActive.getVip().equals(vip)).collect(Collectors.toList());
        vipsToRemove.forEach(vipToRemove -> {
            int index = userVip.getVipsActivated().indexOf(vipToRemove);
            userVip.getVipsActivated().remove(index);
            executeRemoveCommands(vipToRemove.getVip(), player);
        });
    }

    private void executeActivationCommands(Vip vip, Player player) {
        vip.getCommandsActivationVip().forEach(command -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
        });
    }

    private void executeRemoveCommands(Vip vip, Player player) {
        vip.getCommandsRemovelVip().forEach(command -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
        });
    }

}
