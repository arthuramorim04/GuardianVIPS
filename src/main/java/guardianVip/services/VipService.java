package guardianVip.services;

import guardianVip.GuardianVips;
import guardianVip.entity.Vip;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class VipService {

    private List<Vip> vips = new ArrayList<>();

    private GuardianVips plugin;

    public VipService(GuardianVips plugin) {
        this.plugin = plugin;
    }


    public void loadVipListOnFile() {
        FileConfiguration configFile = plugin.getYamlVipConfig().getConfigFile();
        configFile.getKeys(false).forEach(vipKey -> {

            String nameVip = configFile.getString(vipKey + ".name");
            List<String> commandsActivationVip = configFile.getStringList(vipKey + ".commandsActivationVip");
            System.out.println(commandsActivationVip);
        });
    }
}
