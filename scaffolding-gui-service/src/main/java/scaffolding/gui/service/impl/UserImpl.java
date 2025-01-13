package scaffolding.gui.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import scaffolding.gui.common.util.JsonParser;
import scaffolding.gui.common.util.TransferStringUtils;
import scaffolding.gui.dal.database.DatabaseConnector;
import scaffolding.gui.dal.database.factory.DataBaseFactory;
import scaffolding.gui.service.utils.CookieUtils;
import scaffolding.gui.start.init.UserConfig;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author lb
 */
@Slf4j
public class UserImpl {
    private static DatabaseConnector databaseConnector;
    static{
        try {
            databaseConnector = DataBaseFactory.getConnector();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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

        Class<?> fieldType = usernameField.getType();
        Object convertedValue = convertToFieldType(username, fieldType);
        usernameField.set(user, convertedValue);
        passwordField.set(user, password);
        List<Object> userList = databaseConnector.select(user, dbUserName, "password");
        if (CollectionUtils.isNotEmpty(userList)) {
            cookieUtils.setUsername(username);
            return true;
        }
        cookieUtils.destroy();
        return false;
    }

    private static Object convertToFieldType(String value, Class<?> fieldType) throws Exception {
        if (fieldType == String.class) {
            return value;
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return Integer.valueOf(value);
        } else if (fieldType == Short.class || fieldType == short.class) {
            return Short.valueOf(value);
        } else if (fieldType == Long.class || fieldType == long.class) {
            return Long.valueOf(value);
        } else if (fieldType == java.math.BigDecimal.class) {
            return new java.math.BigDecimal(value);
        } else if (fieldType == java.util.Date.class) {
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(value);
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType);
        }
    }

    public static void main(String[] args) throws Exception {
        //login("学生", "S20230001", "123456");
    }
}
