package scaffolding.gui.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import scaffolding.gui.dal.config.DB;
import scaffolding.gui.service.utils.CookieUtils;
import scaffolding.gui.start.JsonParser;
import scaffolding.gui.start.init.UserConfig;
import scaffolding.gui.start.util.TransferStringUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author lb
 */
@Slf4j
public class UserImpl {

    public boolean login(String roleName, String username, String password) throws Exception {
        UserConfig userConfig = JsonParser.parseUserConfig();
        CookieUtils cookieUtils = CookieUtils.getInstance();
        String className = null;
        String dbUserName = null;
        if(userConfig == null) {
            log.error("userConfig is null");
            throw new RuntimeException("UserConfig is null");
        }
        for(UserConfig.User user : userConfig.getUsers()) {
            if(StringUtils.equals(user.getRoleName(), roleName)) {
                className = String.format("scaffolding.gui.dal.entity.%s", TransferStringUtils.toPascalCase(user.getTableName()));
                dbUserName = user.getKeyOfUserName();
                cookieUtils.setUser(user);
                break;
            }
        }
        if(StringUtils.isBlank(className)) {
            log.error("can't find given role");
            throw new RuntimeException("can't find given role");
        }
        Class<?> userClass = Class.forName(className);
        Object user = userClass.getDeclaredConstructor().newInstance();

        Field usernameField = userClass.getDeclaredField(dbUserName);
        Field passwordField = userClass.getDeclaredField("password");
        usernameField.setAccessible(true);
        passwordField.setAccessible(true);

        usernameField.set(user, username);
        passwordField.set(user, password);
        List<Object> userList = DB.select(user, dbUserName, "password");
        if (CollectionUtils.isNotEmpty(userList)) {
            cookieUtils.setUsername(username);
            return true;
        }
        cookieUtils.destroy();
        return false;
    }

    public static void main(String[] args) throws Exception {
        //login("学生", "S20230001", "123456");
    }
}
