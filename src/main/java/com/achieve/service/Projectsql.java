package com.achieve.service;

import com.achieve.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Projectsql {
    public static Object[][] scodata = new Object[999][7];

    private static Connection getConn(){
        return DB.getConn();
    }








    public static int updateProject(int id, int project, String title) {
        Connection conn = getConn();
        int i = 0;
        String sql = "UPDATE project SET mark = ? WHERE id = ? AND title = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, project);
            pstmt.setInt(2, id);
            pstmt.setString(3, title);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static void getIdSubjectSubjectTeacherAndProject() {
        Connection conn = getConn();
        String sql = "SELECT * FROM project";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                scodata[i][0] = rs.getInt(1);
                scodata[i][1] = rs.getString(2);
                scodata[i][2] = rs.getString(3);
                scodata[i][3] = rs.getInt("mark"); // Use getInt for mark column
                i++;
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<String> findTop8StudentsBySport(String sportName) throws SQLException {
        Connection connection = getConn();
        List<String> top8InfoList = new ArrayList<>();

        // Assuming you have a connection object named 'connection'
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT si.mark, s.name AS studentName " +
                        "FROM sportinformation si " +
                        "JOIN student s ON si.id = s.id " +
                        "WHERE si.sportId = (SELECT sportId FROM sport WHERE sportName = ?) " +
                        "ORDER BY si.mark DESC " +
                        "LIMIT 8"
        )) {
            preparedStatement.setString(1, sportName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int mark = resultSet.getInt("mark");
                    String studentName = resultSet.getString("studentName");

                    // Customize this part based on your requirements
                    String info = "分数: " + mark + "        学生姓名: " + studentName;
                    top8InfoList.add(info);
                }
            }
        }

        return top8InfoList;
    }

    public List<SportInformationVO> findSportInfoStudentsBySport(String sportName) throws SQLException {
        Connection connection = getConn();
        List<SportInformationVO> top8InfoList = new ArrayList<>();

        // Assuming you have a connection object named 'connection'
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT s.name AS studentName, sp.sportName, si.* FROM student s JOIN sportinformation si ON s.id = si.id JOIN sport sp ON si.sportId = sp.sportId WHERE sp.sportName = ?"

        )) {
            preparedStatement.setString(1, sportName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String  id = resultSet.getString("id");

                    String studentName = resultSet.getString("studentName");
                    sportName = resultSet.getString("sportName");
                    String sportId = resultSet.getString("sportId");
                    int mark = resultSet.getInt("mark");
                    String startTime = resultSet.getString("startTime");
                    String adminUsername = resultSet.getString("adminUsername");
                    SportInformationVO sportInfo = SportInformationVO.builder().id(id).name(studentName).sportName(sportName).sportId(sportId).startTime(startTime).mark(mark).adminUsername(adminUsername).build();

                    // Customize this part based on your requirements

                    top8InfoList.add(sportInfo);
                }
            }
        }

        return top8InfoList;
    }


    public List<SportInformationVO> findSportInfoTeachersBySport(String sportName) throws SQLException {
        Connection connection = getConn();
        List<SportInformationVO> top8InfoList = new ArrayList<>();

        // Assuming you have a connection object named 'connection'
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT s.name AS studentName, sp.sportName, si.* FROM teacher s JOIN sportinformation si ON s.id = si.id JOIN sport sp ON si.sportId = sp.sportId WHERE sp.sportName = ?"

        )) {
            preparedStatement.setString(1, sportName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String  id = resultSet.getString("id");

                    String studentName = resultSet.getString("studentName");
                    sportName = resultSet.getString("sportName");
                    String sportId = resultSet.getString("sportId");
                    int mark = resultSet.getInt("mark");
                    String startTime = resultSet.getString("startTime");
                    String adminUsername = resultSet.getString("adminUsername");
                    SportInformationVO sportInfo = SportInformationVO.builder().id(id).name(studentName).sportName(sportName).sportId(sportId).startTime(startTime).mark(mark).adminUsername(adminUsername).build();

                    // Customize this part based on your requirements

                    top8InfoList.add(sportInfo);
                }
            }
        }

        return top8InfoList;
    }
    public List<String> findTop8TeachersBySport(String sportName) throws SQLException {
        List<String> top8InfoList = new ArrayList<>();
        Connection connection = getConn();
        // Assuming you have a connection object named 'connection'
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT si.mark, t.name AS teacherName " +
                        "FROM sportinformation si " +
                        "JOIN teacher t ON si.id = t.id " +
                        "WHERE si.sportId = (SELECT sportId FROM sport WHERE sportName = ?) " +
                        "ORDER BY si.mark DESC " +
                        "LIMIT 8"
        )) {
            preparedStatement.setString(1, sportName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int mark = resultSet.getInt("mark");
                    String teacherName = resultSet.getString("teacherName");

                    // Customize this part based on your requirements
                    String info = "分数: " + mark + "        老师姓名: " + teacherName;
                    top8InfoList.add(info);
                }
            }
        }

        return top8InfoList;
    }


    public List<String> findTop8ByClazz() throws SQLException {
        Connection connection = getConn();
        List<String> top8InfoList = new ArrayList<>();

        // Assuming you have a connection object named 'connection'
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT s.clazz, SUM(si.mark) AS totalScore FROM sportinformation si JOIN student s ON si.id = s.id GROUP BY s.clazz ORDER BY totalScore DESC LIMIT 8;"
        )) {


            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String clazz = resultSet.getString("clazz");
                    int totalScore = resultSet.getInt("totalScore");

                    // Customize this part based on your requirements
                    String info = "学院: " + clazz + "        积分: " + totalScore*0.4;

                    top8InfoList.add(info);
                }
            }
        }

        return top8InfoList;
    }
    public static void get(String title) {
        Connection conn = getConn();
        String sql = "SELECT * FROM project WHERE title = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                scodata[i][0] = rs.getInt(1);
                scodata[i][1] = rs.getString(2);
                scodata[i][2] = rs.getString(3);
                scodata[i][3] = rs.getString(4);
                scodata[i][4] = rs.getInt(5);
                i++;
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void getByStudentId(int id, String title) {
        Connection conn = getConn();
        String sql = "SELECT * FROM project WHERE title = ? AND id = ? ORDER BY mark";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setInt(2, id);
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                scodata[i][0] = rs.getInt(1);
                scodata[i][1] = rs.getString(2);
                scodata[i][2] = rs.getString(3);
                scodata[i][3] = rs.getString(4);
                scodata[i][4] = rs.getInt(5);
                i++;
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getByProjectQujian(String title, int down, int up) {
        Connection conn = getConn();
        String sql = "SELECT * FROM project WHERE title = ? AND mark >= ? AND mark <= ? ORDER BY mark";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setInt(2, down);
            pstmt.setInt(3, up);
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                scodata[i][0] = rs.getInt(1);
                scodata[i][1] = rs.getString(2);
                scodata[i][2] = rs.getString(3);
                scodata[i][3] = rs.getString(4);
                scodata[i][4] = rs.getInt(5);
                i++;
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int delete(int id) {
        Connection conn = getConn();
        int i = 0;
        String sql = "DELETE FROM project WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int deleteById(int id, String title) {
        Connection conn = getConn();
        int i = 0;
        String sql = "DELETE FROM project WHERE id = ? AND title = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, title);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int insert(int id, String number) {
        Connection conn = getConn();
        int i = 0;
        String sql = "INSERT INTO project (id, studentName) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, number);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int update(int id, String title, String initiator) {
        Connection conn = getConn();
        int i = 0;
        String sql = "UPDATE project SET title = ?, initiator = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, initiator);
            pstmt.setInt(3, id);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 教师输入成绩
     */
    public static int update(int id, int mark) {
        Connection conn = getConn();
        int i = 0;
        String sql = "UPDATE project SET mark = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mark);
            pstmt.setInt(2, id);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 学生查自己科研成绩
     */
    public static void get(int id) {
        Connection conn = getConn();
        String sql = "SELECT * FROM project WHERE id = ?";
        PreparedStatement pstmt;
        PreparedStatement pstmt2;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                scodata[i][0] = rs.getInt(1);
                scodata[i][1] = rs.getString(2);

                String sql2 = "SELECT AVG(mark) AS avg_project FROM project WHERE title = ?";
                Connection conn2 = getConn();
                pstmt2 = conn2.prepareStatement(sql2);
                pstmt2.setString(1, rs.getString(2));
                ResultSet rs2 = pstmt2.executeQuery();
                rs2.next();

                scodata[i][2] = rs.getString(3);
                scodata[i][3] = rs.getString(4);

                scodata[i][4] = rs.getInt(5);
                scodata[i][5] = rs2.getObject("avg_project");

                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAll() {
        Connection conn = getConn();
        String sql = "SELECT * FROM project";
        PreparedStatement pstmt;
        PreparedStatement pstmt2;
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                scodata[i][0] = rs.getInt(1);
                scodata[i][1] = rs.getString(2);

                scodata[i][2] = rs.getString(3);
                scodata[i][3] = rs.getString(5);

                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getRankBySubjectAndResult(String title, int mark) {
        Connection conn = getConn();
        String sql = "SELECT COUNT(*) AS num FROM project WHERE mark > ? AND title = ?";

        PreparedStatement pstmt;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mark);
            pstmt.setString(2, title);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String[] args) throws SQLException {
        Projectsql projectsql = new Projectsql();
        List<SportInformationVO> list =  projectsql.findSportInfoStudentsBySport("羽毛球");
        System.out.println(1);
    }
}