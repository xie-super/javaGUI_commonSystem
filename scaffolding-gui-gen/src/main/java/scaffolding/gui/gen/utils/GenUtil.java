package scaffolding.gui.gen.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: 乐滨
 * @Date: 2025-01-02
 */
public class GenUtil {
    /**
     * 将数据库数据类型转换为Java类型
     *
     * @param dbColumnType 数据库数据类型字符串
     * @return 对应的Java类型字符串
     */
    public static String mapDbTypeToJavaType(String dbColumnType) {
        Map<String, String> typeMapping = new HashMap<>();
        typeMapping.put("varchar", "String");
        typeMapping.put("char", "String");
        typeMapping.put("text", "String");
        typeMapping.put("int", "Integer");
        typeMapping.put("integer", "Integer");
        typeMapping.put("tinyint", "Integer");
        typeMapping.put("smallint", "Short");
        typeMapping.put("bigint", "Long");
        typeMapping.put("float", "BigDecimal");
        typeMapping.put("double", "BigDecimal");
        typeMapping.put("decimal", "BigDecimal");
        typeMapping.put("date", "Date");
        typeMapping.put("datetime", "Date");
        typeMapping.put("timestamp", "Date");

        String lowerCaseDbColumnType = dbColumnType.toLowerCase();
        return typeMapping.getOrDefault(lowerCaseDbColumnType, "String");
    }

}
