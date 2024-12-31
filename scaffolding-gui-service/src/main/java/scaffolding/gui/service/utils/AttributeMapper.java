package scaffolding.gui.service.utils;

import java.util.HashMap;
import java.util.Map;

public class AttributeMapper {//规范是小写
    private static final Map<String, String> attributeMap = new HashMap<>();

    static {
        // 添加属性映射关系，将界面上的属性名映射为数据库中的字段名
        //项目名


        // 可根据实际情况添加更多映射关系
    }

    public static String mapAttributeToField(String attribute) {
        if(attributeMap.containsKey(attribute))
            return attributeMap.get(attribute);
        else
            return attribute;
    }
}
