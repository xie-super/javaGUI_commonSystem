package scaffolding.gui.dal.database.connector;

import lombok.extern.slf4j.Slf4j;
import scaffolding.gui.common.util.ConfigManager;
import scaffolding.gui.common.util.TransferStringUtils;
import scaffolding.gui.dal.database.DatabaseConnector;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description 工厂方法具体Product，mysql连接器
 * @author 乐滨
 * @date 2025-01-06
 */
@Slf4j
public class MysqlConnector implements DatabaseConnector {
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

    @Override
    public  void closeQuietly(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception ignored) {
                    log.error("close error",ignored);
                }
            }
        }
    }

    @SuppressWarnings("raw")
    @Override
    public <T> boolean insert(T entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            Field[] fields = entity.getClass().getDeclaredFields();
            String tableName = TransferStringUtils.toCamelCase(entity.getClass().getSimpleName());

            // 构建SQL语句
            StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
            StringBuilder values = new StringBuilder(") VALUES (");
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (value != null) {
                    sql.append(field.getName()).append(",");
                    values.append("?,");
                }
            }
            // 移除末尾的逗号
            sql.deleteCharAt(sql.length() - 1);
            values.deleteCharAt(values.length() - 1);
            // 拼接最终的SQL语句
            sql.append(values).append(")");

            preparedStatement = connection.prepareStatement(sql.toString());
            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (value != null) {
                    preparedStatement.setObject(parameterIndex++, value);
                }
            }
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException | IllegalAccessException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(preparedStatement);

        }
    }


    // 通用的 update 方法，传入的是实体以及字段名（根据该字段找数据）
    @Override
    public <T> boolean update(T entity, String... fieldNames)  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            Field[] fields = entity.getClass().getDeclaredFields();
            String tableName = TransferStringUtils.toCamelCase(entity.getClass().getSimpleName());

            StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");

            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(entity);

                if (value != null) {
                    sql.append(field.getName()).append("=?,");
                }
            }

            // 移除末尾的逗号
            sql.deleteCharAt(sql.length() - 1);

            // 添加 WHERE 子句
            if (fieldNames.length > 0 && !fieldNames[0].isEmpty()) {
                sql.append(" WHERE ");
                // 添加每个字段名和对应的值
                for (int i = 0; i < fieldNames.length; i++) {
                    if (i > 0) {
                        sql.append(" AND ");
                    }
                    sql.append(fieldNames[i]).append("=?");
                }
            }
            // 创建PreparedStatement并设置参数
            preparedStatement = connection.prepareStatement(sql.toString());

            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true); 
                Object value = field.get(entity);

                if (value != null) {
                    preparedStatement.setObject(parameterIndex++, value);
                }
            }

            // 设置 WHERE 子句的参数
            for (String fieldName : fieldNames) {
                if (!fieldName.isEmpty()) {
                    // 通过反射获取实体对象的字段值
                    Field field = entity.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    preparedStatement.setObject(parameterIndex++, value);
                }
            }

            // 执行更新操作
            return preparedStatement.executeUpdate() != 0;

        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            closeQuietly(preparedStatement);
        }
    }

    // 简化的 select 方法，传入类型实例，以及 where后的限制字段  select * from (entity的类名) where fieldName = (entity实例的fieldName值)返回传入类型的链表
    //若field == ""则返回表中所有记录
    @SuppressWarnings("raw")
    @Override
    public <T> List<T> select(T entity, String... fieldNames) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection =  getConnection();
            String tableName = TransferStringUtils.toCamelCase(entity.getClass().getSimpleName());
            // 构建基本的SQL语句
            StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName);
            if (Objects.nonNull(fieldNames) && fieldNames.length > 0 && !fieldNames[0].isEmpty()) {
                sql.append(" WHERE ");
                // 添加每个字段名和对应的值
                for (int i = 0; i < fieldNames.length; i++) {
                    if (i > 0) {
                        sql.append(" AND ");
                    }
                    sql.append(fieldNames[i]).append("=?");
                }
            }

            // 创建PreparedStatement并设置参数
            preparedStatement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < fieldNames.length; i++) {
                if (!fieldNames[i].isEmpty()) {
                    // 通过反射获取实体对象的字段值
                    Field field = entity.getClass().getDeclaredField(fieldNames[i]);
                    field.setAccessible(true);
                    Object value = field.get(entity);

                    preparedStatement.setObject(i + 1, value);
                }
            }
            resultSet = preparedStatement.executeQuery();
            List<T> resultList = new ArrayList<>();

            // 获取查询结果并设置实体对象的属性
            while (resultSet.next()) {
                T resultEntity = (T) entity.getClass().getDeclaredConstructor().newInstance();
                Field[] fields = resultEntity.getClass().getDeclaredFields();
                for (Field resultField : fields) {
                    resultField.setAccessible(true); 
                    Object resultValue = resultSet.getObject(resultField.getName());
                    resultField.set(resultEntity, resultValue);
                }
                resultList.add(resultEntity);
            }
            return resultList;
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException |
                 NoSuchFieldException | InvocationTargetException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(resultSet, preparedStatement);

        }
    }


    @Override
    public  <T> boolean delete(T entity, String... fieldNames) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            String tableName = TransferStringUtils.toCamelCase(entity.getClass().getSimpleName());

            StringBuilder sql = new StringBuilder("DELETE FROM " + tableName);
            if (fieldNames.length > 0 && !fieldNames[0].isEmpty()) {
                sql.append(" WHERE ");
                // 添加每个字段名和对应的值
                for (int i = 0; i < fieldNames.length; i++) {
                    if (i > 0) {
                        sql.append(" AND ");
                    }
                    sql.append(fieldNames[i]).append("=?");
                }
            }
            // 创建PreparedStatement并设置参数
            preparedStatement = connection.prepareStatement(sql.toString());

            for (int i = 0; i < fieldNames.length; i++) {
                if (!fieldNames[i].isEmpty()) {
                    Field field = entity.getClass().getDeclaredField(fieldNames[i]);
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    preparedStatement.setObject(i + 1, value);
                }
            }
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException | IllegalAccessException | NoSuchFieldException | IOException e) {
            throw new RuntimeException(e);
        } finally {

            closeQuietly(preparedStatement);
        }
    }


}
