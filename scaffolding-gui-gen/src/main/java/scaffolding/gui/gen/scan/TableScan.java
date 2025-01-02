package scaffolding.gui.gen.scan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static scaffolding.gui.dal.config.DB.getConn;

public class TableScan {


    public static List<String> getAllTableNames() throws Exception {
        Connection conn = getConn();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES");

        List<String> tableNames = new ArrayList<>();
        while (rs.next()) {
            tableNames.add(rs.getString(1));
        }
        rs.close();
        stmt.close();
        conn.close();
        return tableNames;
    }

    public static void getDataForTableAndFormDomain() throws Exception {
        List<String> tableNames = getAllTableNames();
        Connection conn = getConn();
        for (String tableName : tableNames) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM `" + tableName + "`");
            ResultSet rs = pstmt.executeQuery();

            List<Map<String, Object>> results = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
            rs.close();
            pstmt.close();
        }

        conn.close();
    }

    public static List<Map<String, Object>> getColumnInfo(String tableName) throws Exception {
        Connection conn = getConn();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT " +
                        "FROM INFORMATION_SCHEMA.COLUMNS " +
                        "WHERE TABLE_SCHEMA = '"+tableName+"'"+" AND TABLE_NAME = '" + tableName + "'"
        );

        List<Map<String, Object>> columnInfos = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> columnInfo = new HashMap<>();
            columnInfo.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
            columnInfo.put("DATA_TYPE", rs.getString("DATA_TYPE"));
            columnInfo.put("COLUMN_COMMENT", rs.getString("COLUMN_COMMENT"));
            columnInfos.add(columnInfo);
        }

        rs.close();
        stmt.close();
        conn.close();

        return columnInfos;
    }

    public static void main(String[] args) throws Exception {
        getDataForTableAndFormDomain();
    }
}
