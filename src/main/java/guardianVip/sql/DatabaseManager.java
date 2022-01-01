package guardianVip.sql;

import guardianVip.sql.interfaces.SQL;
import guardianVip.sql.providers.MySQL;
import guardianVip.sql.providers.SQLite;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

@Getter
public class DatabaseManager {

    private JavaPlugin plugin;
    private SQL sql;

    private String connectionType;

    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initMySQL(String host, String database, String user, String password) {
        synchronized (plugin) {
            sql = new MySQL(plugin,
                    host,
                    database,
                    user,
                    password
            );
            this.connectionType = "MYSQL";
        }
    }

    public void initSQL(String archiveName) {
        synchronized (plugin) {
            sql = new SQLite(plugin, "database.db");
            this.connectionType = "SQLITE";
        }
    }

    public DatabaseManager startConnection() {
        try {
            sql.start();
            plugin.getLogger().info(String.format("Connection successful with %s database.", connectionType));
            return this;
        } catch (SQLException e) {
            plugin.getLogger().severe(String.format("Connection failed with %s database! Disabling plugin.", connectionType));
            e.printStackTrace();
            return this;
        }
    }

    public void closeConnection() {
        sql.close();
        plugin.getLogger().info(String.format("Connection closed to %s database.", connectionType));
    }


}
