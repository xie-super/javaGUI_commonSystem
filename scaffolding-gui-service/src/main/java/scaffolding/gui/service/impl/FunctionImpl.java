package scaffolding.gui.service.impl;


import scaffolding.gui.dal.annotation.AnnotationUtils;
import scaffolding.gui.dal.config.DB;
import scaffolding.gui.service.utils.CookieUtils;
import scaffolding.gui.service.vo.FunctionDataVO;
import scaffolding.gui.start.config.UserConfig;
import scaffolding.gui.start.util.TransferStringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author superxie
 */
public class FunctionImpl {

    /**
     * @Param functionName 功能名称，用于定位到当前登录角色所选Function
     * @Param condition 键为表字段名，值为对应字段的筛选值
     */

    public FunctionDataVO getFunctionDataByCondition(String functionName, Map<String, String> condition) {
        CookieUtils cookieUtils = CookieUtils.getInstance();
        if(cookieUtils.getUser() == null || cookieUtils.getUser().getFunction() == null) {
            throw new RuntimeException("当前无用户信息/当前用户无功能区");
        }
        FunctionDataVO functionDataVO = new FunctionDataVO();
        List<UserConfig.User.Function> functionList = cookieUtils.getUser().getFunction();
        for(UserConfig.User.Function function : functionList) {
            if(!functionName.equals(function.getFunctionName())) {
                continue;
            }
            functionDataVO.setTableName(function.getTableName());
            functionDataVO.setFunctionName(function.getFunctionName());
            Map<String, String> functionMapping = new HashMap<>();
            for(int i = 0; i < function.getRole().size(); i++) {
                String role = function.getRole().get(i);
                String roleName = function.getRoleName().get(i);
                functionMapping.put(role, roleName);
            }
            functionDataVO.setFunctionMapping(functionMapping);
            String className = String.format("scaffolding.gui.dal.entity.%s", TransferStringUtils.toPascalCase(function.getTableName()));
            System.out.println(className);
            try {
                List<String> argsList = new ArrayList<>();
                Class<?> entityClass = Class.forName(className);
                List<String> functionHeaderList = AnnotationUtils.getEntityDescriptionList(entityClass);
                functionDataVO.setFunctionHeaderList(functionHeaderList);
                Object entity = entityClass.getDeclaredConstructor().newInstance();
                for(Map.Entry<String, String> entry : condition.entrySet()) {
                    String key = entry.getKey();
                    Field field = entityClass.getDeclaredField(key);
                    field.setAccessible(true);
                    String value = entry.getValue();
                    field.set(entity, value);
                    argsList.add(value);
                }
                List<List<Object>> dataList = convertToListOfObjectLists(DB.select(entity, argsList.toArray(new String[0])));
                functionDataVO.setDataList(dataList);
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return functionDataVO;
    }
    public FunctionDataVO getFunctionData(String functionName){
        return getFunctionDataByCondition(functionName, new HashMap<>());
    }

    public void insertEntity(String tableName, String[] newValues) {
        String className = String.format("scaffolding.gui.dal.entity.%s", TransferStringUtils.toPascalCase(tableName));
        try {
            Class<?> clazz = Class.forName(className);
            Object entity = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < newValues.length && i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                field.set(entity, convertValue(newValues[i],field.getType()));
            }
            // 保存实体，调用你的 DAO 层逻辑
            DB.insert(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateEntity(String tableName, String[] newValues) {
        String className = String.format("scaffolding.gui.dal.entity.%s", TransferStringUtils.toPascalCase(tableName));
        try {
            Class<?> clazz = Class.forName(className);
            Object entity = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < newValues.length && i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                field.set(entity, convertValue(newValues[i],field.getType()));
            }
            DB.update(entity, fields[0].getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteEntity(String tableName, String key) {
        String className = String.format("scaffolding.gui.dal.entity.%s", TransferStringUtils.toPascalCase(tableName));
        try {
            Class<?> clazz = Class.forName(className);
            Object entity = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();
            Field field = fields[0];
            field.setAccessible(true);
            field.set(entity, convertValue(key,field.getType()));
            DB.delete(entity, fields[0].getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<List<Object>> convertToListOfObjectLists(List<?> objects) {
        List<List<Object>> result = new ArrayList<>();

        for (Object obj : objects) {
            List<Object> objectList = new ArrayList<>();
            Class<?> clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    objectList.add(value != null ? value : "");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    objectList.add(null);
                }
            }
            result.add(objectList);
        }

        return result;
    }
    private Object convertValue(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }
        if (targetType.isInstance(value)) {
            return value;
        }
        if (targetType == int.class || targetType == Integer.class) {
            return Integer.valueOf(value.toString());
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.valueOf(value.toString());
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.valueOf(value.toString());
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.valueOf(value.toString());
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.valueOf(value.toString());
        } else if (targetType == String.class) {
            return value.toString();
        } else if (targetType == BigDecimal.class) {
            // Convert String to BigDecimal
            if (value instanceof String) {
                return new BigDecimal((String) value);
            } else {
                throw new IllegalArgumentException("Cannot convert value of type " + value.getClass() + " to BigDecimal");
            }
        }else if (targetType == Date.class) {
            if (value instanceof String) {
                // Parse the String into a Date using SimpleDateFormat
                try {
                    String dateString = (String) value;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return sdf.parse(dateString);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Cannot convert value of type " + value.getClass() + " to Date", e);
                }
            } else {
                throw new IllegalArgumentException("Cannot convert value of type " + value.getClass() + " to Date");
            }
        }else {

            throw new IllegalArgumentException("Cannot convert value of type " + value.getClass() + " to " + targetType);
        }
    }
}
