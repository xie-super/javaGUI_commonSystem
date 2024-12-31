package scaffolding.gui.service.impl;


import scaffolding.gui.dal.annotation.AnnotationUtils;
import scaffolding.gui.dal.config.DB;
import scaffolding.gui.service.utils.CookieUtils;
import scaffolding.gui.service.vo.FunctionDataVO;
import scaffolding.gui.start.config.UserConfig;
import scaffolding.gui.start.util.TransferStringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            functionDataVO.setFunctionName(function.getFunctionName());
            Map<String, String> functionMapping = new HashMap<>();
            for(int i = 0; i < function.getRole().size(); i++) {
                String role = function.getRole().get(i);
                String roleName = function.getRoleName().get(i);
                functionMapping.put(role, roleName);
            }
            functionDataVO.setFunctionMapping(functionMapping);
            String className = String.format("scaffolding.gui.dal.entity.%s", TransferStringUtils.toPascalCase(function.getTableName()));
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

    public static List<List<Object>> convertToListOfObjectLists(List<?> objects) {
        List<List<Object>> result = new ArrayList<>();

        for (Object obj : objects) {
            List<Object> objectList = new ArrayList<>();
            Class<?> clazz = obj.getClass(); // 获取对象的类
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); // 设置字段可访问
                try {
                    Object value = field.get(obj); // 获取字段的值
                    objectList.add(value); // 直接存储 Object 类型
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    objectList.add(null); // 异常时使用 null 填充
                }
            }
            result.add(objectList);
        }

        return result;
    }
}
