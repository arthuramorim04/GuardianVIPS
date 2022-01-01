package guardianVip;

import guardianVip.sql.DatabaseManager;
import guardianVip.utils.YamlConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class GuardianVips extends JavaPlugin {

    private YamlConfig yamlConfig;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        super.onEnable();
        initConfig();
        initDatabase();
        openConnection();
    }

    @Override
    public void onDisable() {
        this.closeConnection();
        super.onDisable();
    }


    private void initConfig() {
        yamlConfig = new YamlConfig(this,"config");
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
}
