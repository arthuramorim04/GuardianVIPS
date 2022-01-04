package guardianVip.repositories;

import guardianVip.entity.UserVip;
import guardianVip.repositories.interfaces.RepositoryInterface;

import java.util.HashMap;
import java.util.Map;

public class UserVipRepository implements RepositoryInterface<UserVip, String> {

    private Map<String, UserVip> userVips = new HashMap<>();


    @Override
    public UserVip selectById(Long id) {
        return null;
    }

    @Override
    public Map<String, UserVip> getAll() {
        return userVips;
    }

    @Override
    public UserVip seletcByName(String key) {
        return userVips.get(key);
    }
}
