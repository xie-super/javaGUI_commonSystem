package scaffolding.gui.service.impl;

import lombok.extern.slf4j.Slf4j;
import scaffolding.gui.service.utils.CookieUtils;
import scaffolding.gui.start.config.UserConfig.User;

import java.util.List;

/**
 * @author superxie
 */
@Slf4j
public class MenueImpl {
    public List<User.Function> getUserFunctions() {
        CookieUtils cookieUtils = CookieUtils.getInstance();
        if(cookieUtils.getUser() == null || cookieUtils.getUser().getFunction() == null) {
            throw new RuntimeException("获取不到当前登录用户信息/用户功能信息");
        }
        return cookieUtils.getUser().getFunction();
    }
}
