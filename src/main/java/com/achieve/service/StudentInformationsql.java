package com.achieve.service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentInformationsql {
    public static Object[][] studata = new Object[99][5];

    private static Connection getConn() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://150.158.173.160:3306/science?serverTimezone=UTC";
        String username = "science";
        String password = "2etebKAnxrN3ct5W";
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



    // 修改 update 方法中的 SQL 语句
    public static int update(int id, String clazz, String password) {
        Connection conn = getConn();
        int i = 0;
        String sql = "UPDATE student SET clazz=?, password=? WHERE id=?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, clazz);
            pstmt.setString(2, password);
            pstmt.setInt(3, id);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    // 修改 updatePassword 方法中的 SQL 语句
    public static int updatePassword(int id, String password) {
        Connection conn = getConn();
        int i = 0;
        String sql = "UPDATE student SET password=? WHERE id=?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setInt(2, id);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    // 修改 getAll 方法中的 SQL 查询语句和数据获取
    public static void getAll() {
        Connection conn = getConn();
        String sql = "SELECT * FROM student";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                studata[i][0] = rs.getInt("id");
                studata[i][1] = rs.getString("name");
                studata[i][2] = rs.getString("sex");
                studata[i][3] = rs.getString("clazz");
                studata[i][4] = rs.getString("password");
                i++;
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改 delete 方法中的 SQL 语句
    public static int delete(int id) {
        Connection conn = getConn();
        int i = 0;
        String sql = "DELETE FROM student WHERE id=?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    // 修改 getclazz 方法中的 SQL 查询语句和数据获取
    public static String getclazz(int id) {
        String clazz = null;
        Connection conn = getConn();
        String sql = "SELECT clazz FROM student WHERE id=?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                clazz = rs.getString("clazz");
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    // 修改 getname 方法中的 SQL 查询语句和数据获取
    public static String getname(int id) {
        String name = null;
        Connection conn = getConn();
        String sql = "SELECT name FROM student WHERE id=?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }


    public static void main(String[] args) throws IllegalAccessException {

    }
}

