package guardianVip.services;

import guardianVip.GuardianVips;
import guardianVip.dtos.ActiveVipDTO;
import guardianVip.entity.KeyVip;
import guardianVip.entity.Vip;
import guardianVip.utils.ActiveVipType;
import guardianVip.utils.KeysUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KeysService {

    private GuardianVips plugin;

    public KeysService(GuardianVips plugin) {
        this.plugin = plugin;
    }

    public KeyVip generate(Vip vip, Long days, Long hours, Long minutes, Long usage, String player) {        try {
            KeyVip keyVip = create(vip, days, hours, minutes, usage, player);
            saveKey(keyVip);
            return keyVip;
        } catch (Exception e) {
            return null;
        }
    }

    public void activeKey(KeyVip keyVip, Player player) {
        if (keyVip != null && keyVip.isEnable() && keyVip.getRemainingUse() > 0) {
            Vip vip = plugin.getVipService().getVipByName(keyVip.getVipName());
            ActiveVipDTO activeVipDTO = new ActiveVipDTO(vip, player.getName(), player.getUniqueId(), keyVip.getDays(), keyVip.getHours(), keyVip.getMinutes(), ActiveVipType.ADD);
            plugin.getVipActiveService().activeVip(activeVipDTO);
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

    public KeyVip create(Vip vip, Long days, Long hours, Long minutes, Long remainingUse, String player) {
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
        keyVip.setPlayer(player);
        return keyVip;
    }


    public boolean deleteKey(String key) throws IllegalAccessException, IOException {
        try {
            disableKey(key);
            return true;
        } catch (NullPointerException e) {
            Long convert = KeysUtils.convert(key);
            disableKey(String.valueOf(convert));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void disableKey(String key) throws IOException {
        ConfigurationSection section = plugin.getKeysVips().getConfigFile().getConfigurationSection(key);
        section.set("isEnable", false);
        plugin.getKeysVips().getConfigFile().save(plugin.getKeysVips().getConfig());
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
            String player = section.getString("player");
            KeyVip keyVip = new KeyVip(keyNumeric, keyAlpha, vipName, days,hours, minutes,remainingUse, isEnable, player);
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
        section.set("player", keyVip.getPlayer());

        plugin.getKeysVips().getConfigFile().save(plugin.getKeysVips().getConfig());
    }

    public List<KeyVip> getAllKeys() {
        List<KeyVip> keyVips = new ArrayList<>();
        plugin.getKeysVips().getConfigFile().getKeys(true).forEach(key -> {
            KeyVip keyVip = getKeyVip(key);
            keyVips.add(keyVip);
        });
        return keyVips;
    }
}
