package scaffolding.gui.dal.database.factory;

import scaffolding.gui.common.constants.Constants;
import scaffolding.gui.common.util.ConfigManager;
import scaffolding.gui.dal.database.DatabaseConnector;
import scaffolding.gui.dal.database.connector.MysqlConnector;
import scaffolding.gui.dal.database.connector.SqlServerConnector;

import java.io.IOException;

/**
 * @description 简单工厂
 * @author 乐滨
 * @date 2025-01-06
 */
public class DataBaseFactory {
    public static DatabaseConnector getConnector() throws IOException {
        ConfigManager configManager = new ConfigManager("application.properties");
        String dbType = configManager.getProperty("database.type");
        if (Constants.DatabaseType.MYSQL.equals(dbType)) {
            return new MysqlConnector();
        } else if (Constants.DatabaseType.SQLSERVER.equals(dbType)) {
            return new SqlServerConnector();
        }
        throw new RuntimeException("不支持的数据库类型");
    }
}
