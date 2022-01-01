package guardianVip.sql.providers;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends SQLImpl {

    private final String host, database, user, password;
    public MySQL(JavaPlugin plugin, String host, String database, String user, String password) {
        super(plugin);
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        final String url = "jdbc:mysql://" + host + "/" + database + "?autoReconnect=true";
        return DriverManager.getConnection(
                url,
                user,
                password
        );
    }
}
