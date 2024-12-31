package scaffolding.gui.service.utils;

import lombok.Data;
import scaffolding.gui.start.config.UserConfig.User;

/**
 * Author:lb
 * Time:2024/1/11
 * Name:IntelliJ IDEA
 */
@Data
public class CookieUtils {
    private static CookieUtils instance;
    private String username;
    private User user;
    private CookieUtils() {

    }

    public static synchronized CookieUtils getInstance() {
        if (instance == null) {
            instance = new CookieUtils();
        }
        return instance;
    }

    public void destroy() {
        user = null;
        username = null;
    }

}

