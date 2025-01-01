package scaffolding.gui.dal.entity;

import lombok.Data;

import scaffolding.gui.dal.annotation.FieldDescription;

@Data
public class Administrator {

    @FieldDescription("管理员 ID")
    private String adminId;

    @FieldDescription("姓名")
    private String name;

    @FieldDescription("电话")
    private String phone;

    @FieldDescription("邮箱")
    private String email;

    @FieldDescription("密码")
    private String password;

}

