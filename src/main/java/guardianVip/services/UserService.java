package guardianVip.services;

import guardianVip.entity.UserVip;
import guardianVip.repositories.UserVipRepository;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class UserService {

    private UserVipRepository userVipRepository;

    public UserService(UserVipRepository userVipRepository) {
        this.userVipRepository = userVipRepository;
    }

    public UserVip create(Player player) {
        UserVip userVip = new UserVip();
        userVip.setName(player.getName());
        userVip.setUuid(player.getUniqueId());
        addUserToMap(userVip);
        return userVip;
    }

    public UserVip loadUserVip(Player player) {
        UserVip userVip = userVipRepository.seletcByName(player.getName());

        if (userVip == null ){
            //busca no banco de dados
            // userVip = retorno do banco
            if (userVip == null) {
                userVip = create(player);
            }
            addUserToMap(userVip);
        }

        return userVip;
    }

    public void addUserToMap(UserVip userVip) {
        userVipRepository.getAll().put(userVip.getName(), userVip);
    }

    public UserVip getUserVip(String name) {
        return userVipRepository.seletcByName(name);
    }

    public Map<String, UserVip> getUserVipMap() {
        return userVipRepository.getAll();
    }
}
