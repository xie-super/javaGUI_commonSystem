package scaffolding.gui.service.utils;

import lombok.Data;
import scaffolding.gui.start.init.UserConfig.User;

/**
 * Name:IntelliJ IDEA
 * @author lb
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

