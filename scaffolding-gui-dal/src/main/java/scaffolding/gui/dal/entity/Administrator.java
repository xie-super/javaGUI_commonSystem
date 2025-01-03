package scaffolding.gui.dal.entity;

import scaffolding.gui.dal.annotation.FieldDescription;
import lombok.*;
import java.io.Serializable;

/**
 * 管理员账户信息
 *
 * @author lb
 * @date 2025-01-03 13:33:28
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Administrator implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 管理员ID */
    @FieldDescription(name = "adminId",description = "管理员ID")
    private String adminId;

    /** 姓名 */
    @FieldDescription(name = "name",description = "姓名")
    private String name;

    /** 电话号码 */
    @FieldDescription(name = "phone",description = "电话号码")
    private String phone;

    /** 电子邮件地址 */
    @FieldDescription(name = "email",description = "电子邮件地址")
    private String email;

    /** 密码 */
    @FieldDescription(name = "password",description = "密码")
    private String password;


}
