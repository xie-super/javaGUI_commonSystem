package com.util.data;

import lombok.Data;

/**
 * Author:xie-super
 * Time:2024/1/11
 * Name:IntelliJ IDEA
 */
@Data
public class Cookie {
    private static Cookie instance;
    private AccountType accountType;
    private String username;
    private int userId;
    private Cookie() {
        // 私有构造方法，确保只能在类内部实例化

    }

    public static synchronized Cookie getInstance() {
        if (instance == null) {
            instance = new Cookie();
        }
        return instance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //身份
    public enum AccountType {
        STUDENT, ADMINISTRATOR,TEACHER,
        // Add more types as needed
    }
    public String getUsername() {
        return username;
    }
    public Integer getUserId(){
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

