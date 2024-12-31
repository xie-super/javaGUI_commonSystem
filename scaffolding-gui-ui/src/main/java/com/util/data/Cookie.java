package com.util.data;

import lombok.Data;
import scaffolding.gui.start.config.UserConfig.User;

/**
 * Author:lb
 * Time:2024/1/11
 * Name:IntelliJ IDEA
 */
@Data
public class Cookie {
    private static Cookie instance;
    private String username;
    private User user;
    private Cookie() {

    }

    public static synchronized Cookie getInstance() {
        if (instance == null) {
            instance = new Cookie();
        }
        return instance;
    }

}

