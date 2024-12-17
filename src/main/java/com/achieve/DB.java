package com.achieve;





import com.achieve.entity.Student;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DB {
    public static Connection getConn() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://8.141.119.45:3306/gui?serverTimezone=UTC";
        String username = "gui";
        String password = "jHd5HpNx8dzj84RR";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
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
            // 输出SQL异常信息
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

            // 获取实体类的所有字段
            Field[] fields = entity.getClass().getDeclaredFields();

            // 获取实体类的类名并转换为小写
            String tableName = entity.getClass().getSimpleName().toLowerCase();

            // 构建SQL语句
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
    public static <T> List<T> select(T entity, String fieldName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConn();
            // 获取实体类的类名并转换为小写
            String tableName = entity.getClass().getSimpleName().toLowerCase();
            // 构建SQL语句
            String sql = "SELECT * FROM " + tableName;

            if (!fieldName.isEmpty()) {
                // If fieldName is not empty, add WHERE clause
                sql += " WHERE " + fieldName + "=?";
            }

            // 创建PreparedStatement并设置参数
            preparedStatement = connection.prepareStatement(sql);

            // If fieldName is not empty, set the parameter
            if (!fieldName.isEmpty()) {
                // 通过反射获取实体对象的字段值
                Field field = entity.getClass().getDeclaredField(fieldName);
                field.setAccessible(true); // 设置字段可访问
                Object value = field.get(entity);

                preparedStatement.setObject(1, value);
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
            connection.close();
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
    public static void main(String[] args) throws SQLException {
        /*Student student = new Student(1,"2s","3s","4s","5s");
        Information project = new Information(1,"as","bs","cs",2);
        */
        Student student2 = new Student();
        //insert(student);
        student2.setSex("男");
        //System.out.println(delete(student, "name"));
        List<Student> list = select(student2,"sex");
        for(Student s:list){
            System.out.println(s.toString());
        }
        //update(student,"id");
    }
}
