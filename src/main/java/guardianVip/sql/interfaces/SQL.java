package guardianVip.sql.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface SQL {

    void start() throws SQLException;
    void close();
    Connection getConnection();

    PreparedStatement prepareStatement(String query) throws SQLException;
    ResultSet query(String query) throws SQLException;
    ResultSet query(PreparedStatement preparedStatement) throws SQLException;
    void execute(PreparedStatement preparedStatement) throws SQLException;
    void executeAsync(PreparedStatement preparedStatement);

    void createTable(String table, String values) throws SQLException;

}
