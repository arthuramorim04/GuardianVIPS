package guardianVip.sql.providers;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class SQLite extends SQLImpl {

    private final JavaPlugin plugin;
    private final String filename;

    public SQLite(JavaPlugin plugin, String filename) {
        super(plugin);
        this.plugin = plugin;
        this.filename = filename;
    }

    @Override
    public Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(String.format("jdbc:sqlite:plugins/%s/%s.db", plugin.getName(), filename));
    }

}
