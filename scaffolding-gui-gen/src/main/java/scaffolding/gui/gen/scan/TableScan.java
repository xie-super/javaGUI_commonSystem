package scaffolding.gui.gen.scan;

import scaffolding.gui.common.util.TransferStringUtils;
import scaffolding.gui.gen.entity.GenTable;
import scaffolding.gui.gen.entity.GenTableColumn;
import scaffolding.gui.gen.utils.GenUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static scaffolding.gui.dal.config.DB.closeQuietly;
import static scaffolding.gui.dal.config.DB.getConn;

public class TableScan {


    public static List<GenTable> getAllTableInfos() throws Exception {
        Connection conn = getConn();
        Statement stmt = conn.createStatement();
        // 查询表名及注释
        ResultSet rs = stmt.executeQuery(
                "SELECT TABLE_NAME, TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES " +
                        "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_TYPE = 'BASE TABLE'"
        );

        List<GenTable> tableInfos = new ArrayList<>();
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            String tableComment = rs.getString("TABLE_COMMENT");
            GenTable genTable = GenTable.builder()
                    .tableName(tableName)
                    .tableComment(tableComment)
                    .className(TransferStringUtils.toPascalCase(tableName))
                    .build();
            genTable.setColumns(getColumnInfo(tableName));
            tableInfos.add(genTable);
        }
        closeQuietly(rs);
        closeQuietly(stmt);
        closeQuietly(conn);
        return tableInfos;
    }

    public static GenTable getTableInfoByTableName(String tableName) throws Exception {
        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalArgumentException("tableName cannot be null or empty");
        }

        Connection conn = getConn();
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT TABLE_NAME, TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?"
        );
        pstmt.setString(1, tableName);

        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            closeQuietly(rs);
            closeQuietly(pstmt);
            closeQuietly(conn);
            return null;
        }

        String tableComment = rs.getString("TABLE_COMMENT");
        GenTable genTable = GenTable.builder()
                .tableName(tableName)
                .tableComment(tableComment)
                .className(TransferStringUtils.toPascalCase(tableName))
                .build();
        genTable.setColumns(getColumnInfo(tableName));

        closeQuietly(rs);
        closeQuietly(pstmt);
        closeQuietly(conn);
        return genTable;
    }


    public static List<GenTableColumn> getColumnInfo(String tableName) throws Exception {
        Connection conn = getConn();
        Statement stmt = conn.createStatement();

        // 查询字段基本信息
        ResultSet rsColumns = stmt.executeQuery(
                "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT " +
                        "FROM INFORMATION_SCHEMA.COLUMNS " +
                        "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '" + tableName + "'"
        );

        List<GenTableColumn> columnInfos = new ArrayList<>();
        while (rsColumns.next()) {
            String columnName = rsColumns.getString("COLUMN_NAME");
            String columnType = rsColumns.getString("DATA_TYPE");
            String columnComment = rsColumns.getString("COLUMN_COMMENT");
            String javaField = TransferStringUtils.toCamelCase(columnName);
            GenTableColumn columnInfo = GenTableColumn.builder()
                    .columnName(columnName)
                    .columnComment(columnComment)
                    .columnType(columnType)
                    .javaType(GenUtil.mapDbTypeToJavaType(columnType))
                    .javaField(javaField)
                    .build();
            // 查询字段是否为主键
            Statement pkStmt = conn.createStatement();
            ResultSet rsKeys = pkStmt.executeQuery(
                    "SELECT CONSTRAINT_NAME " +
                            "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                            "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '" + tableName + "' " +
                            "AND COLUMN_NAME = '" + columnName + "' " +
                            "AND CONSTRAINT_NAME = 'PRIMARY'"
            );
            if (rsKeys.next()) {
                columnInfo.setIsPk(true);
            }
            closeQuietly(pkStmt,rsKeys);
            columnInfos.add(columnInfo);
        }
        closeQuietly(rsColumns,stmt);
        return columnInfos;
    }

    public static void main(String[] args) throws Exception {
        getAllTableInfos();
    }
}
