package guardianVip.sql.providers;

import lombok.Getter;
import org.bukkit.Bukkit;
import guardianVip.sql.interfaces.SQL;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public abstract class SQLImpl implements SQL {

    private JavaPlugin plugin;
    private Connection connection;

    public SQLImpl(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void start() {
        try {
            this.connection = createConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (getConnection() != null && !getConnection().isClosed())
                getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return getConnection().prepareStatement(query);
    }

    @Override
    public ResultSet query(String query) throws SQLException {
        return prepareStatement(query).executeQuery();
    }

    @Override
    public ResultSet query(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    @Override
    public void execute(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
    }

    @Override
    public void executeAsync(PreparedStatement preparedStatement) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void createTable(String table, String values) throws SQLException {
        prepareStatement(String.format("CREATE TABLE IF NOT EXISTS %s (%s);", table, values)).execute();
    }

    public abstract Connection createConnection() throws SQLException, ClassNotFoundException;

}
