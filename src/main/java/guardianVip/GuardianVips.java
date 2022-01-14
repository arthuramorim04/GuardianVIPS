package guardianVip;

import guardianVip.commands.ActiveVipCommand;
import guardianVip.commands.PlayerCommands;
import guardianVip.commands.VipTimeCommand;
import guardianVip.commands.admin.*;
import guardianVip.listeners.PlayerListener;
import guardianVip.repositories.UserVipRepository;
import guardianVip.services.KeysService;
import guardianVip.services.UserService;
import guardianVip.services.VipActiveService;
import guardianVip.services.VipService;
import guardianVip.sql.DatabaseManager;
import guardianVip.tasks.RemoveExpiredVips;
import guardianVip.utils.MessageUtils;
import guardianVip.utils.YamlConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class GuardianVips extends JavaPlugin {

    private YamlConfig yamlConfig;
    private YamlConfig yamlVipConfig;
    private YamlConfig messageConfig;
    private YamlConfig keysVips;

    private DatabaseManager databaseManager;

    private VipService vipService;
    private UserService userService;
    private VipActiveService vipActiveService;
    private KeysService keysService;

    private MessageUtils messageUtils;

    private RemoveExpiredVips removeExpiredVips;

    @Override
    public void onEnable() {
        super.onEnable();
        initConfig();
        initDatabase();
        openConnection();
        initServices();
        initCommands();
        initListeners();
        initialLoad();
        initTasks();
    }

    @Override
    public void onLoad() {
        super.onLoad();

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
        keysVips = new YamlConfig(this, "keys");
    }

    private void initServices() {
        vipService = new VipService(this);
        vipActiveService = new VipActiveService(this);
        userService = new UserService(this);
        messageUtils = new MessageUtils(this);
        keysService = new KeysService(this);
    }

    private void initialLoad() {
        vipService.loadVipListOnFile();
    }

    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    private void initCommands() {
        getCommand("guardianvip").setExecutor(new PlayerCommands(this));
        getCommand("gerarkeyvip").setExecutor(new KeyGenerateCommand(this));
        getCommand("enviarkeyvip").setExecutor(new KeySendGenerateCommand(this));
        getCommand("removerkeyvip").setExecutor(new RemoveKeyCommand(this));
        getCommand("listarkeysvip").setExecutor(new ListKeysCommand(this));
        getCommand("ativarvip").setExecutor(new ActiveVipCommand(this));
        getCommand("darvip").setExecutor(new AddVipCommand(this));
        getCommand("setvip").setExecutor(new SetVipCommand(this));
        getCommand("removervip").setExecutor(new RemoveVipCommand(this));
        getCommand("tempovip").setExecutor(new VipTimeCommand(this));
        getCommand("listvip").setExecutor(new ListVipCommand(this));
        getCommand("addTempovipglobal").setExecutor(new AddVipTimeCommand(this));


    }

    private void initDatabase() {
        String type = yamlConfig.getConfigFile().getString("storage.type");
        String user = yamlConfig.getConfigFile().getString("storage.user");
        String pass = yamlConfig.getConfigFile().getString("storage.pass");
        String host = yamlConfig.getConfigFile().getString("storage.host");
        String db = yamlConfig.getConfigFile().getString("storage.db");
        databaseManager = new DatabaseManager(this);
        if (type.equals("SQLite")) {
            databaseManager.initSQL(type);
        } else {
            databaseManager.initMySQL(host, db, user, pass);
        }
    }

    private void initTasks() {
        removeExpiredVips = new RemoveExpiredVips(this);
        removeExpiredVips.runTaskTimerAsynchronously(this, 0L, 20*600L);
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

    public YamlConfig getKeysVips() {
        return keysVips;
    }

    public KeysService getKeysService() {
        return keysService;
    }
}
