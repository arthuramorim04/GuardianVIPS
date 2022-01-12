package guardianVip.services;

import guardianVip.GuardianVips;
import guardianVip.entity.KeyVip;
import guardianVip.entity.Vip;
import guardianVip.utils.KeysUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.IOException;

public class KeysService {

    private GuardianVips plugin;

    public KeysService(GuardianVips plugin) {
        this.plugin = plugin;
    }

    public KeyVip generate(Vip vip, Long days, Long hours, Long minutes, Long usage) {
        try {
            KeyVip keyVip = create(vip, days, hours, minutes, usage);
            saveKey(keyVip);
            return keyVip;
        } catch (Exception e) {
            return null;
        }
    }

    public void activeKey(KeyVip keyVip, Player player) {
        if (keyVip != null && keyVip.isEnable() && keyVip.getRemainingUse() > 0) {
            Vip vip = plugin.getVipService().getVipByName(keyVip.getVipName());
            plugin.getVipActiveService().activeVip(vip, player, keyVip.getDays(), keyVip.getHours(), keyVip.getMinutes());
            keyVip.setRemainingUse(keyVip.getRemainingUse() - 1);
            ConfigurationSection section = plugin.getKeysVips().getConfigFile().getConfigurationSection(String.valueOf(keyVip.getKey()));
            section.set("remainingUse", keyVip.getRemainingUse());
            try {
                plugin.getKeysVips().getConfigFile().save(plugin.getKeysVips().getConfig());
            } catch (Exception e) {
                System.out.println("error save keys.yml");
            }
        }
    }

    public KeyVip create(Vip vip, Long days, Long hours, Long minutes, Long remainingUse) {
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
        keyVip.setHours(hours);
        keyVip.setMinutes(minutes);
        keyVip.setRemainingUse(remainingUse);
        keyVip.setEnable(true);
        return keyVip;
    }

    public void deleteKey(String key) {
        try {
            ConfigurationSection section = plugin.getKeysVips().getConfigFile().getConfigurationSection(key);
            section.set("isEnable", false);
            plugin.getKeysVips().getConfigFile().save(plugin.getKeysVips().getConfig());
        } catch (Exception e) {
            return;
        }
    }

    public KeyVip getKeyVip(String key) {
        try {
            ConfigurationSection section = plugin.getKeysVips().getConfigFile().getConfigurationSection(key);

            String vipName = section.getString("vipName");
            long keyNumeric = section.getLong("keyNumeric");
            String keyAlpha = section.getString("keyAlpha");
            long days = section.getLong("days");
            long hours = section.getLong("hours");
            long minutes = section.getLong("minutes");
            long remainingUse = section.getLong("remainingUse");
            boolean isEnable = section.getBoolean("isEnable");
            KeyVip keyVip = new KeyVip(keyNumeric, keyAlpha, vipName, days,hours, minutes,remainingUse, isEnable);
            return keyVip;
        }catch (Exception e) {
            return null;
        }
    }

    public void saveKey(KeyVip keyVip) throws IOException {
        String sectionName = String.valueOf(keyVip.getKey());
        plugin.getKeysVips().getConfigFile().createSection(sectionName);

        ConfigurationSection section = plugin.getKeysVips().getConfigFile().getConfigurationSection(sectionName);

        section.set("vipName", keyVip.getVipName());
        section.set("keyNumeric", keyVip.getKey());
        section.set("keyAlpha", keyVip.getKeyString());
        section.set("days", keyVip.getDays());
        section.set("hours", keyVip.getHours());
        section.set("minutes", keyVip.getMinutes());
        section.set("remainingUse", keyVip.getRemainingUse());
        section.set("isEnable", keyVip.isEnable());

        plugin.getKeysVips().getConfigFile().save(plugin.getKeysVips().getConfig());
    }
}
