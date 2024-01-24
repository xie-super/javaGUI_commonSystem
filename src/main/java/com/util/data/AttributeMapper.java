package com.util.data;

import java.util.Map;
import java.util.HashMap;
import java.util.Map;

public class AttributeMapper {//规范是小写
    private static final Map<String, String> attributeMap = new HashMap<>();

    static {
        // 添加属性映射关系，将界面上的属性名映射为数据库中的字段名
        //项目名
        attributeMap.put("projectName", "大学运动会管理系统");
        //类名
        attributeMap.put("student","学生信息");
        attributeMap.put("teacher","教师信息");
        attributeMap.put("sport","运动项目信息");
        attributeMap.put("sportinformation","参赛信息");
        //属性名
        attributeMap.put("id", "学生学号/教室工号");
        attributeMap.put("name", "姓名");
        attributeMap.put("clazz", "班级");
        attributeMap.put("studentName", "姓名");
        attributeMap.put("password", "密码");
        attributeMap.put("sex", "性别");
        attributeMap.put("isRegistered", "是否注册");
        attributeMap.put("title", "职称");
        attributeMap.put("phone", "电话");
        attributeMap.put("email", "邮箱");
        attributeMap.put("adminUsername", "管理员用户名");
        attributeMap.put("sportId", "运动项目id");
        attributeMap.put("sportName","运动项目名称");
        attributeMap.put("startTime","开始时间");
        attributeMap.put("endTime","结束时间");
        attributeMap.put("type","类型");

        // 可根据实际情况添加更多映射关系
    }

    public static String mapAttributeToField(String attribute) {
        if(attributeMap.containsKey(attribute))
            return attributeMap.get(attribute);
        else
            return attribute;
    }
}
