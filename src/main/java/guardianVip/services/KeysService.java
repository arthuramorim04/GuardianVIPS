package guardianVip.services;

import guardianVip.GuardianVips;
import guardianVip.entity.KeyVip;
import guardianVip.entity.Vip;
import guardianVip.utils.KeysUtils;
import org.bukkit.entity.Player;

import java.io.IOException;

public class KeysService {

    private GuardianVips plugin;

    public KeysService(GuardianVips plugin) {
        this.plugin = plugin;
    }

    public KeyVip generate(Vip vip, Long days, Long usage) {
        try {
            KeyVip keyVip = create(vip, days, usage);
            saveKey(keyVip);
            return keyVip;
        } catch (Exception e) {
            return null;
        }
    }

    public void activeKey(KeyVip keyVip, Player player) {
        if (keyVip != null && keyVip.getAllowedUsage() > 0) {
            Vip vip = plugin.getVipService().getVipByName(keyVip.getVipName());
            plugin.getVipActiveService().activeVip(vip, player, keyVip.getDays(), keyVip.getHours(), keyVip.getMinutes());
        }
    }

    public KeyVip create(Vip vip, Long days, Long quantityUsage) {
        long key = System.currentTimeMillis();
        KeyVip keyVip = new KeyVip();
        keyVip.setKey(key);
        String keyConverted;
        try {
            keyConverted = KeysUtils.convert(key);
        }  catch (IllegalAccessException ie) {
            System.out.println("Invalid Key");
            return null;
        }
        keyVip.setKeyString(keyConverted);
        keyVip.setVipName(vip.getName());
        keyVip.setDays(days);
        keyVip.setAllowedUsage(quantityUsage);
        return keyVip;
    }

    public void deleteKey(Long key) {

    }

    public KeyVip getKeyVip(String key) {
        try {
            String keySerialized = plugin.getKeysVips().getConfigFile().getString(key);
            KeyVip keyVip = new KeyVip();
            keyVip = keyVip.deserializeKey(keySerialized);
            return keyVip;
        }catch (Exception e) {
            return null;
        }
    }

    public void saveKey(KeyVip keyVip) throws IOException {
        plugin.getKeysVips().getConfigFile().set(String.valueOf(keyVip.getKey()), keyVip.serializeKey());
        plugin.getKeysVips().getConfigFile().save(plugin.getKeysVips().getConfig());
    }
}
