package guardianVip;

import guardianVip.commands.PlayerCommands;
import guardianVip.services.UserService;
import guardianVip.services.VipActiveService;
import guardianVip.services.VipService;
import guardianVip.sql.DatabaseManager;
import guardianVip.utils.YamlConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class GuardianVips extends JavaPlugin {

    private YamlConfig yamlConfig;
    private YamlConfig yamlVipConfig;
    private DatabaseManager databaseManager;

    private VipService vipService;
    private UserService userService;
    private VipActiveService vipActiveService;

    @Override
    public void onEnable() {
        super.onEnable();
        initConfig();
        vipService = new VipService(this);
        vipService.loadVipListOnFile();
        System.out.println(vipService.getVips());
        getCommand("guardianvips").setExecutor(new PlayerCommands(this));
//        initDatabase();
//        openConnection();
    }

    @Override
    public void onDisable() {
        this.closeConnection();
        super.onDisable();
    }


    private void initConfig() {
        yamlConfig = new YamlConfig(this,"config");
        yamlVipConfig = new YamlConfig(this, "vips");
    }

    private void initDatabase() {
        String user = yamlConfig.getConfigFile().getString("storage.user");
        String pass = yamlConfig.getConfigFile().getString("storage.pass");
        String host = yamlConfig.getConfigFile().getString("storage.host");
        String db = yamlConfig.getConfigFile().getString("storage.db");
        Integer port = yamlConfig.getConfigFile().getInt("storage.port");
        databaseManager = new DatabaseManager(this);
        databaseManager.initMySQL(host, db, user, pass);
    }

    private void openConnection() {
            databaseManager.startConnection();
    }

    private void closeConnection() {
            databaseManager.closeConnection();
    }

    public YamlConfig getYamlConfig() {
        return yamlConfig;
    }

    public YamlConfig getYamlVipConfig() {
        return yamlVipConfig;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public VipService getVipService() {
        return vipService;
    }

    public UserService getUserService() {
        return userService;
    }

    public VipActiveService getVipActiveService() {
        return vipActiveService;
    }
}
