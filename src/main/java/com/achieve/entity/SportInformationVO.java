package com.achieve.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:xie-super
 * Time:2024/1/14
 * Name:IntelliJ IDEA
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SportInformationVO {
    //添加中文注释
    private String id;
    //运动标题
    private String sportId;
    private String name;
    private String sportName;
    //运动类型
    //开始时间
    private String startTime;
    //分数
    private Integer mark;
    //管理员用户名
    private String adminUsername;

}
