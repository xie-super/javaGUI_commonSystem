package scaffolding.gui.dal.entity;

import lombok.Data;

import scaffolding.gui.dal.annotation.FieldDescription;

@Data
public class Administrator {

    @FieldDescription(name = "adminId", description = "管理员 ID")
    private String adminId;

    @FieldDescription(description = "姓名")
    private String name;

    @FieldDescription(description = "电话")
    private String phone;

    @FieldDescription(description = "邮箱")
    private String email;

    @FieldDescription(description = "密码")
    private String password;

    public static void main(String[] args) {

    }
}

