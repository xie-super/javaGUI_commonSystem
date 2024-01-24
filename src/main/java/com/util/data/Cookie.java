package com.util.data;

/**
 * Author:xie-super
 * Time:2024/1/11
 * Name:IntelliJ IDEA
 */
public class Cookie {
    private static Cookie instance;
    private AccountType accountType;
    private String username;

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
    //身份
    public enum AccountType {
        STUDENT, ADMINISTRATOR,TEACHER,
        // Add more types as needed
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

