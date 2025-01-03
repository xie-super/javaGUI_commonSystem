package scaffolding.gui.dal.config;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import scaffolding.gui.common.util.TransferStringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DB {

    private static String databaseName ;
    private static String username ;
    private static String password ;
    private static Connection conn = null;
    static {
        databaseName = "dormitories_system";
        username = "dormitories_system";
        password = "dAxrwXchtSM7YYNB";
    }
    public static Connection getConn() {
        if(conn!=null){
            return conn;
        }
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = String.format("jdbc:mysql://8.141.119.45:3306/%s?serverTimezone=UTC", databaseName);
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeQuietly(AutoCloseable... closeables) {
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



    //实现所有类型的插入操作
    public static <T> boolean insert(T entity) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConn();
            // 获取实体类的所有字段
            Field[] fields = entity.getClass().getDeclaredFields();
            // 获取实体类的类名并转换为小写，和数据库中表名字对应
            String tableName = entity.getClass().getSimpleName().toLowerCase();
            // 构建SQL语句
            StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
            StringBuilder values = new StringBuilder(") VALUES (");
            for (Field field : fields) {
                //传入类变量只有有值才能更新，获取有值的变量
                field.setAccessible(true); // 设置字段可访问
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

            // 创建PreparedStatement并设置参数
            preparedStatement = connection.prepareStatement(sql.toString());

            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true); // 设置字段可访问
                Object value = field.get(entity);

                if (value != null) {
                    preparedStatement.setObject(parameterIndex++, value);
                }
            }
            // 执行插入操作
            if(preparedStatement.executeUpdate()!=0){
                return true;
            }
            return false;
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
            System.err.println("SQL Exception: " + e.getMessage());
            return false;
        } finally {
            connection.close();
        }
    }


    // 通用的 update 方法，传入的是实体以及字段名（根据该字段找数据）
    public static <T> boolean update(T entity, String fieldName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConn();
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
            sql.append(" WHERE ").append(fieldName).append("=?");

            // 创建PreparedStatement并设置参数
            preparedStatement = connection.prepareStatement(sql.toString());

            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true); // 设置字段可访问
                Object value = field.get(entity);

                if (value != null) {
                    preparedStatement.setObject(parameterIndex++, value);
                }
            }

            // 设置 WHERE 子句的参数
            Field targetField = entity.getClass().getDeclaredField(fieldName);
            targetField.setAccessible(true); // 设置字段可访问
            Object targetValue = targetField.get(entity);
            preparedStatement.setObject(parameterIndex, targetValue);

            // 执行更新操作
            if(preparedStatement.executeUpdate()!=0){
                return true;
            }
            return false;

        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            // 输出SQL异常信息
            System.err.println("SQL Exception: " + e.getMessage());
            return false; // 返回插入失败的标志
        } finally {
            connection.close();
        }
    }

    // 简化的 select 方法，传入类型实例，以及 where后的限制字段  select * from (entity的类名) where fieldName = (entity实例的fieldName值)返回传入类型的链表
    //若field == ""则返回表中所有记录
    @SuppressWarnings("raw")
    public static <T> List<T> select(T entity, String... fieldNames) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConn();
            // 获取实体类的类名并转换为小写
            String tableName = TransferStringUtils.toCamelCase(entity.getClass().getSimpleName());
            // 构建基本的SQL语句
            StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName);

            // 检查是否有WHERE条件
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

            // 如果有WHERE条件，通过反射设置参数值
            for (int i = 0; i < fieldNames.length; i++) {
                if (!fieldNames[i].isEmpty()) {
                    // 通过反射获取实体对象的字段值
                    Field field = entity.getClass().getDeclaredField(fieldNames[i]);
                    field.setAccessible(true); // 设置字段可访问
                    Object value = field.get(entity);

                    preparedStatement.setObject(i + 1, value);
                }
            }

            // 执行查询操作
            resultSet = preparedStatement.executeQuery();

            // 创建实体对象列表
            List<T> resultList = new ArrayList<>();

            // 获取查询结果并设置实体对象的属性
            while (resultSet.next()) {
                T resultEntity = (T) entity.getClass().getDeclaredConstructor().newInstance();
                Field[] fields = resultEntity.getClass().getDeclaredFields();
                for (Field resultField : fields) {
                    resultField.setAccessible(true); // 设置字段可访问
                    Object resultValue = resultSet.getObject(resultField.getName());
                    resultField.set(resultEntity, resultValue);
                }
                resultList.add(resultEntity);
            }

            return resultList;

        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException | NoSuchFieldException | InvocationTargetException e) {
            e.printStackTrace();
            // 输出异常信息
            System.err.println("Exception: " + e.getMessage());
            return new ArrayList<>(); // 返回空列表或其他标志
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }


    // 通用的 delete 方法
    public static <T> boolean delete(T entity, String fieldName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConn();// 获取数据库连接，这里是你的实现

            // 获取实体类的类名并转换为小写
            String tableName = entity.getClass().getSimpleName().toLowerCase();

            // 构建SQL语句
            System.out.println(tableName);
            String sql = "DELETE FROM " + tableName + " WHERE " + fieldName + "=?";

            // 创建PreparedStatement并设置参数
            preparedStatement = connection.prepareStatement(sql);

            // 通过反射获取实体对象的字段值
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true); // 设置字段可访问
            Object value = field.get(entity);
            System.out.println(value.toString());
            preparedStatement.setObject(1, value);

            int falg = preparedStatement.executeUpdate();
            System.out.println(falg);
            // 执行删除操作
            if(falg!=0){
                return true;
            }
            return false;

        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            // 输出异常信息
            System.err.println("Exception: " + e.getMessage());
            return false; // 返回删除失败的标志
        } finally {
            connection.close();
        }
    }
    public static void main(String[] args) throws Exception {

        //update(student,"id");
    }
}
