package com.achieve.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Information表，功能是存储一些列与系统相关的字段,还可以由此表进行派生
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SportInformation {
    //添加中文注释
    private String id;
    //运动标题
    private String sportId;

//运动类型
    //开始时间

   //分数
    private Integer mark;
    private String startTime;
    //管理员用户名
    private String adminUsername;


}
