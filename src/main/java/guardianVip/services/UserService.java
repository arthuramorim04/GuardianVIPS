package guardianVip.services;

import guardianVip.GuardianVips;
import guardianVip.entity.UserVip;
import guardianVip.repositories.UserVipRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserService {

    private UserVipRepository userVipRepository;
    private GuardianVips plugin;

    public UserService(GuardianVips plugin) {
        this.plugin = plugin;
        this.userVipRepository = new UserVipRepository(plugin);
    }

    public UserVip create(String player, UUID uuid) {
        UserVip userVip = new UserVip();
        userVip.setName(player);
        userVip.setUuid(uuid);
        userVipRepository.create(userVip);
        return userVip;
    }

    public UserVip loadUserVip(Player player) {
        UserVip userVip = userVipRepository.seletcByName(player);

        if (userVip == null ){
             userVip = userVipRepository.selectById(player.getUniqueId().toString());
            if (userVip == null) {
                userVip = create(player.getName(), player.getUniqueId());
            }
        }
        plugin.getVipActiveService().removeVipExpired(userVip, player);
        return userVip;
    }

    public Map<String, UserVip> loadAllUserVip() {
        userVipRepository.loadAll();
        return userVipRepository.getAll();
    }

    public void removeOfflineUserVip() {
        userVipRepository.getAll().keySet().forEach(userVip -> {
            userVipRepository.saveUserVip(userVip);
        });
        List<String> usersOffline = userVipRepository.getAll().keySet().stream().filter(user -> !Bukkit.getPlayerExact(user).isOnline()).collect(Collectors.toList());
        usersOffline.forEach(userOffline -> {
            userVipRepository.unloadUserVip(userOffline);
        });
    }

    public void saveUserVip(Player player) {
        userVipRepository.saveUserVip(player.getName());
    }

    public void saveUserVip(String player) {
        userVipRepository.saveUserVip(player);
        Player playerExact = Bukkit.getPlayerExact(player);
        if (playerExact == null || !playerExact.isOnline()) {
            removeUserVipOnMap(player);
        }
    }

    public void removeVipToDatabase(String name) {
        userVipRepository.removeUserToDB(name);
    }

    public void removeUserVipOnMap(String name) {
        userVipRepository.removeUserVipOnMap(name);
    }

    public UserVip getUserVip(String player) {
        return userVipRepository.seletcByName(player);
    }

    public UserVip getUserVip(Player player) {
        return userVipRepository.seletcByName(player);
    }

    public Map<String, UserVip> getUserVipMap() {
        return userVipRepository.getAll();
    }
}
