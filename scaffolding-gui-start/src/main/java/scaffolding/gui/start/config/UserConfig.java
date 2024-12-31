package scaffolding.gui.start.config;
import java.util.List;

public class UserConfig {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public static class User {
        private String tableName;
        private String roleName;
        private String keyOfUserName;
        private List<Function> function;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }
        public String getRoleName() {
            return roleName;
        }
        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getKeyOfUserName() {
            return keyOfUserName;
        }

        public void setKeyOfUserName(String keyOfUserName) {
            this.keyOfUserName = keyOfUserName;
        }

        public List<Function> getFunction() {
            return function;
        }

        public void setFunction(List<Function> function) {
            this.function = function;
        }

        public static class Function {
            private String tableName;
            private List<String> role;
            private List<String> roleName;

            public String getTableName() {
                return tableName;
            }

            public void setTableName(String tableName) {
                this.tableName = tableName;
            }

            public List<String> getRole() {
                return role;
            }

            public void setRole(List<String> role) {
                this.role = role;
            }

            public List<String> getRoleName() {
                return roleName;
            }

            public void setRoleName(List<String> roleName) {
                this.roleName = roleName;
            }
        }
    }
}
