package scaffolding.gui.dal.entity;

import java.util.Date;
import scaffolding.gui.dal.annotation.FieldDescription;
import lombok.*;
import java.io.Serializable;

/**
 * 学生信息
 *
 * @author lb
 * @date 2025-01-03 11:48:35
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 学号 */
    @FieldDescription(name = "studentId",description = "学号")
    private String studentId;

    /** 姓名 */
    @FieldDescription(name = "name",description = "姓名")
    private String name;

    /** 性别 */
    @FieldDescription(name = "gender",description = "性别")
    private String gender;

    /** 系别 */
    @FieldDescription(name = "department",description = "系别")
    private String department;

    /** 电话 */
    @FieldDescription(name = "phone",description = "电话")
    private String phone;

    /** 宿舍编号 */
    @FieldDescription(name = "dormId",description = "宿舍编号")
    private String dormId;

    /** 床位号 */
    @FieldDescription(name = "bedNumber",description = "床位号")
    private Integer bedNumber;

    /** 入住日期 */
    @FieldDescription(name = "checkInDate",description = "入住日期")
    private Date checkInDate;

    /** 密码 */
    @FieldDescription(name = "password",description = "密码")
    private String password;


}
