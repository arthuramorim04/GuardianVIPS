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

    public VipActiveService(GuardianVips plugin) {
        this.plugin = plugin;
    }

    public VipActive activeVip(Vip vip, Player player, Long days, Long hours, Long minutes) {
        VipActive vipActive = new VipActive(vip);
        boolean isActive = vipActive.activeVip(days, hours, minutes);
        if (!isActive) return null;
        UserVip userVip = plugin.getUserService().getUserVip(player.getName());
        if (userVip == null) {
            userVip = plugin.getUserService().create(player);
        }
        setVipOrAddDays(userVip, vipActive, days);
        executeActivationCommands(vip, player);

        String activateMessage = vip.getBroadcastActivation().replace("%player%", player.getName());
        sendActiveVipMessage(activateMessage);
        return vipActive;
    }

    public void setVipOrAddDays(UserVip userVip, VipActive vipActive, Long days) {
        VipActive vipInActivatedList = userVip.getVipsActivated()
                .stream().filter(vipActivated ->
                        vipActivated.getVip().getName().equals(vipActive.getVip().getName())
                ).findFirst().orElse(null);
        if (vipInActivatedList == null) {
            userVip.getVipsActivated().add(vipActive);
            return;
        } else {
            vipInActivatedList.addDays(days);
        }
    }

    public void removeVipExpired(UserVip userVip, Player player) {
        userVip.getVipsActivated().forEach(vipActive -> {
            if (vipActive.getExpiredAt().isBefore(LocalDateTime.now())) {
                removeVip(vipActive.getVip(), player);
            }
        });
    }

    public void removeVip(Vip vip, Player player) {
        UserVip userVip = plugin.getUserService().getUserVip(player.getName());
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

    private void sendActiveVipMessage(String message){

        Bukkit.getOnlinePlayers().forEach(player -> plugin.getMessageUtils().sendTitle(player, message));
    }

    public void removeExpiredVipsIfN() {
        plugin.getUserService().getUserVipMap().values().forEach(userVip -> {
            Player playerExact = Bukkit.getPlayerExact(userVip.getName());
            if (userVip.getVipsActivated().size() > 0 && playerExact != null) {
                List<VipActive> vipsToRemove = getVipsToRemove(userVip);
                vipsToRemove.forEach(vipExpired -> removeVipExpired(userVip, playerExact));
            }
        });
    }

    private List<VipActive> getVipsToRemove(UserVip userVip) {
        if (!userVip.getVipsActivated().isEmpty()) {
            return userVip.getVipsActivated().stream().filter(
                    vipActive -> vipActive.getExpiredAt().isBefore(LocalDateTime.now()))
                    .collect(Collectors.toList());
        }
        return null;
    }

}
