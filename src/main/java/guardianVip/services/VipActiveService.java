package guardianVip.services;

import guardianVip.GuardianVips;
import guardianVip.dtos.ActiveVipDTO;
import guardianVip.entity.UserVip;
import guardianVip.entity.Vip;
import guardianVip.entity.VipActive;
import guardianVip.utils.ActiveVipType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VipActiveService {

    private GuardianVips plugin;

    public VipActiveService(GuardianVips plugin) {
        this.plugin = plugin;
    }

    public VipActive activeVip(ActiveVipDTO activeVipDTO) {

        Vip vip = activeVipDTO.getVip();
        VipActive vipActive = createVipActive(activeVipDTO, vip);
        Long days = activeVipDTO.getDays();
        Long hours = activeVipDTO.getHours();
        Long minutes = activeVipDTO.getMinutes();
        boolean isActive = vipActive.activeVip(days, hours, minutes);

        if (!isActive) return null;

        UserVip userVip = plugin.getUserService().getUserVip(activeVipDTO.getPlayerName());
        if (userVip == null) {
            userVip = plugin.getUserService().create(activeVipDTO.getPlayerName(), activeVipDTO.getUuid());
        }
        setVipOrAddDays(userVip, vipActive, days, hours, minutes, activeVipDTO.getActiveVipType());
        executeCommandGroup(vip, activeVipDTO.getPlayerName());

        if (activeVipDTO.getActiveVipType().equals(ActiveVipType.ADD)) {
            executeActivationCommands(vip, activeVipDTO.getPlayerName());
            String activateMessage = vip.getBroadcastActivation().replace("%player%", activeVipDTO.getPlayerName()).replace("%vip%", vip.getName());
            sendActiveVipMessage(activateMessage);
        }

        plugin.getUserService().saveUserVip(activeVipDTO.getPlayerName());
        return vipActive;
    }

    private VipActive createVipActive(ActiveVipDTO activeVipDTO, Vip vip) {
        VipActive vipActive = new VipActive(vip);
        if (activeVipDTO.getEterna()) {
            vipActive.setEternal(true);
        }
        return vipActive;
    }

    public void addVipToAllUserVip(Vip vip, Long days, Long hours, Long minutes) {
        plugin.getUserService().loadAllUserVip();
        plugin.getUserService().getUserVipMap().values().forEach(userVip -> {
            userVip.getVipsActivated().forEach(vipActive -> {
                vipActive.addDays(days);
                vipActive.addHours(hours);
                vipActive.addMinutes(minutes);
            });
        });

        plugin.getUserService().removeOfflineUserVip();
    }

    public void setVipOrAddDays(UserVip userVip, VipActive vipActive, Long days, Long hours, Long minutes,ActiveVipType activeVipType) {
        VipActive vipInActivatedList = userVip.getVipsActivated()
                .stream().filter(vipActivated ->
                        vipActivated.getVip().getName().equals(vipActive.getVip().getName())
                ).findFirst().orElse(null);
        if (vipInActivatedList == null) {
            userVip.getVipsActivated().add(vipActive);
        } else {
            if (vipActive.getEternal()) {
                vipInActivatedList.setEternal(true);
            } else {
                if (activeVipType.equals(ActiveVipType.SET)) {
                    vipInActivatedList.activeVip(days, hours, minutes);
                } else {
                    vipInActivatedList.addDays(days);
                    vipInActivatedList.addHours(hours);
                    vipInActivatedList.addMinutes(minutes);
                }

            }
        }
        plugin.getUserService().saveUserVip(userVip.getName());
    }

    public void removeVipExpired(UserVip userVip, Player player) {
        List<VipActive> vipsToRemove = new ArrayList<>();
        userVip.getVipsActivated().forEach(vipActive -> {
            if (vipActive.getExpiredAt().isBefore(LocalDateTime.now()) && !vipActive.getEternal()) {
                vipsToRemove.add(vipActive);
            }
        });

        vipsToRemove.forEach(vipActive -> removeVip(vipActive.getVip(), player));

        plugin.getUserService().saveUserVip(userVip.getName());
    }


    public boolean removeVipOfflinePlayer(Vip vip, String player) {
        UserVip userVip = plugin.getUserService().getUserVip(player);
        if (userVip == null) return false;
        removeVip(vip, player, userVip);
        return true;
    }

    public void removeVip(Vip vip, Player player) {
        UserVip userVip = plugin.getUserService().getUserVip(player);
        if (userVip == null) return;
        removeVip(vip, player.getName(), userVip);
    }

    private void removeVip(Vip vip, String player, UserVip userVip) {
        List<VipActive> vipsToRemove = userVip.getVipsActivated().stream()
                .filter(vipActive -> vipActive.getVip().getName().equals(vip.getName())).collect(Collectors.toList());
        vipsToRemove.forEach(vipToRemove -> {
            int index = userVip.getVipsActivated().indexOf(vipToRemove);
            userVip.getVipsActivated().remove(index);
            executeRemoveCommands(vipToRemove.getVip(), player);
        });

        if (userVip.getVipsActivated().size() == 0) {
            plugin.getUserService().removeVipToDatabase(userVip.getName());
        }
    }


    private void executeCommandGroup(Vip vip, String player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), vip.getCommandGroup().replace("%player%", player));
    }

    private void executeActivationCommands(Vip vip, String player) {
        vip.getCommandsActivationVip().forEach(command -> {
            try {
                command = command.replace("%player%", player);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(plugin.getMessageUtils().replaceColorSimbol("&cImpossible execute command :" + command));
            }
        });
    }

    private void executeRemoveCommands(Vip vip, String player) {
        vip = plugin.getVipService().getVipByName(vip.getName());
        vip.getCommandsRemovelVip().forEach(command -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player));
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
            plugin.getUserService().saveUserVip(userVip.getName());
        });
    }

    public void notifyVipsOnline() {
        plugin.getUserService().getUserVipMap().values().forEach(userVip -> {
            Player playerExact = Bukkit.getPlayerExact(userVip.getName());
            if (userVip.getVipsActivated().size() > 0 && playerExact != null) {
                userVip.getVipsActivated().forEach(vipActive -> {
                    Player player = Bukkit.getPlayerExact(userVip.getName());
                    if (ChronoUnit.HOURS.between(LocalDateTime.now(), vipActive.getExpiredAt()) < plugin.getYamlConfig().getConfigFile().getInt("minDyasToNotify")) {
                        player.sendMessage(plugin.getMessageUtils().getMessage("vip_time_ending"));
                    }
                });
            }
            plugin.getUserService().saveUserVip(userVip.getName());
        });
    }

    private List<VipActive> getVipsToRemove(UserVip userVip) {
        if (!userVip.getVipsActivated().isEmpty()) {
            return userVip.getVipsActivated().stream().filter(
                    vipActive -> vipActive.getExpiredAt().isBefore(LocalDateTime.now()) && !vipActive.getEternal())
                    .collect(Collectors.toList());
        }
        return null;
    }

}
