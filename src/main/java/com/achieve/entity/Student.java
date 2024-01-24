package com.achieve.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//规定使用此项目所有实体的变量必须为数据库中对应表中的名字，且都为包装类，不能为基础类（null问题)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Student {
    private String id;
    private String name;
    private String sex;
    private String clazz;
    private String password;
    private Integer isRegistered; // 0和 1


    public Student(String id, String password, String realNamel, int i) {
        this.id = id;
        this.password = password;
        this.name  = realNamel;
        this.isRegistered = i;
    }

    public Student(String username) {
        this.name = username;
    }


}