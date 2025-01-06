package scaffolding.gui.dal.database.connector;

import lombok.extern.slf4j.Slf4j;
import scaffolding.gui.common.util.ConfigManager;
import scaffolding.gui.dal.database.DatabaseConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @description 工厂方法具体Product，sqlserver连接器
 * @author 乐滨
 * @date 2025-01-06
 */
@Slf4j
public class SqlServerConnector implements DatabaseConnector {

    private static Connection conn = null;
    @Override
    public Connection getConnection() throws IOException {
        if(conn!=null){
            return conn;
        }
        ConfigManager configManager = new ConfigManager("application.properties");
        String dbName = configManager.getProperty("database.name");
        String dbUsername = configManager.getProperty("database.username");
        String dbPassword = configManager.getProperty("database.password");
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = String.format("jdbc:mysql://8.141.119.45:3306/%s?serverTimezone=UTC", dbName);
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, dbUsername, dbPassword);
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException", e);
        } catch (SQLException e) {
            log.error("SQLException", e);
        }
        return conn;
    }
    @Override
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
