package scaffolding.gui.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import scaffolding.gui.dal.config.DB;
import scaffolding.gui.start.JsonParser;
import scaffolding.gui.start.config.UserConfig;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
public class UserImpl {

    private static boolean login(String className, String username, String password) throws Exception {
        className = String.format("scaffolding.gui.dal.entity.%s", className);
        Class<?> userClass = Class.forName(className);
        Object user = userClass.getDeclaredConstructor().newInstance();
        UserConfig userConfig = JsonParser.parseUserConfig();
        String dbUserName = "studentId";
        Field usernameField = userClass.getDeclaredField(dbUserName);
        usernameField.setAccessible(true);

        usernameField.set(user, username);
        List<Object> userList = DB.select(user, dbUserName);
        if (CollectionUtils.isNotEmpty(userList)) {
            return true;
        }
        return false;

    }

    public static void main(String[] args) throws Exception {
        login("Student", "S20230001", "student");
    }
}
