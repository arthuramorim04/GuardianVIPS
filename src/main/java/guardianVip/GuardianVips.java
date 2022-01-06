package guardianVip;

import guardianVip.commands.ActiveVipCommand;
import guardianVip.commands.PlayerCommands;
import guardianVip.commands.VipTimeCommand;
import guardianVip.commands.admin.*;
import guardianVip.repositories.UserVipRepository;
import guardianVip.services.UserService;
import guardianVip.services.VipActiveService;
import guardianVip.services.VipService;
import guardianVip.sql.DatabaseManager;
import guardianVip.utils.MessageUtils;
import guardianVip.utils.YamlConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class GuardianVips extends JavaPlugin {

    private YamlConfig yamlConfig;
    private YamlConfig yamlVipConfig;
    private YamlConfig messageConfig;
    private DatabaseManager databaseManager;

    private VipService vipService;
    private UserService userService;
    private VipActiveService vipActiveService;

    private MessageUtils messageUtils;

    private UserVipRepository userVipRepository;

    @Override
    public void onEnable() {
        super.onEnable();
        initConfig();
        initRepositories();
        initServices();
        initCommands();
//        initDatabase();
//        openConnection();
        initialLoad();
    }

    @Override
    public void onLoad() {
        super.onLoad();

        //recarrega vips
    }

    @Override
    public void onDisable() {
        this.closeConnection();
        super.onDisable();
    }


    private void initConfig() {
        yamlConfig = new YamlConfig(this,"config");
        yamlVipConfig = new YamlConfig(this, "vips");
        messageConfig = new YamlConfig(this, "messages");
    }

    private void initRepositories() {
        userVipRepository = new UserVipRepository();
    }

    private void initServices() {
        vipService = new VipService(this);
        vipActiveService = new VipActiveService(this);
        userService = new UserService(this.userVipRepository);
        messageUtils = new MessageUtils(this);
    }

    private void initialLoad() {
        vipService.loadVipListOnFile();
    }

    private void initCommands() {
        getCommand("guardianvips").setExecutor(new PlayerCommands(this));
        getCommand("addvip").setExecutor(new AddVipCommand(this));
        getCommand("gerarkey").setExecutor(new KeyGenerateCommand(this));

        getCommand("removekey").setExecutor(new RemoveKeyCommand(this));
        getCommand("removevip").setExecutor(new RemoveVipCommand(this));
        getCommand("activevip").setExecutor(new ActiveVipCommand(this));
        getCommand("viptime").setExecutor(new VipTimeCommand(this));
        getCommand("listvip").setExecutor(new ListVipCommand(this));
    }

    private void initDatabase() {
        String user = yamlConfig.getConfigFile().getString("storage.user");
        String pass = yamlConfig.getConfigFile().getString("storage.pass");
        String host = yamlConfig.getConfigFile().getString("storage.host");
        String db = yamlConfig.getConfigFile().getString("storage.db");
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

    public YamlConfig getMessageConfig() {
        return messageConfig;
    }

    public MessageUtils getMessageUtils() {
        return messageUtils;
    }
}
