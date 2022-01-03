package guardianVip.services;

import guardianVip.GuardianVips;
import guardianVip.entity.Vip;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class VipService {

    private final List<Vip> vips = new ArrayList<>();

    private final GuardianVips plugin;

    public VipService(GuardianVips plugin) {
        this.plugin = plugin;
    }


    public void loadVipListOnFile() {
        vips.clear();
        FileConfiguration configFile = plugin.getYamlVipConfig().getConfigFile();
        configFile.getKeys(false).forEach(vipKey -> {

            try {
                String nameVip = configFile.getString(vipKey + ".name");
                List<String> commandsActivationVip = configFile.getStringList(vipKey + ".commandsActivationVip");
                List<String> commandsRemoveVip = configFile.getStringList(vipKey + ".commandsRemovelVip");
                String activationBroadcast = configFile.getString(vipKey + ".activationBroadcast");

                Vip vip = new Vip();
                vip.setName(nameVip);
                vip.setCommandsActivationVip(commandsActivationVip);
                vip.setCommandsRemovelVip(commandsRemoveVip);
                vip.setBroadcastActivation(activationBroadcast);

                vips.add(vip);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public List<Vip> getVips() {
        return vips;
    }

    public Vip getVipByName(String vipName) {
        return vips.stream().filter(vip -> vip.getName().equals(vipName)).findFirst().orElse(null);
    }
}
