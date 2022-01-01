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
        vips.clear();
        FileConfiguration configFile = plugin.getYamlVipConfig().getConfigFile();
        configFile.getKeys(false).forEach(vipKey -> {

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
        });
    }

    public List<Vip> getVips() {
        return vips;
    }
}
